package com.yamahaw.xgbuddy.data.qs300

import androidx.annotation.Keep
import com.google.gson.Gson

@Keep
data class QS300Preset(val name: String, val voices: MutableList<QS300Voice>) {
    fun clone(): QS300Preset {
        val presetString = Gson().toJson(this, QS300Preset::class.java)
        return Gson().fromJson<QS300Preset>(presetString, QS300Preset::class.java)
    }
}