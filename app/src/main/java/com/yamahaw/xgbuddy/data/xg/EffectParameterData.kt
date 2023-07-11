package com.yamahaw.xgbuddy.data.xg

import com.yamahaw.xgbuddy.R
import com.yamahaw.xgbuddy.util.DataFormatUtil
import kotlin.reflect.KMutableProperty

enum class EffectParameterData(
    val resId: Int,
    val addrLo: Byte,
    val min: Byte = 0,
    val max: Byte = 127,
    val default: Byte = 0,
    val size: Byte = 1,
    val reflectedField: KMutableProperty<Byte>? = null,
    val reflectedBigField: KMutableProperty<Int>? = null,
    val paramIndex: Int? = null,
    val formatter: DataFormatUtil.DataAssignFormatter = DataFormatUtil.signed127Formatter
) {
    REVERB_TYPE(R.string.fxpc_rev_type, 0, default = 1, size = 2),
    REVERB_PARAM_1(R.string.fxpc_rev_p1, 2, reflectedBigField = Effect::param1, paramIndex = 0),
    REVERB_PARAM_2(R.string.fxpc_rev_p2, 3, reflectedBigField = Effect::param2, paramIndex = 1),
    REVERB_PARAM_3(R.string.fxpc_rev_p3, 4, reflectedBigField = Effect::param3, paramIndex = 2),
    REVERB_PARAM_4(R.string.fxpc_rev_p4, 5, reflectedBigField = Effect::param4, paramIndex = 3),
    REVERB_PARAM_5(R.string.fxpc_rev_p5, 6, reflectedBigField = Effect::param5, paramIndex = 4),
    REVERB_PARAM_6(R.string.fxpc_rev_p6, 7, reflectedBigField = Effect::param6, paramIndex = 5),
    REVERB_PARAM_7(R.string.fxpc_rev_p7, 8, reflectedBigField = Effect::param7, paramIndex = 6),
    REVERB_PARAM_8(R.string.fxpc_rev_p8, 9, reflectedBigField = Effect::param8, paramIndex = 7),
    REVERB_PARAM_9(R.string.fxpc_rev_p9, 0x0a, reflectedBigField = Effect::param9, paramIndex = 8),
    REVERB_PARAM_10(
        R.string.fxpc_rev_p10,
        0x0b,
        reflectedBigField = Effect::param10,
        paramIndex = 9
    ),
    REVERB_RETURN(R.string.fxpc_rev_ret, 0x0c, default = 0x40, reflectedField = Reverb::revReturn),
    REVERB_PAN(
        R.string.fxpc_rev_pan,
        0x0d,
        min = 1,
        default = 0x40,
        reflectedField = Reverb::revPan
    ),
    REVERB_PARAM_11(
        R.string.fxpc_rev_p11,
        0x10,
        reflectedBigField = Effect::param11,
        paramIndex = 10
    ),
    REVERB_PARAM_12(
        R.string.fxpc_rev_p12,
        0x11,
        reflectedBigField = Effect::param12,
        paramIndex = 11
    ),
    REVERB_PARAM_13(
        R.string.fxpc_rev_p13,
        0x12,
        reflectedBigField = Effect::param13,
        paramIndex = 12
    ),
    REVERB_PARAM_14(
        R.string.fxpc_rev_p14,
        0x13,
        reflectedBigField = Effect::param14,
        paramIndex = 13
    ),
    REVERB_PARAM_15(
        R.string.fxpc_rev_p15,
        0x14,
        reflectedBigField = Effect::param15,
        paramIndex = 14
    ),
    REVERB_PARAM_16(
        R.string.fxpc_rev_p16,
        0x15,
        reflectedBigField = Effect::param16,
        paramIndex = 15
    ),
    CHORUS_TYPE(R.string.fxpc_chor_type, 0x20, default = 0x41, size = 2),
    CHORUS_PARAMETER_1(
        R.string.fxpc_chor_p1,
        0x22,
        reflectedBigField = Effect::param1,
        paramIndex = 0
    ),
    CHORUS_PARAMETER_2(
        R.string.fxpc_chor_p2,
        0x23,
        reflectedBigField = Effect::param2,
        paramIndex = 1
    ),
    CHORUS_PARAMETER_3(
        R.string.fxpc_chor_p3,
        0x24,
        reflectedBigField = Effect::param3,
        paramIndex = 2
    ),
    CHORUS_PARAMETER_4(
        R.string.fxpc_chor_p4,
        0x25,
        reflectedBigField = Effect::param4,
        paramIndex = 3
    ),
    CHORUS_PARAMETER_5(
        R.string.fxpc_chor_p5,
        0x26,
        reflectedBigField = Effect::param5,
        paramIndex = 4
    ),
    CHORUS_PARAMETER_6(
        R.string.fxpc_chor_p6,
        0x27,
        reflectedBigField = Effect::param6,
        paramIndex = 5
    ),
    CHORUS_PARAMETER_7(
        R.string.fxpc_chor_p7,
        0x28,
        reflectedBigField = Effect::param7,
        paramIndex = 6
    ),
    CHORUS_PARAMETER_8(
        R.string.fxpc_chor_p8,
        0x29,
        reflectedBigField = Effect::param8,
        paramIndex = 7
    ),
    CHORUS_PARAMETER_9(
        R.string.fxpc_chor_p9,
        0x2a,
        reflectedBigField = Effect::param9,
        paramIndex = 8
    ),
    CHORUS_PARAMETER_10(
        R.string.fxpc_chor_p10,
        0x2b,
        reflectedBigField = Effect::param10,
        paramIndex = 9
    ),
    CHORUS_PARAMETER_11(
        R.string.fxpc_chor_p11,
        0x30,
        reflectedBigField = Effect::param11,
        paramIndex = 10
    ),
    CHORUS_PARAMETER_12(
        R.string.fxpc_chor_p12,
        0x31,
        reflectedBigField = Effect::param12,
        paramIndex = 11
    ),
    CHORUS_PARAMETER_13(
        R.string.fxpc_chor_p13,
        0x32,
        reflectedBigField = Effect::param13,
        paramIndex = 12
    ),
    CHORUS_PARAMETER_14(
        R.string.fxpc_chor_p14,
        0x33,
        reflectedBigField = Effect::param14,
        paramIndex = 13
    ),
    CHORUS_PARAMETER_15(
        R.string.fxpc_chor_p15,
        0x34,
        reflectedBigField = Effect::param15,
        paramIndex = 14
    ),
    CHORUS_PARAMETER_16(
        R.string.fxpc_chor_p16,
        0x35,
        reflectedBigField = Effect::param16,
        paramIndex = 15
    ),
    CHORUS_RETURN(
        R.string.fxpc_chor_ret,
        0x2c,
        default = 0x40,
        reflectedField = Chorus::chorusReturn
    ),
    CHORUS_PAN(
        R.string.fxpc_chor_pan,
        0x2d,
        min = 1,
        default = 40,
        reflectedField = Chorus::chorusPan
    ),
    SEND_CHOR_TO_REV(R.string.fxpc_chor_send_rev, 0x2e, reflectedField = Chorus::sendReverb),
    VARIATION_TYPE(R.string.fxpc_vari_type, 0x40, default = 5, size = 2),
    VARIATION_PARAM_1(
        R.string.fxpc_vari_p1,
        0x42,
        size = 2,
        reflectedBigField = Effect::param1,
        paramIndex = 0
    ),
    VARIATION_PARAM_2(
        R.string.fxpc_vari_p2,
        0x44,
        size = 2,
        reflectedBigField = Effect::param2,
        paramIndex = 1
    ),
    VARIATION_PARAM_3(
        R.string.fxpc_vari_p3,
        0x46,
        size = 2,
        reflectedBigField = Effect::param3,
        paramIndex = 2
    ),
    VARIATION_PARAM_4(
        R.string.fxpc_vari_p4,
        0x48,
        size = 2,
        reflectedBigField = Effect::param4,
        paramIndex = 3
    ),
    VARIATION_PARAM_5(
        R.string.fxpc_vari_p5,
        0x4a,
        size = 2,
        reflectedBigField = Effect::param5,
        paramIndex = 4
    ),
    VARIATION_PARAM_6(
        R.string.fxpc_vari_p6,
        0x4c,
        size = 2,
        reflectedBigField = Effect::param6,
        paramIndex = 5
    ),
    VARIATION_PARAM_7(
        R.string.fxpc_vari_p7,
        0x4e,
        size = 2,
        reflectedBigField = Effect::param7,
        paramIndex = 6
    ),
    VARIATION_PARAM_8(
        R.string.fxpc_vari_p8,
        0x50,
        size = 2,
        reflectedBigField = Effect::param8,
        paramIndex = 7
    ),
    VARIATION_PARAM_9(
        R.string.fxpc_vari_p9,
        0x52,
        size = 2,
        reflectedBigField = Effect::param9,
        paramIndex = 8
    ),
    VARIATION_PARAM_10(
        R.string.fxpc_vari_p10,
        0x54,
        size = 2,
        reflectedBigField = Effect::param10, paramIndex = 9
    ),
    VARIATION_PARAM_11(
        R.string.fxpc_vari_p11,
        0x70,
        reflectedBigField = Effect::param11,
        paramIndex = 10
    ),
    VARIATION_PARAM_12(
        R.string.fxpc_vari_p12,
        0x71,
        reflectedBigField = Effect::param12,
        paramIndex = 11
    ),
    VARIATION_PARAM_13(
        R.string.fxpc_vari_p13,
        0x72,
        reflectedBigField = Effect::param13,
        paramIndex = 12
    ),
    VARIATION_PARAM_14(
        R.string.fxpc_vari_p14,
        0x73,
        reflectedBigField = Effect::param14,
        paramIndex = 13
    ),
    VARIATION_PARAM_15(
        R.string.fxpc_vari_p15,
        0x74,
        reflectedBigField = Effect::param15,
        paramIndex = 14
    ),
    VARIATION_PARAM_16(
        R.string.fxpc_vari_p16,
        0x75,
        reflectedBigField = Effect::param16,
        paramIndex = 15
    ),
    VARIATION_RETURN(
        R.string.fxpc_vari_ret,
        0x56,
        default = 0x40,
        reflectedField = Variation::variReturn
    ),
    SEND_VARI_TO_REV(R.string.fxpc_vari_send_rev, 0x58, reflectedField = Variation::sendReverb),
    SEND_VARI_TO_CHOR(R.string.fxpc_vari_send_chor, 0x59, reflectedField = Variation::sendChorus),
    VARIATION_CONNECTION(
        R.string.fxpc_vari_conn,
        0x5a,
        max = 1,
        reflectedField = Variation::connection
    ),
    VARIATION_PART(
        R.string.fxpc_vari_part,
        0x5b,
        min = 0,
        max = 17,
        default = 17, reflectedField = Variation::part
    ), // 0...0x0f = Part 1...16, 0x40 = A/D, 0x7f = off
    VARIATION_PAN(
        R.string.fxpc_vari_pan,
        0x57,
        min = 1,
        default = 0x40,
        reflectedField = Variation::variPan
    ),
    MW_VARI_CTRL_DEPTH(
        R.string.fxpc_vari_mw_ctrl,
        0x5c,
        default = 0x40,
        reflectedField = Variation::modWheelDepth
    ),
    BEND_VARI_CTRL_DEPTH(
        R.string.fxpc_vari_bend_ctrl,
        0x5d,
        default = 0x40,
        reflectedField = Variation::bendDepth
    ),
    CAT_VARI_CTRL_DEPTH(
        R.string.fxpc_vari_cat_ctrl,
        0x5e,
        default = 0x40,
        reflectedField = Variation::catDepth
    ),
    AC1_VARI_CTRL_DEPTH(
        R.string.fxpc_vari_ac1_ctrl,
        0x5f,
        default = 0x40,
        reflectedField = Variation::ac1Depth
    ),
    AC2_VARI_CTRL_DEPTH(
        R.string.fxpc_vari_ac2_ctrl,
        0x60,
        default = 0x40,
        reflectedField = Variation::ac2Depth
    )
}