package com.example.xgbuddy.data.xg

import com.example.xgbuddy.util.EnumFinder.findBy

class Chorus(chorusType: ChorusType) : Effect(
    chorusType.nameRes,
    chorusType.msb,
    chorusType.lsb,
    chorusType.getParameterList()
) {

    var chorusType = chorusType
        set(value) {
            field = value
            defaultValues = (ChorusType::nameRes findBy nameRes)!!.parameterDefaults
            initializeDefaultValues()
        }

    override var defaultValues: IntArray? = (ChorusType::nameRes findBy nameRes)!!.parameterDefaults

    init {
        initializeDefaultValues()
    }

    var chorusReturn = EffectParameterData.CHORUS_RETURN.default
    var chorusPan = EffectParameterData.CHORUS_PAN.default
    var sendReverb = EffectParameterData.SEND_CHOR_TO_REV.default
}