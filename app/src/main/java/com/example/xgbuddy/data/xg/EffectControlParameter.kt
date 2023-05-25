package com.example.xgbuddy.data.xg

import com.example.xgbuddy.data.ControlParameter

class EffectControlParameter(
    override val name: String,
    override val addr: UByte,
    override var value: Int,
    override val min: Int,
    override val max: Int,
    override val default: Int
) : ControlParameter() {

    // TODO: Will probably have to have an additional field to account for
    //  large variation values

    // Reverb parameter
    constructor(
        effectParam: EffectParameterData,
        reverbParameter: EffectParameter?,
        value: Int,
        defaultValue: Int
    ) : this(
        effectParam.name,
        effectParam.addrLo.toUByte(),
        value,
        reverbParameter?.min ?: effectParam.min.toInt(),
        reverbParameter?.max ?: effectParam.max.toInt(),
        defaultValue
    )
}