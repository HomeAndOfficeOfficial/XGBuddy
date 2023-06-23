package com.example.xgbuddy.data.voiceselect

import com.example.xgbuddy.data.qs300.QS300Preset
import com.example.xgbuddy.data.xg.SFXNormalVoice
import com.example.xgbuddy.data.xg.XGDrumKit
import com.example.xgbuddy.data.xg.XGNormalVoice

enum class VoiceListCategory(val enumName: String) {
    XG_NORMAL(XGNormalVoice::class.java.name),
    XG_DRUM(XGDrumKit::class.java.name),
    SFX(SFXNormalVoice::class.java.name),
    QS300(QS300Preset::class.java.name)
}