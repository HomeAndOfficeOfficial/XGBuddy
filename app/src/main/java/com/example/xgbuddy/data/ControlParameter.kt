package com.example.xgbuddy.data

import com.example.xgbuddy.data.gm.MidiParameter
import com.example.xgbuddy.data.qs300.QS300ElementParameter
import com.example.xgbuddy.data.xg.DrumVoiceParameter
import com.example.xgbuddy.data.xg.EffectParameter
import com.example.xgbuddy.data.xg.EffectParameterData
import com.example.xgbuddy.data.xg.SystemParameter
import com.example.xgbuddy.util.DataFormatUtil

class ControlParameter private constructor(
    val name: String,
    val addr: UByte,
    var value: Int,
    var min: Int,
    var max: Int,
    var default: Int,
    var formatter: DataFormatUtil.DataAssignFormatter
) {

    constructor(
        effectParam: EffectParameterData,
        reverbParameter: EffectParameter?,
        value: Int,
        defaultValue: Int
    ) : this(
        effectParam.name,
        effectParam.addrLo.toUByte(),
        value,
        reverbParameter?.min ?: effectParam.min.toInt(),
        reverbParameter?.max ?: effectParam.max.toInt(),
        defaultValue,
        reverbParameter?.dataAssignFormatter ?: effectParam.formatter
    )

    constructor(elementParameter: QS300ElementParameter, value: Byte) : this(
        elementParameter.name,
        elementParameter.baseAddress,
        value.toInt(),
        elementParameter.min.toInt(),
        elementParameter.max.toInt(),
        elementParameter.default.toInt(),
        elementParameter.formatter
    )

    constructor(drumParameter: DrumVoiceParameter, value: Byte) : this(
        drumParameter.name,
        drumParameter.ordinal.toUByte(),
        value.toInt(),
        0,
        drumParameter.max.toInt(),
        drumParameter.default.toInt(),
        drumParameter.formatter
    )

    constructor(systemParameter: SystemParameter, value: Int) : this(
        systemParameter.name,
        systemParameter.addr.toUByte(),
        value,
        systemParameter.min,
        systemParameter.max,
        systemParameter.default,
        systemParameter.formatter
    )

    constructor(elementParameter: MidiParameter, value: Byte) : this(
        elementParameter.name,
        elementParameter.addrLo.toUByte(),
        value.toInt(),
        elementParameter.min.toInt(),
        elementParameter.max.toInt(),
        elementParameter.default.toInt(),
        elementParameter.formatter
    )
}