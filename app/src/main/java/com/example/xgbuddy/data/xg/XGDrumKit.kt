package com.example.xgbuddy.data.xg

import com.example.xgbuddy.R
import com.example.xgbuddy.util.DrumKitVoiceUtil

enum class XGDrumKit(val nameRes: Int, val programNumber: Byte, val drumVoices: List<DrumVoice>) {
    STANDARD_KIT_1(R.string.xgdk_standard1, 0, DrumKitVoiceUtil.standardKit1),
    STANDARD_KIT_2(R.string.xgdk_standard2, 1, DrumKitVoiceUtil.standardKit2),
    ROOM_KIT(R.string.xgdk_room, 8, DrumKitVoiceUtil.roomKit),
    ROCK_KIT(R.string.xgdk_rock, 16, DrumKitVoiceUtil.rockKit),
    ELECTRO_KIT(R.string.xgdk_electro, 24, DrumKitVoiceUtil.electroKit),
    ANALOG_KIT(R.string.xgdk_analog, 25, DrumKitVoiceUtil.analogKit),
    JAZZ_KIT(R.string.xgdk_jazz, 32, DrumKitVoiceUtil.jazzKit),
    BRUSH_KIT(R.string.xgdk_brush, 40, DrumKitVoiceUtil.brushKit),
    CLASSIC_KIT(R.string.xgdk_classic, 48, DrumKitVoiceUtil.classicKit)
}