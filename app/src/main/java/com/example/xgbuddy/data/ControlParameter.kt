package com.example.xgbuddy.data

abstract class ControlParameter(
    val name: String,
    val value: Byte,
    val min: Byte,
    val max: Byte,
    val default: Byte
) {

    var formatString: String? = null

    abstract fun getParamChangeMessage(): MidiMessage
}