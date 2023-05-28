package com.example.xgbuddy.data.xg

import com.example.xgbuddy.util.EnumFinder.findBy

class Reverb(reverbType: ReverbType) : Effect(
    reverbType.nameRes,
    reverbType.msb,
    reverbType.lsb,
    reverbType.parameterList
) {

    var reverbType = reverbType
        set(value) {
            field = value
            updateEffectType(value)
            defaultValues = (ReverbType::nameRes findBy nameRes)!!.parameterDefaults
            initializeDefaultValues()
        }

    override val baseAddr: Byte = 0
    override var defaultValues: IntArray? = (ReverbType::nameRes findBy nameRes)!!.parameterDefaults

    init {
        initializeDefaultValues()
    }

    var revReturn = EffectParameterData.REVERB_RETURN.default
    var revPan = EffectParameterData.REVERB_PAN.default
}