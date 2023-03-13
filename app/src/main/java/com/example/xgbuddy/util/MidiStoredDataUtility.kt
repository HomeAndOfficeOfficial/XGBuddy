package com.example.xgbuddy.util

import android.content.Context
import android.util.Log
import com.example.xgbuddy.data.*
import com.example.xgbuddy.util.EnumFinder.findBy
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject

class MidiStoredDataUtility @Inject constructor(val context: Context) {

    private var qs300Presets: List<QS300Preset>? = null
    private var qs300PresetsJSON: JSONObject? = null

    /**
     * TODO: Consider an interface or an abstract class which would define the following methods.
     * Since there will likely be a similar set of methods shared between QS300, XG, and maybe GM
     * and TGB, it would probably make sense to have a general MidiDataParser class and separate
     * implementations for QS300Parser, XGParser, etc.
     *
     * Will have to wait and see how the other data classes pan out. There will probably be a lot of
     * refactoring to do as that develops.
     *
     * Anyway it would also be nice to not have all this extra code cluttering this class.
     */

    fun getQS300Presets(): List<QS300Preset> {
        if (qs300Presets == null) {
            qs300Presets = parseQS300PresetsJSON()
        }
        return qs300Presets!!
    }

    fun getQS300BulkDumpMessage(preset: QS300Preset): List<MidiMessage> {
        val midiMessages = mutableListOf<MidiMessage>()
        qs300PresetsJSON?.let { presetsJSON ->
            val jsonArray = presetsJSON.getJSONArray(preset.name)
            for (i in 0 until jsonArray.length()) {
                val messageString = jsonArray.getString(i)
                midiMessages.add(MidiMessage(messageString.toByteArray(), getBulkDumpTimeStamp(i)))
            }
        }
        return midiMessages
    }

    private fun parseQS300PresetsJSON(): List<QS300Preset> {
        var jsonString: String
        val presets = mutableListOf<QS300Preset>()
        context.openFileInput(AppConstants.QS300_PRESET_FILE).apply {
            val size = available()
            val buffer = ByteArray(size)
            read(buffer)
            close()
            jsonString = String(buffer)
        }
        qs300PresetsJSON = JSONObject(jsonString)
        val presetKeyIterator = qs300PresetsJSON!!.keys()
        while (presetKeyIterator.hasNext()) {
            val presetName = presetKeyIterator.next()
            try {
                val messages: JSONArray = qs300PresetsJSON!!.get(presetName) as JSONArray
                presets.add(parseQS300PresetJSON(presetName, messages))
            } catch (e: Exception) {
                Log.e(
                    TAG,
                    "Unable to access preset with key: $presetName. Caught exception: ${e.message}"
                )
            }
        }
        return presets
    }


    private fun parseQS300PresetJSON(name: String, messageArray: JSONArray): QS300Preset =
        QS300Preset(name, voices = mutableListOf()).apply {
            for (i in 0 until messageArray.length()) {
                val messageString = messageArray.getString(i)
                if (messageString.length > MidiConstants.OFFSET_DEVICE_ID &&
                    messageString[MidiConstants.OFFSET_DEVICE_ID].code.toByte() ==
                    MidiConstants.MODEL_ID_QS300
                ) {
                    voices.add(parseQS300Voice(messageString))
                }
            }
        }

    private fun parseQS300Voice(midiByteString: String): QS300Voice = QS300Voice().apply {
        voiceName = String(
            midiByteString.substring(
                MidiConstants.OFFSET_QS300_DATA_START,
                MidiConstants.OFFSET_QS300_DATA_START + MidiConstants.QS300_VOICE_NAME_SIZE
            ).toByteArray(), Charsets.US_ASCII
        )
        voiceLevel = midiByteString[MidiConstants.OFFSET_QS300_VOICE_LEVEL].code.toByte()
        elementSwitch =
            midiByteString[MidiConstants.OFFSET_QS300_EL_SWITCH].code.toByte()

        for (i in 0 until MidiConstants.QS300_MAX_ELEMENTS) {
            elements.add(parseQS300Element(midiByteString, i))
        }
    }

    private fun parseQS300Element(midiByteString: String, index: Int): QS300Element =
        QS300Element(index).apply {
            val startIndex =
                MidiConstants.OFFSET_QS300_ELEMENT_DATA_START + (index * MidiConstants.QS300_ELEMENT_DATA_SIZE)
            for (i in startIndex until startIndex + MidiConstants.QS300_ELEMENT_DATA_SIZE) {
                val baseAddress =
                    (i - MidiConstants.OFFSET_QS300_DATA_START - (index * MidiConstants.QS300_ELEMENT_DATA_SIZE)).toUByte()
                val elementParam = QS300ElementParameter::getBaseAddress findBy baseAddress
                val property = elementParam?.reflectedField
                setProperty(property, midiByteString[i].code.toByte())
            }
        }

    // TODO: Figure out best practice for scheduling messages
    private fun getBulkDumpTimeStamp(messageIndex: Int): Long =
        System.nanoTime() + messageIndex * MidiConstants.SEND_SCHEDULE_INTERVAL_NANO

    companion object {
        private const val TAG = "MidiDataUtility"
    }
}