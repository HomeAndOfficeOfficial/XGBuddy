package com.example.xgbuddy.data

class QS300Voice {

    val elements: MutableList<QS300Element> = mutableListOf()
    var voiceLevel: Byte = 100
    var elementSwitch: Byte = EL_12
    var voiceName: String = ""

    companion object {
        const val EL_1: Byte = 0
        const val EL_2: Byte = 1
        const val EL_12: Byte = 2
    }
}