package com.example.xgbuddy.data

/**
 * WIll probably need special subclasses for every type of parameter change
 * since the messages are constructed slightly differently. The converter
 * utility will make the correct type of instance based on the type of parameter
 * it receives. Then the control view can just use whatever message is associated
 * with the implementation of this method.
 */

class QS300ControlParameter(
    override val name: String,
    override val addr: UByte,
    override var value: Byte,
    override val min: Byte,
    override val max: Byte,
    override val default: Byte
) : ControlParameter() {

    constructor(elementParameter: QS300ElementParameter, value: Byte) : this(
        elementParameter.name,
        elementParameter.baseAddress,
        value,
        elementParameter.min,
        elementParameter.max,
        elementParameter.default
    )

    override fun getParamChangeMessage(): MidiMessage {
        val addrLo = (addr - MidiConstants.OFFSET_QS300_ELEMENT_PARAM_CHANGE_ADDR).toByte()
        // TODO... Probably make these constants
        val midiBytes = byteArrayOf(-16, 67, 16, 75, 46, 0, addrLo, value, -9)
        return MidiMessage(midiBytes, System.nanoTime())
    }
}