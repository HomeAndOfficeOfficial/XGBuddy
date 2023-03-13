package com.example.xgbuddy.util

import android.content.Context
import android.util.Log
import com.example.xgbuddy.data.*
import com.example.xgbuddy.util.EnumFinder.findBy
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.memberExtensionProperties
import kotlin.reflect.full.memberProperties

class MidiDataUtility @Inject constructor(val context: Context) {

    private var qs300Presets: List<QS300Preset>? = null
    private var qs300PresetsJSON: JSONObject? = null

    fun getQS300Presets() {
        parsePresetsJSON()
//        if (qs300Presets == null) {
//            qs300Presets = parsePresetsJSON()
//        }
//        return qs300Presets!!
    }

    private fun parsePresetsJSON() {
        var jsonString: String
        context.openFileInput(AppConstants.QS300_PRESET_FILE).apply {
            val size = available()
            val buffer = ByteArray(size)
            read(buffer)
            close()
            jsonString = String(buffer)
        }
        qs300PresetsJSON = JSONObject(jsonString)
        val presetKeyIterator = qs300PresetsJSON!!.keys()
//        while (presetKeyIterator.hasNext()) {
        presetKeyIterator.next()
        val presetName = presetKeyIterator.next()
        Log.d(TAG, "Attempting parse of preset with name: $presetName")
        try {
            val messages: JSONArray = qs300PresetsJSON!!.get(presetName) as JSONArray
            val preset = parseQS300PresetJSON(presetName, messages)
        } catch (e: JSONException) {
            Log.e(TAG, "Unable to access preset with key: $presetName")
        }
//        }
    }

    private fun parseQS300PresetJSON(name: String, messageArray: JSONArray): QS300Preset {
        val preset = QS300Preset(name, voices = mutableListOf())
        for (i in 0 until messageArray.length()) {
            val messageString = messageArray.getString(i)
            if (messageString.length > MidiConstants.OFFSET_DEVICE_ID &&
                messageString[MidiConstants.OFFSET_DEVICE_ID].code.toByte() ==
                MidiConstants.MODEL_ID_QS300
            ) {
                val voice = QS300Voice()
                voice.elements.first.apply {
                    for (j in MidiConstants.OFFSET_QS300_ELEMENT_DATA_START until
                            MidiConstants.OFFSET_QS300_ELEMENT_DATA_START
                            + MidiConstants.QS300_ELEMENT_DATA_SIZE) {
                        val addr = (j - MidiConstants.OFFSET_QS300_DATA_START).toUByte()
                        val elementParam = QS300ElementParameter::getBaseAddress findBy addr
                        val property = elementParam?.reflectedField
                        setProperty(property, messageString[j].code.toByte())
                    }
                }
                Log.d(TAG, voice.elements.first.toString())
            }
        }
        return preset
    }

    companion object {
        private const val TAG = "MidiDataUtility"
    }
}