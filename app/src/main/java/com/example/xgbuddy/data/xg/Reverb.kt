package com.example.xgbuddy.data.xg

import com.example.xgbuddy.R

enum class Reverb(
    val nameRes: Int,
    val msb: Byte,
    val lsb: Byte,
    val parameterList: Array<EffectParameter?>?,
    val parameterDefaults: IntArray?
) {
    NO_EFFECT(R.string.vari_no_effect, 0, 0, null, null),
    HALL1(R.string.vari_hall_1, 0x01, 0, VariationParameterLists.basicReverb, EffectParameterDefaults.hall1Defaults),
    HALL2(R.string.vari_hall_2, 0x01, 0x01, VariationParameterLists.basicReverb, EffectParameterDefaults.hall2Defaults),
    ROOM1(R.string.vari_room_1, 0x02, 0, VariationParameterLists.basicReverb, EffectParameterDefaults.room1Defaults),
    ROOM2(R.string.vari_room_2, 0x02, 0x01, VariationParameterLists.basicReverb, EffectParameterDefaults.room2Defaults),
    ROOM3(R.string.vari_room_3, 0x02, 0x02, VariationParameterLists.basicReverb, EffectParameterDefaults.room3Defaults),
    STAGE1(R.string.vari_stage_1, 0x03, 0, VariationParameterLists.basicReverb, EffectParameterDefaults.stage1Defaults),
    STAGE2(R.string.vari_stage_2, 0x03, 0x01, VariationParameterLists.basicReverb, EffectParameterDefaults.stage2Defaults),
    PLATE(R.string.vari_plate, 0x04, 0, VariationParameterLists.basicReverb, EffectParameterDefaults.plateDefaults),
    WHITE_ROOM(R.string.reverb_white, 0x10, 0, VariationParameterLists.dimensionalReverb, EffectParameterDefaults.whiteRoomDefaults),
    TUNNEL(R.string.reverb_basement, 0x13, 0, VariationParameterLists.dimensionalReverb, EffectParameterDefaults.tunnelDefaults)
}