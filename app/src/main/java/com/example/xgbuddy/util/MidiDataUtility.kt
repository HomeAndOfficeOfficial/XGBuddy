package com.example.xgbuddy.util

import android.content.Context
import com.example.xgbuddy.data.AppConstants
import com.example.xgbuddy.data.QS300Preset
import org.json.JSONObject
import javax.inject.Inject

class MidiDataUtility @Inject constructor(val context: Context) {

    private var qs300Presets: List<QS300Preset>? = null
    private var qs300PresetsJSON: JSONObject? = null

    fun getQS300Presets(): List<QS300Preset> {
        if (qs300Presets == null) {
            qs300Presets = parsePresetsJSON()
        }
        return qs300Presets!!
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

    }
}