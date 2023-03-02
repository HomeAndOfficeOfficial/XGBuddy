package com.example.xgbuddy.data

data class MidiSetup(
    val mode: InstrumentMode,
    val channels: List<MidiChannel>,
    val variation: Variation,
    val name: String
)
