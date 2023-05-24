package com.example.xgbuddy.data.qs300

import com.example.xgbuddy.data.ControlParameter

class QS300ControlParameter(
    override val name: String,
    override val addr: UByte,
    override var value: Int,
    override val min: Byte,
    override val max: Byte,
    override val default: Byte
) : ControlParameter() {

    constructor(elementParameter: QS300ElementParameter, value: Byte) : this(
        elementParameter.name,
        elementParameter.baseAddress,
        value.toInt(),
        elementParameter.min,
        elementParameter.max,
        elementParameter.default
    )
}