package com.example.xgbuddy.data.qs300

data class QS300Preset(val name: String, val voices: MutableList<QS300Voice>) {
    fun clone(): QS300Preset = QS300Preset(name, voices.let {
        val listCopy = mutableListOf<QS300Voice>()
        it.forEach { voice ->
            listCopy.add(voice.copy(voiceName = voice.voiceName).apply { elements = elements.toMutableList() })
        }
        listCopy
    })
}