package com.example.xgbuddy.data.xg

import com.example.xgbuddy.R
import com.example.xgbuddy.util.DataFormatUtil

enum class SystemParameter(
    val nameRes: Int,
    val addr: Byte,
    val min: Int,
    val max: Int,
    val default: Int,
    val formatter: DataFormatUtil.DataAssignFormatter = DataFormatUtil.signed127Formatter
) {
    MASTER_TUNE(R.string.sys_master_tune, 0, 0, 0x07ff, 0x400),
    MASTER_VOLUME(R.string.sys_master_vol, 4, 0, 0x7f, 0x7f),
    TRANSPOSE(R.string.sys_transpose, 6, 0x28, 0x58, 0x40)
}