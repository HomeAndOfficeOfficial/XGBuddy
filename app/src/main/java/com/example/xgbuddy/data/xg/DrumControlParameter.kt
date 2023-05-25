package com.example.xgbuddy.data.xg

import com.example.xgbuddy.data.ControlParameter

class DrumControlParameter(
    override val name: String,
    override val addr: UByte,
    override var value: Int,
    override var min: Int,
    override var max: Int,
    override var default: Int
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