package com.example.xgbuddy.data.xg

import com.example.xgbuddy.data.ControlParameter
import com.example.xgbuddy.data.gm.MidiParameter

class XGControlParameter(
    override val name: String,
    override val addr: UByte,
    override var value: Int,
    override val min: Int,
    override val max: Int,
    override val default: Int
) : ControlParameter() {

    constructor(elementParameter: MidiParameter, value: Byte) : this(
        elementParameter.name,
        elementParameter.addrLo.toUByte(),
        value.toInt(),
        elementParameter.min.toInt(),
        elementParameter.max.toInt(),
        elementParameter.default.toInt()
    )
}