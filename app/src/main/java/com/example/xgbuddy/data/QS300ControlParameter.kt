package com.example.xgbuddy.data

/**
 * WIll probably need special subclasses for every type of parameter change
 * since the messages are constructed slightly differently. The converter
 * utility will make the correct type of instance based on the type of parameter
 * it receives. Then the control view can just use whatever message is associated
 * with the implementation of this method.
 */

class QS300ControlParameter : ControlParameter() {
    override fun getParamChangeMessage(): MidiMessage {
        TODO("Not yet implemented")
    }
}