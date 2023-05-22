package com.example.xgbuddy.data.xg

import com.example.xgbuddy.util.EnumFinder.findBy

class Variation(variationType: VariationType) : Effect(
    variationType.nameRes,
    variationType.msb,
    variationType.lsb,
    variationType.parameterList
) {

    var variMsb = VariationType.DELAY_LCR.msb
    var variLsb = VariationType.DELAY_LCR.lsb
    var param1: Int = EffectParameterData.VARIATION_PARAM_1.default.toInt()
    var param2: Int = EffectParameterData.VARIATION_PARAM_2.default.toInt()
    var param3: Int = EffectParameterData.VARIATION_PARAM_3.default.toInt()
    var param4: Int = EffectParameterData.VARIATION_PARAM_4.default.toInt()
    var param5: Int = EffectParameterData.VARIATION_PARAM_5.default.toInt()
    var param6: Int = EffectParameterData.VARIATION_PARAM_6.default.toInt()
    var param7: Int = EffectParameterData.VARIATION_PARAM_7.default.toInt()
    var param8: Int = EffectParameterData.VARIATION_PARAM_8.default.toInt()
    var param9: Int = EffectParameterData.VARIATION_PARAM_9.default.toInt()
    var param10: Int = EffectParameterData.VARIATION_PARAM_10.default.toInt()
    var param11 = EffectParameterData.VARIATION_PARAM_11.default
    var param12 = EffectParameterData.VARIATION_PARAM_12.default
    var param13 = EffectParameterData.VARIATION_PARAM_13.default
    var param14 = EffectParameterData.VARIATION_PARAM_14.default
    var param15 = EffectParameterData.VARIATION_PARAM_15.default
    var param16 = EffectParameterData.VARIATION_PARAM_16.default
    var variReturn = EffectParameterData.VARIATION_RETURN.default
    var variPan = EffectParameterData.VARIATION_PAN.default
    var sendReverb = EffectParameterData.SEND_VARI_TO_REV.default
    var sendChorus = EffectParameterData.SEND_VARI_TO_CHOR.default
    var part: Byte = 0x7f // 0...0x0f = Part 1...16, 0x40 = A/D, 0x7f = off
    var connection: Byte = 0
    var modWheelDepth = EffectParameterData.MW_VARI_CTRL_DEPTH.default
    var bendDepth = EffectParameterData.BEND_VARI_CTRL_DEPTH.default
    var catDepth = EffectParameterData.CAT_VARI_CTRL_DEPTH.default
    var ac1Depth = EffectParameterData.AC1_VARI_CTRL_DEPTH.default
    var ac2Depth = EffectParameterData.AC2_VARI_CTRL_DEPTH.default

    override fun getEffectDefaults(): IntArray? =
        (VariationType::nameRes findBy nameRes)!!.parameterDefaults

}