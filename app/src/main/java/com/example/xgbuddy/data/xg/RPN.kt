package com.example.xgbuddy.data.xg

import com.example.xgbuddy.R

enum class RPN(
    val nameRes: Int,
    val msb: Byte,
    val lsb: Byte,
    val min: Byte,
    val max: Byte,
    val default: Byte?,
    val lsbData: Byte?
) {
    PITCH_BEND_SENS(R.string.rpn_pitch_bend, 0, 0, 0, 0x18, 0x02, null),
    FINE_TUNING(R.string.rpn_fine_tune, 0, 0x01, 0, 0x7f, 0x40, 0x11),
    COARSE_TUNING(R.string.rpn_coarse_tune, 0, 0x02, 0x28, 0x58, 0x40, null),
    RPN_NULL(R.string.rpn_null, 0x7f, 0x7f, 0, 0, null, null)
}