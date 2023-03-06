package com.example.xgbuddy.data

data class MidiSetup(
    val mode: InstrumentMode,
    val channels: List<MidiChannel>,
    val variation: Variation,
    val name: String
) {
    companion object {
        fun getXGDefault() =
            MidiSetup(InstrumentMode.XG, List(16) { MidiChannel(it) }, Variation(0), "XGDefault")

        fun getQSDefault() =
            MidiSetup(InstrumentMode.QS300, List(16) { MidiChannel(it) }, Variation(0), "QSDefault")
    }
}
