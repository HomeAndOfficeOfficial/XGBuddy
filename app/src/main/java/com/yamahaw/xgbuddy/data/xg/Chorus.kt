package com.yamahaw.xgbuddy.data.xg

import com.yamahaw.xgbuddy.util.EnumFinder.findBy

class Chorus(chorusType: ChorusType) : Effect(
    chorusType.nameRes,
    chorusType.msb,
    chorusType.lsb,
    chorusType.getParameterList()
) {

    var chorusType = chorusType
        set(value) {
            field = value
            updateEffectType(value)
            defaultValues = (ChorusType::nameRes findBy nameRes)!!.parameterDefaults
            initializeDefaultValues()
        }

    override val baseAddr: Byte = 0x20
    override var defaultValues: IntArray? = (ChorusType::nameRes findBy nameRes)!!.parameterDefaults

    init {
        initializeDefaultValues()
    }

    var chorusReturn = EffectParameterData.CHORUS_RETURN.default
    var chorusPan = EffectParameterData.CHORUS_PAN.default
    var sendReverb = EffectParameterData.SEND_CHOR_TO_REV.default
}