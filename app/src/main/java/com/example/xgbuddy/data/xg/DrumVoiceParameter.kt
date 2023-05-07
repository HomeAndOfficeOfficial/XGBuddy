package com.example.xgbuddy.data.xg

import com.example.xgbuddy.util.DataFormatUtil

enum class DrumVoiceParameter(
    val nameRes: String,
    val default: Byte = 0x40,
    val max: Byte = 0x7f,
    val formatter: DataFormatUtil.DataAssignFormatter? = DataFormatUtil.signed127Formatter
) {
    PITCH_COARSE("Pitch Coarse"),
    PITCH_FINE("Pitch Fine"),
    LEVEL("Level", formatter = null),
    ALTERNATE_GROUP("Alternate Group", default = 0, formatter = DataFormatUtil.zeroOffFormatter),
    PAN("Pan", formatter = DataFormatUtil.panFormatter),
    REVERB_SEND("Reverb Send", formatter = null),
    CHORUS_SEND("Chorus Send", formatter = null),
    VARI_SEND("Variation Send", default = 0x7f, formatter = null),
    KEY_ASSIGN("Key Assign", default = 0, max = 1, formatter = DataFormatUtil.keyAssignFormatter),
    RCV_NOTE_OFF("Rcv Note Off", default = 0, max = 1, formatter = null),
    RCV_NOTE_ON("Rcv Note On", default = 1, max = 1, formatter = null),
    CUTOFF_FREQ("Cutoff Freq"),
    RESONANCE("Resonance"),
    EG_ATTACK("EG Attack Rate"),
    EG_DECAY1("EG Decay Rate 1"),
    EG_DECAY2("EG Decay Rate 2");

    fun getAddrLo(): Byte = ordinal.toByte()
}