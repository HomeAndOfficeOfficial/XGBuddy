package com.example.xgbuddy.data.xg

import com.example.xgbuddy.data.ControlParameter

class DrumControlParameter(
    override val name: String,
    override val addr: UByte,
    override var value: Int,
    override val min: Int,
    override val max: Int,
    override val default: Int
) : ControlParameter() {

    constructor(drumParameter: DrumVoiceParameter, value: Byte) : this(
        drumParameter.name,
        drumParameter.ordinal.toUByte(),
        value.toInt(),
        0,
        drumParameter.max.toInt(),
        drumParameter.default.toInt()
    )
}