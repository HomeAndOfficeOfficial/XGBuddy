package com.example.xgbuddy.data.xg

import com.example.xgbuddy.data.ControlParameter
import com.example.xgbuddy.data.MidiMessage

class DrumControlParameter(
    override val name: String,
    override val addr: UByte,
    override var value: Byte,
    override val min: Byte,
    override val max: Byte,
    override val default: Byte
) : ControlParameter() {

    constructor(drumParameter: DrumVoiceParameter, value: Byte) : this(
        drumParameter.name,
        drumParameter.ordinal.toUByte(),
        value,
        0,
        drumParameter.max,
        drumParameter.default
    )

    override fun getParamChangeMessage(): MidiMessage = MidiMessage(null, 0)
}