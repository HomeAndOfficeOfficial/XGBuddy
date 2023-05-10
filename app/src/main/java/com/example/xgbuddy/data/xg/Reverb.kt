package com.example.xgbuddy.data.xg

import com.example.xgbuddy.R

enum class Reverb(
    val nameRes: Int,
    val msb: Byte,
    val lsb: Byte,
    val parameterList: Array<EffectParameter?>?
) {
    NO_EFFECT(R.string.vari_no_effect, 0, 0, VariationParameterLists.basicReverb),
    HALL1(R.string.vari_hall_1, 0x01, 0, VariationParameterLists.basicReverb),
    HALL2(R.string.vari_hall_2, 0x01, 0x01, VariationParameterLists.basicReverb),
    ROOM1(R.string.vari_room_1, 0x02, 0, VariationParameterLists.basicReverb),
    ROOM2(R.string.vari_room_2, 0x02, 0x01, VariationParameterLists.basicReverb),
    ROOM3(R.string.vari_room_3, 0x02, 0x02, VariationParameterLists.basicReverb),
    STAGE1(R.string.vari_stage_1, 0x03, 0, VariationParameterLists.basicReverb),
    STAGE2(R.string.vari_stage_2, 0x03, 0x01, VariationParameterLists.basicReverb),
    PLATE(R.string.vari_plate, 0x04, 0, VariationParameterLists.basicReverb),
    WHITE_ROOM(R.string.reverb_white, 0x10, 0, VariationParameterLists.dimensionalReverb),
    TUNNEL(R.string.reverb_basement, 0x13, 0, VariationParameterLists.dimensionalReverb)
}