package com.example.xgbuddy.data.xg

import com.example.xgbuddy.util.EnumFinder.findBy

class Reverb(reverbType: ReverbType) : Effect(
    reverbType.nameRes,
    reverbType.msb,
    reverbType.lsb,
    reverbType.parameterList
) {

    var reverbMsb = ReverbType.HALL1.msb
    var reverbLsb = ReverbType.HALL1.lsb
    var param1 = EffectParameterData.REVERB_PARAM_1.default
    var param2 = EffectParameterData.REVERB_PARAM_2.default
    var param3 = EffectParameterData.REVERB_PARAM_3.default
    var param4 = EffectParameterData.REVERB_PARAM_4.default
    var param5 = EffectParameterData.REVERB_PARAM_5.default
    var param6 = EffectParameterData.REVERB_PARAM_6.default
    var param7 = EffectParameterData.REVERB_PARAM_7.default
    var param8 = EffectParameterData.REVERB_PARAM_8.default
    var param9 = EffectParameterData.REVERB_PARAM_9.default
    var param10 = EffectParameterData.REVERB_PARAM_10.default
    var param11 = EffectParameterData.REVERB_PARAM_11.default
    var param12 = EffectParameterData.REVERB_PARAM_12.default
    var param13 = EffectParameterData.REVERB_PARAM_13.default
    var param14 = EffectParameterData.REVERB_PARAM_14.default
    var param15 = EffectParameterData.REVERB_PARAM_15.default
    var param16 = EffectParameterData.REVERB_PARAM_16.default
    var revReturn = EffectParameterData.REVERB_RETURN.default
    var revPan = EffectParameterData.REVERB_PAN.default

    override fun getEffectDefaults(): IntArray? =
        (ReverbType::nameRes findBy nameRes)!!.parameterDefaults

}