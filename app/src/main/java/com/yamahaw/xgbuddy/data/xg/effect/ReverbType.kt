package com.yamahaw.xgbuddy.data.xg.effect

import com.yamahaw.xgbuddy.R
import java.util.*

enum class ReverbType(
    val nameRes: Int,
    val msb: Byte,
    val lsb: Byte,
    val parameterList: EnumMap<EffectParameterData, EffectParameter>?,
    val parameterDefaults: IntArray?
) {
    NO_EFFECT(R.string.vari_no_effect, 0, 0, null, null),
    HALL1(
        R.string.vari_hall_1,
        0x01,
        0,
        VariationParameterLists.basicReverb,
        EffectParameterDefaults.hall1Defaults
    ),
    HALL2(
        R.string.vari_hall_2,
        0x01,
        0x01,
        VariationParameterLists.basicReverb,
        EffectParameterDefaults.hall2Defaults
    ),
    ROOM1(
        R.string.vari_room_1,
        0x02,
        0,
        VariationParameterLists.basicReverb,
        EffectParameterDefaults.room1Defaults
    ),
    ROOM2(
        R.string.vari_room_2,
        0x02,
        0x01,
        VariationParameterLists.basicReverb,
        EffectParameterDefaults.room2Defaults
    ),
    ROOM3(
        R.string.vari_room_3,
        0x02,
        0x02,
        VariationParameterLists.basicReverb,
        EffectParameterDefaults.room3Defaults
    ),
    STAGE1(
        R.string.vari_stage_1,
        0x03,
        0,
        VariationParameterLists.basicReverb,
        EffectParameterDefaults.stage1Defaults
    ),
    STAGE2(
        R.string.vari_stage_2,
        0x03,
        0x01,
        VariationParameterLists.basicReverb,
        EffectParameterDefaults.stage2Defaults
    ),
    PLATE(
        R.string.vari_plate,
        0x04,
        0,
        VariationParameterLists.basicReverb,
        EffectParameterDefaults.plateDefaults
    ),
    WHITE_ROOM(
        R.string.reverb_white,
        0x10,
        0,
        VariationParameterLists.dimensionalReverb,
        EffectParameterDefaults.whiteRoomDefaults
    ),
    TUNNEL(
        R.string.reverb_tunnel,
        0x11,
        0,
        VariationParameterLists.dimensionalReverb,
        EffectParameterDefaults.tunnelDefaults
    )
    /**
     * So why does Tunnel have a string resource called "reverb_basement" as its title? Well,
     * I messed up. I added an enum entry called "TUNNEL", gave it the address of the Basement
     * preset, and gave it the default values of the tunnel preset. Then I forgot to add a BASEMENT
     * enum entry. So I just changed the address to match the actual Tunnel address and I'm going
     * to pretend Basement doesn't exist for now. I can add it some other time.
     */
}