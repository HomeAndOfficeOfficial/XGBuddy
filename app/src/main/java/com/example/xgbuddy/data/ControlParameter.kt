package com.example.xgbuddy.data

import com.example.xgbuddy.data.gm.MidiParameter
import com.example.xgbuddy.data.qs300.QS300ElementParameter
import com.example.xgbuddy.data.xg.DrumVoiceParameter
import com.example.xgbuddy.data.xg.EffectParameter
import com.example.xgbuddy.data.xg.EffectParameterData
import com.example.xgbuddy.data.xg.SystemParameter
import com.example.xgbuddy.util.DataFormatUtil

class ControlParameter private constructor(
    val nameRes: Int,
    val name: String,
    val addr: UByte,
    var value: Int,
    var min: Int,
    var max: Int,
    var default: Int,
    var formatter: DataFormatUtil.DataAssignFormatter
) {

    constructor(
        displayName: String,
        effectParam: EffectParameterData,
        reverbParameter: EffectParameter?,
        value: Int,
        defaultValue: Int
    ) : this(
        effectParam.resId,
        displayName,
        effectParam.addrLo.toUByte(),
        value,
        reverbParameter?.min ?: effectParam.min.toInt(),
        reverbParameter?.max ?: effectParam.max.toInt(),
        defaultValue,
        reverbParameter?.dataAssignFormatter ?: effectParam.formatter
    )

    constructor(displayName: String, elementParameter: QS300ElementParameter, value: Byte) : this(
        elementParameter.nameRes,
        displayName,
        elementParameter.baseAddress,
        value.toInt(),
        elementParameter.min.toInt(),
        elementParameter.max.toInt(),
        elementParameter.default.toInt(),
        elementParameter.formatter
    )

    constructor(displayName: String, drumParameter: DrumVoiceParameter, value: Byte) : this(
        drumParameter.nameRes,
        displayName,
        drumParameter.ordinal.toUByte(),
        value.toInt(),
        0,
        drumParameter.max.toInt(),
        drumParameter.default.toInt(),
        drumParameter.formatter
    )

    constructor(displayName: String, systemParameter: SystemParameter, value: Int) : this(
        systemParameter.nameRes,
        displayName,
        systemParameter.addr.toUByte(),
        value,
        systemParameter.min,
        systemParameter.max,
        systemParameter.default,
        systemParameter.formatter
    )

    constructor(displayName: String, elementParameter: MidiParameter, value: Byte) : this(
        elementParameter.descriptionRes,
        displayName,
        elementParameter.addrLo.toUByte(),
        value.toInt(),
        elementParameter.min.toInt(),
        elementParameter.max.toInt(),
        elementParameter.default.toInt(),
        elementParameter.formatter
    )
}