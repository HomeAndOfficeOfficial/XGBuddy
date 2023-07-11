package com.yamahaw.xgbuddy.util

import android.content.Context
import android.util.Log
import com.yamahaw.xgbuddy.R
import com.yamahaw.xgbuddy.data.*
import com.yamahaw.xgbuddy.data.qs300.*
import com.yamahaw.xgbuddy.util.EnumFinder.findBy
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject

class QS300Repository @Inject constructor(val context: Context) {

    private var qs300Presets: List<QS300Preset>? = null
    private var qs300PresetsJSON: JSONObject? = null

    fun getQS300Presets(): List<QS300Preset> {
        if (qs300Presets == null) {
            qs300Presets = parseQS300PresetsJSON()
        }
        return qs300Presets!!
    }

    private fun parseQS300PresetsJSON(): List<QS300Preset> {
        var jsonString: String
        val presets = mutableListOf<QS300Preset>()
        context.resources.openRawResource(R.raw.qs300_presets).apply {
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
                MidiConstants.OFFSET_QS300_BULK_DATA_START,
                MidiConstants.OFFSET_QS300_BULK_DATA_START + MidiConstants.QS300_VOICE_NAME_SIZE
            ).toByteArray(), Charsets.US_ASCII
        )

        for (i in MidiConstants.OFFSET_QS300_BULK_VOICE_COMMON_START until MidiConstants.OFFSET_QS300_BULK_ELEMENT_DATA_START) {
            val addr = (i - MidiConstants.OFFSET_QS300_BULK_DATA_START).toUByte()
            val voiceParam = QS300VoiceParameter::baseAddress findBy addr
            val property = voiceParam?.reflectedField
            setProperty(property, midiByteString[i].code.toByte())
        }

        for (i in 0 until MidiConstants.QS300_MAX_ELEMENTS) {
            elements.add(parseQS300Element(midiByteString, i))
        }
    }

    private fun parseQS300Element(midiByteString: String, index: Int): QS300Element =
        QS300Element(index).apply {
            val startIndex =
                MidiConstants.OFFSET_QS300_BULK_ELEMENT_DATA_START + (index * MidiConstants.QS300_ELEMENT_DATA_SIZE)
            for (i in startIndex until startIndex + MidiConstants.QS300_ELEMENT_DATA_SIZE) {
                val baseAddress =
                    (i - MidiConstants.OFFSET_QS300_BULK_DATA_START - (index * MidiConstants.QS300_ELEMENT_DATA_SIZE)).toUByte()
                val elementParam = QS300ElementParameter::baseAddress findBy baseAddress
                val property = elementParam?.reflectedField
                setProperty(property, midiByteString[i].code.toByte())
            }
        }

    companion object {
        private const val TAG = "QS300Repository"
    }
}