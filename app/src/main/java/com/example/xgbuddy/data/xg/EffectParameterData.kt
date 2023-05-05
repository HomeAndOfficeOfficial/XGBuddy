package com.example.xgbuddy.data.xg

enum class EffectParameterData(
    val addrLo: Byte,
    val min: Byte = 0,
    val max: Byte = 127,
    val default: Byte = 0,
    val size: Byte = 1
) {
    REVERB_TYPE(0, default = 1, size = 2),
    REVERB_PARAM_1(2),
    REVERB_PARAM_2(3),
    REVERB_PARAM_3(4),
    REVERB_PARAM_4(5),
    REVERB_PARAM_5(6),
    REVERB_PARAM_6(7),
    REVERB_PARAM_7(8),
    REVERB_PARAM_8(9),
    REVERB_PARAM_9(0x0a),
    REVERB_PARAM_10(0x0b),
    REVERB_RETURN(0x0c, default = 0x40),
    REVERB_PAN(0x0d, min = 1, default = 0x40),
    REVERB_PARAM_11(0x10),
    REVERB_PARAM_12(0x11),
    REVERB_PARAM_13(0x12),
    REVERB_PARAM_14(0x13),
    REVERB_PARAM_15(0x14),
    REVERB_PARAM_16(0x15),
    CHORUS_TYPE(0x20, default = 0x41, size = 2),
    CHORUS_PARAMETER_1(0x22),
    CHORUS_PARAMETER_2(0x23),
    CHORUS_PARAMETER_3(0x24),
    CHORUS_PARAMETER_4(0x25),
    CHORUS_PARAMETER_5(0x26),
    CHORUS_PARAMETER_6(0x27),
    CHORUS_PARAMETER_7(0x28),
    CHORUS_PARAMETER_8(0x29),
    CHORUS_PARAMETER_9(0x2a),
    CHORUS_PARAMETER_10(0x2b),
    CHORUS_PARAMETER_11(0x30),
    CHORUS_PARAMETER_12(0x31),
    CHORUS_PARAMETER_13(0x32),
    CHORUS_PARAMETER_14(0x33),
    CHORUS_PARAMETER_15(0x34),
    CHORUS_PARAMETER_16(0x35),
    CHORUS_RETURN(0x2c, default = 0x40),
    CHORUS_PAN(0x2d, min = 1, default = 40),
    SEND_CHOR_TO_REV(0x2e),
    VARIATION_TYPE(0x40, default = 5, size = 2),
    VARIATION_PARAM_1(0x42, size = 2),
    VARIATION_PARAM_2(0x44, size = 2),
    VARIATION_PARAM_3(0x46, size = 2),
    VARIATION_PARAM_4(0x48, size = 2),
    VARIATION_PARAM_5(0x4a, size = 2),
    VARIATION_PARAM_6(0x4c, size = 2),
    VARIATION_PARAM_7(0x4e, size = 2),
    VARIATION_PARAM_8(0x50, size = 2),
    VARIATION_PARAM_9(0x52, size = 2),
    VARIATION_PARAM_10(0x54, size = 2),
    VARIATION_PARAM_11(0x70),
    VARIATION_PARAM_12(0x71),
    VARIATION_PARAM_13(0x72),
    VARIATION_PARAM_14(0x73),
    VARIATION_PARAM_15(0x74),
    VARIATION_PARAM_16(0x75),
    VARIATION_RETURN(0x56, default = 0x40),
    SEND_VARI_TO_REV(0x58),
    SEND_VARI_TO_CHOR(0x59),
    VARIATION_CONNECTION(0x5a, max = 1),
    VARIATION_PART(0x5b, default = 0x7f), // 0...0x0f = Part 1...16, 0x40 = A/D, 0x7f = off
    VARIATION_PAN(0x57, min = 1, default = 0x40),
    MW_VARI_CTRL_DEPTH(0x5c, default = 0x40),
    BEND_VARI_CTRL_DEPTH(0x5d, default = 0x40),
    CAT_VARI_CTRL_DEPTH(0x5e, default = 0x40),
    AC1_VARI_CTRL_DEPTH(0x5f, default = 0x40),
    AC2_VARI_CTRL_DEPTH(0x60, default = 0x40)


}