package com.example.xgbuddy.data.xg

import com.example.xgbuddy.data.ControlParameter
import com.example.xgbuddy.data.MidiMessage
import com.example.xgbuddy.data.gm.MidiParameter

class XGControlParameter(
    override val name: String,
    override val addr: UByte,
    override var value: Int,
    override val min: Byte,
    override val max: Byte,
    override val default: Byte
) : ControlParameter() {

    constructor(elementParameter: MidiParameter, value: Byte) : this(
        elementParameter.name,
        elementParameter.addrLo.toUByte(),
        value.toInt(),
        elementParameter.min,
        elementParameter.max,
        elementParameter.default
    )
}