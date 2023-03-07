package com.example.xgbuddy.data

import java.util.*

data class QS300Element(val elementNumber: Int) {
    val parameters: EnumMap<QS300ElementParameter, UByte> =
        EnumMap(QS300ElementParameter::class.java)
}