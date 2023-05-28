package com.example.xgbuddy.data.qs300

import com.example.xgbuddy.data.ControlParameter

class QS300ControlParameter(
    override val name: String,
    override val addr: UByte,
    override var value: Int,
    override var min: Int,
    override var max: Int,
    override var default: Int
) : ControlParameter() {

    constructor(elementParameter: QS300ElementParameter, value: Byte) : this(
        elementParameter.name,
        elementParameter.baseAddress,
        value.toInt(),
        elementParameter.min.toInt(),
        elementParameter.max.toInt(),
        elementParameter.default.toInt()
    )
}