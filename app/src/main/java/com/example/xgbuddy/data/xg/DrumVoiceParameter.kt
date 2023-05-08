package com.example.xgbuddy.data.xg

import com.example.xgbuddy.R
import com.example.xgbuddy.util.DataFormatUtil
import kotlin.reflect.KMutableProperty

enum class DrumVoiceParameter(
    val nameRes: Int,
    val reflectedField: KMutableProperty<Byte>,
    val default: Byte = 0x40,
    val max: Byte = 0x7f,
    val formatter: DataFormatUtil.DataAssignFormatter? = DataFormatUtil.signed127Formatter
) {
    PITCH_COARSE(R.string.xgdp_pitch_crs, DrumVoice::pitchCoarse),
    PITCH_FINE(R.string.xgdp_pitch_fine, DrumVoice::pitchFine),
    LEVEL(R.string.xgdp_level, DrumVoice::level, formatter = null),
    ALTERNATE_GROUP(
        R.string.xgdp_alt_group,
        DrumVoice::alternateGroup,
        default = 0,
        formatter = DataFormatUtil.zeroOffFormatter
    ),
    PAN(R.string.xgdp_pan, DrumVoice::pan, formatter = DataFormatUtil.panFormatter),
    REVERB_SEND(R.string.xgdp_reverb_send, DrumVoice::reverbSend, formatter = null),
    CHORUS_SEND(R.string.xgdp_chorus_send, DrumVoice::chorusSend, formatter = null),
    VARI_SEND(R.string.xgdp_vari_send, DrumVoice::variSend, default = 0x7f, formatter = null),
    KEY_ASSIGN(
        R.string.xgdp_key_assign,
        DrumVoice::keyAssign,
        default = 0,
        max = 1,
        formatter = DataFormatUtil.keyAssignFormatter
    ),
    RCV_NOTE_OFF(
        R.string.xgdp_rcv_noteoff,
        DrumVoice::rcvNoteOff,
        default = 0,
        max = 1,
        formatter = null
    ),
    RCV_NOTE_ON(
        R.string.xgdp_rcv_noteon,
        DrumVoice::rcvNoteOn,
        default = 1,
        max = 1,
        formatter = null
    ),
    CUTOFF_FREQ(R.string.xgdp_cutoff, DrumVoice::cutoffFreq),
    RESONANCE(R.string.xgdp_resonance, DrumVoice::resonance),
    EG_ATTACK(R.string.xgdp_attack, DrumVoice::egAttack),
    EG_DECAY1(R.string.xgdp_decay1, DrumVoice::egDecay1),
    EG_DECAY2(R.string.xgdp_decay2, DrumVoice::egDecay2);

    fun getAddrLo(): Byte = ordinal.toByte()
}