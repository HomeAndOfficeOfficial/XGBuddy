package com.example.xgbuddy.data.xg

import com.example.xgbuddy.data.ControlParameter
import com.example.xgbuddy.data.MidiMessage
import com.example.xgbuddy.data.MidiParameter

class XGControlParameter(
    override val name: String,
    override val addr: UByte,
    override var value: Byte,
    override val min: Byte,
    override val max: Byte,
    override val default: Byte
) : ControlParameter() {

    constructor(elementParameter: MidiParameter, value: Byte) : this(
        elementParameter.name,
        elementParameter.addrLo.toUByte(),
        value,
        elementParameter.min,
        elementParameter.max,
        elementParameter.default
    )

    override fun getParamChangeMessage(): MidiMessage {
        TODO("Not yet implemented")
    }

}