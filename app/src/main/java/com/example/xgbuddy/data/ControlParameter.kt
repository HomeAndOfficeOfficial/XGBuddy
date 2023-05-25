package com.example.xgbuddy.data

abstract class ControlParameter() {

    abstract val name: String
    abstract val addr: UByte
    abstract var value: Int
    abstract val min: Int
    abstract val max: Int
    abstract val default: Int

    var formatString: String? = null
}