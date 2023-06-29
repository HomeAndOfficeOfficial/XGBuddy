package com.example.xgbuddy.data.xg

import com.example.xgbuddy.util.DataFormatUtil

enum class SystemParameter(
    val addr: Byte,
    val min: Int,
    val max: Int,
    val default: Int,
    val formatter: DataFormatUtil.DataAssignFormatter = DataFormatUtil.signed127Formatter) {
    MASTER_TUNE(0, 0, 0x07ff, 0x400),
    MASTER_VOLUME(4, 0, 0x7f, 0x7f),
    TRANSPOSE(6, 0x28, 0x58, 0x40)
}