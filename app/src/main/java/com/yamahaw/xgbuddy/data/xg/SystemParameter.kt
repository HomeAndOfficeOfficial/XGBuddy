package com.yamahaw.xgbuddy.data.xg

import com.yamahaw.xgbuddy.R
import com.yamahaw.xgbuddy.util.DataFormatUtil

enum class SystemParameter(
    val nameRes: Int,
    val addr: Byte,
    val min: Int,
    val max: Int,
    val default: Int,
    val formatter: DataFormatUtil.DataAssignFormatter
) {
    MASTER_TUNE(R.string.sys_master_tune, 0, 0, 0x07ff, 0x400, DataFormatUtil.masterTuneFormatter),
    MASTER_VOLUME(R.string.sys_master_vol, 4, 0, 0x7f, 0x7f, DataFormatUtil.noFormat),
    TRANSPOSE(R.string.sys_transpose, 6, 0x28, 0x58, 0x40, DataFormatUtil.signed127Formatter)
}