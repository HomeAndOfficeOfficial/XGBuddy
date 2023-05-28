package com.example.xgbuddy.data

abstract class ControlParameter() {

    abstract val name: String
    abstract val addr: UByte
    abstract var value: Int
    abstract var min: Int
    abstract var max: Int
    abstract var default: Int

    var formatString: String? = null
}