package com.example.xgbuddy.data

data class MidiSetup(
    val mode: InstrumentMode,
    val parts: List<MidiPart>,
    val variation: Variation,
    val name: String
) {
    companion object {
        fun getXGDefault() =
            MidiSetup(InstrumentMode.XG, List(16) { MidiPart(it) }, Variation(0), "XGDefault")

        fun getQSDefault() =
            MidiSetup(InstrumentMode.QS300, List(16) { MidiPart(it) }, Variation(0), "QSDefault")
    }
}
