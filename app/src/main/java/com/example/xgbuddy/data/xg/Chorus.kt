package com.example.xgbuddy.data.xg

import com.example.xgbuddy.util.EnumFinder.findBy

class Chorus(val chorusType: ChorusType) : Effect(
    chorusType.nameRes,
    chorusType.msb,
    chorusType.lsb,
    chorusType.getParameterList()
) {

    override val defaultValues: IntArray? = (ChorusType::nameRes findBy nameRes)!!.parameterDefaults

    init {
        initializeDefaultValues()
    }

    var chorusReturn = EffectParameterData.CHORUS_RETURN.default
    var chorusPan = EffectParameterData.CHORUS_PAN.default
    var sendReverb = EffectParameterData.SEND_CHOR_TO_REV.default
}