package com.example.xgbuddy.data.xg

import com.example.xgbuddy.data.ControlParameter

class SystemControlParameter(parameter: SystemParameter, override var value: Int) :
    ControlParameter(

    ) {
    override val name: String = parameter.name
    override val addr: UByte = parameter.addr.toUByte()
    override var min: Int = parameter.min
    override var max: Int = parameter.max
    override var default: Int = parameter.default
}