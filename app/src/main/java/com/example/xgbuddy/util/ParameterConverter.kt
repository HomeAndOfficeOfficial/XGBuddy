package com.example.xgbuddy.util

import com.example.xgbuddy.data.ControlParameter
import com.example.xgbuddy.data.QS300ElementParameter

object ParameterConverter {
    fun toControlParameter(elementParameter: QS300ElementParameter): ControlParameter = ControlParameter(
        elementParameter.name,
        elementParameter.default,
        elementParameter.min,
        elementParameter.max,
        elementParameter.default
    )
}