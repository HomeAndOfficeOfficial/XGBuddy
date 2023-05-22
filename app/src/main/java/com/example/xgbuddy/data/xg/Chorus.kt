package com.example.xgbuddy.data.xg

import com.example.xgbuddy.util.EnumFinder.findBy

class Chorus(chorusType: ChorusType) : Effect(
    chorusType.nameRes,
    chorusType.msb,
    chorusType.lsb,
    chorusType.getParameterList()
) {

    var chorusMsb = ChorusType.CHORUS1.msb
    var chorusLsb = ChorusType.CHORUS1.lsb
    var param1 = EffectParameterData.CHORUS_PARAMETER_1.default
    var param2 = EffectParameterData.CHORUS_PARAMETER_2.default
    var param3 = EffectParameterData.CHORUS_PARAMETER_3.default
    var param4 = EffectParameterData.CHORUS_PARAMETER_4.default
    var param5 = EffectParameterData.CHORUS_PARAMETER_5.default
    var param6 = EffectParameterData.CHORUS_PARAMETER_6.default
    var param7 = EffectParameterData.CHORUS_PARAMETER_7.default
    var param8 = EffectParameterData.CHORUS_PARAMETER_8.default
    var param9 = EffectParameterData.CHORUS_PARAMETER_9.default
    var param10 = EffectParameterData.CHORUS_PARAMETER_10.default
    var param11 = EffectParameterData.CHORUS_PARAMETER_11.default
    var param12 = EffectParameterData.CHORUS_PARAMETER_12.default
    var param13 = EffectParameterData.CHORUS_PARAMETER_13.default
    var param14 = EffectParameterData.CHORUS_PARAMETER_14.default
    var param15 = EffectParameterData.CHORUS_PARAMETER_15.default
    var param16 = EffectParameterData.CHORUS_PARAMETER_16.default
    var chorusReturn = EffectParameterData.CHORUS_RETURN.default
    var chorusPan = EffectParameterData.CHORUS_PAN.default
    var sendReverb = EffectParameterData.SEND_CHOR_TO_REV.default

    override fun getEffectDefaults(): IntArray? =
        (ChorusType::nameRes findBy nameRes)!!.parameterDefaults

}