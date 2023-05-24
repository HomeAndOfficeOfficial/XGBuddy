package com.example.xgbuddy.data.xg

import com.example.xgbuddy.data.ControlParameter

class EffectControlParameter(
    override val name: String,
    override val addr: UByte,
    override var value: Int,
    override val min: Byte,
    override val max: Byte,
    override val default: Byte
) : ControlParameter() {

    // TODO: Will probably have to have an additional field to account for
    //  large variation values

    // Reverb parameter
    constructor(
        effectParam: EffectParameterData,
        reverbParameter: EffectParameter,
        value: Byte,
        defaultValue: Byte
    ) : this(
        effectParam.name,
        effectParam.addrLo.toUByte(),
        value.toInt(),
        reverbParameter.min.toByte(),
        reverbParameter.max.toByte(),
        defaultValue
    )
}