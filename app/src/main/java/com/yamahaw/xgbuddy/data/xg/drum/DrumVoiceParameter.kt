package com.yamahaw.xgbuddy.data.xg.drum

import com.yamahaw.xgbuddy.R
import com.yamahaw.xgbuddy.data.xg.NRPN
import com.yamahaw.xgbuddy.util.DataFormatUtil
import kotlin.reflect.KMutableProperty

enum class DrumVoiceParameter(
    val nameRes: Int,
    val reflectedField: KMutableProperty<Byte>,
    val nrpn: NRPN? = null,
    val default: Byte = 0x40,
    val max: Byte = 0x7f,
    val formatter: DataFormatUtil.DataAssignFormatter = DataFormatUtil.signed127Formatter
) {
    PITCH_COARSE(R.string.xgdp_pitch_crs, DrumVoice::pitchCoarse, NRPN.DRUM_PITCH_COARSE),
    PITCH_FINE(R.string.xgdp_pitch_fine, DrumVoice::pitchFine, NRPN.DRUM_PITCH_FINE),
    LEVEL(
        R.string.xgdp_level,
        DrumVoice::level,
        NRPN.DRUM_LEVEL,
        formatter = DataFormatUtil.noFormat
    ),
    ALTERNATE_GROUP(
        R.string.xgdp_alt_group,
        DrumVoice::alternateGroup,
        default = 0,
        formatter = DataFormatUtil.zeroOffFormatter
    ),
    PAN(R.string.xgdp_pan, DrumVoice::pan, NRPN.DRUM_PAN, formatter = DataFormatUtil.panFormatter),
    REVERB_SEND(
        R.string.xgdp_reverb_send,
        DrumVoice::reverbSend,
        NRPN.DRUM_REVERB,
        formatter = DataFormatUtil.noFormat
    ),
    CHORUS_SEND(
        R.string.xgdp_chorus_send,
        DrumVoice::chorusSend,
        NRPN.DRUM_CHORUS,
        formatter = DataFormatUtil.noFormat
    ),
    VARI_SEND(
        R.string.xgdp_vari_send,
        DrumVoice::variSend,
        NRPN.DRUM_VARIATION,
        default = 0x7f,
        formatter = DataFormatUtil.noFormat
    ),
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
        formatter = DataFormatUtil.noFormat
    ),
    RCV_NOTE_ON(
        R.string.xgdp_rcv_noteon,
        DrumVoice::rcvNoteOn,
        default = 1,
        max = 1,
        formatter = DataFormatUtil.noFormat
    ),
    CUTOFF_FREQ(R.string.xgdp_cutoff, DrumVoice::cutoffFreq, NRPN.DRUM_CUTOFF_FREQ),
    RESONANCE(R.string.xgdp_resonance, DrumVoice::resonance, NRPN.DRUM_RESONANCE),
    EG_ATTACK(R.string.xgdp_attack, DrumVoice::egAttack, NRPN.DRUM_ATTACK),
    EG_DECAY1(R.string.xgdp_decay1, DrumVoice::egDecay1, NRPN.DRUM_DECAY),
    EG_DECAY2(R.string.xgdp_decay2, DrumVoice::egDecay2, NRPN.DRUM_DECAY);

    fun getAddrLo(): Byte = ordinal.toByte()
}