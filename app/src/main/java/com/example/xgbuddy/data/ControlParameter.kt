package com.example.xgbuddy.data

abstract class ControlParameter() {

    abstract val name: String
    abstract val addr: UByte
    abstract var value: Int
    abstract val min: Byte
    abstract val max: Byte
    abstract val default: Byte

    var formatString: String? = null
}