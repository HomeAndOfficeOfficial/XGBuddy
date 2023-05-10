package com.example.xgbuddy.data.qs300

import com.example.xgbuddy.data.ControlParameter
import com.example.xgbuddy.data.MidiConstants
import com.example.xgbuddy.data.MidiMessage

/**
 * WIll probably need special subclasses for every type of parameter change
 * since the messages are constructed slightly differently. The converter
 * utility will make the correct type of instance based on the type of parameter
 * it receives. Then the control view can just use whatever message is associated
 * with the implementation of this method.
 *
 * todo: Actually I'm not totally sure subclasses of ControlParameter are necessary, because I think
 *  there will be different methods for different types of controls (QS/XG). So I don't think there
 *  is any practical use for specific subclasses. I guess as with the other things, just wait and
 *  see what everything looks like once I have a full idea of the shape of things.
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