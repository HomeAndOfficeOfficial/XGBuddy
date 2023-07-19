package com.yamahaw.xgbuddy.data.voiceselect

import com.yamahaw.xgbuddy.data.qs300.QS300Preset
import com.yamahaw.xgbuddy.data.xg.SFXNormalVoice
import com.yamahaw.xgbuddy.data.xg.drum.XGDrumKit
import com.yamahaw.xgbuddy.data.xg.XGNormalVoice

const val QS300_USER_ENUM_NAME = "QS300_USER"

enum class VoiceListCategory(val enumName: String) {
    XG_NORMAL(XGNormalVoice::class.java.name),
    XG_DRUM(XGDrumKit::class.java.name),
    SFX(SFXNormalVoice::class.java.name),
    QS300(QS300Preset::class.java.name),
    QS300_USER(QS300_USER_ENUM_NAME)
}