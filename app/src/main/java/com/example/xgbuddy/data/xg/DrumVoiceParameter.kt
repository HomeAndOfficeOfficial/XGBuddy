package com.example.xgbuddy.data.xg

import com.example.xgbuddy.util.DataFormatUtil
import kotlin.reflect.KMutableProperty

enum class DrumVoiceParameter(
    val nameRes: String,
    val reflectedField: KMutableProperty<Byte>,
    val default: Byte = 0x40,
    val max: Byte = 0x7f,
    val formatter: DataFormatUtil.DataAssignFormatter? = DataFormatUtil.signed127Formatter
) {
    PITCH_COARSE("Pitch Coarse", DrumVoice::pitchCoarse),
    PITCH_FINE("Pitch Fine", DrumVoice::pitchFine),
    LEVEL("Level", DrumVoice::level, formatter = null),
    ALTERNATE_GROUP(
        "Alternate Group",
        DrumVoice::alternateGroup,
        default = 0,
        formatter = DataFormatUtil.zeroOffFormatter
    ),
    PAN("Pan", DrumVoice::pan, formatter = DataFormatUtil.panFormatter),
    REVERB_SEND("Reverb Send", DrumVoice::reverbSend, formatter = null),
    CHORUS_SEND("Chorus Send", DrumVoice::chorusSend, formatter = null),
    VARI_SEND("Variation Send", DrumVoice::variSend, default = 0x7f, formatter = null),
    KEY_ASSIGN(
        "Key Assign",
        DrumVoice::keyAssign,
        default = 0,
        max = 1,
        formatter = DataFormatUtil.keyAssignFormatter
    ),
    RCV_NOTE_OFF("Rcv Note Off", DrumVoice::rcvNoteOff, default = 0, max = 1, formatter = null),
    RCV_NOTE_ON("Rcv Note On", DrumVoice::rcvNoteOn, default = 1, max = 1, formatter = null),
    CUTOFF_FREQ("Cutoff Freq", DrumVoice::cutoffFreq),
    RESONANCE("Resonance", DrumVoice::resonance),
    EG_ATTACK("EG Attack Rate", DrumVoice::egAttack),
    EG_DECAY1("EG Decay Rate 1", DrumVoice::egDecay1),
    EG_DECAY2("EG Decay Rate 2", DrumVoice::egDecay2);

    fun getAddrLo(): Byte = ordinal.toByte()
}