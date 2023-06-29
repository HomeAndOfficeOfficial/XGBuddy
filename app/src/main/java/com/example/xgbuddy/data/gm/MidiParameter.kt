package com.example.xgbuddy.data.gm

import com.example.xgbuddy.R
import com.example.xgbuddy.data.MidiControlChange
import com.example.xgbuddy.data.xg.NRPN
import com.example.xgbuddy.util.DataFormatUtil
import kotlin.reflect.KMutableProperty

enum class MidiParameter(
    val addrLo: Byte, // For use in bulk dump and XG Param Change sysex
    val descriptionRes: Int,
    val min: Byte = 0,
    val max: Byte = 127,
    val default: Byte = 64,
    val controlChange: MidiControlChange? = null,
    val nrpn: NRPN? = null,
    val reflectedField: KMutableProperty<Byte>,
    val formatter: DataFormatUtil.DataAssignFormatter = DataFormatUtil.noFormat
) {
    ELEMENT_RESERVE( // For drum, default is 0
        0,
        R.string.midi_mp_el_reserve,
        max = 32,
        default = 2,
        reflectedField = MidiPart::elementReserve
    ),
    BANK_MSB( // For drum, default is 127
        1,
        R.string.midi_mp_BANK_MSB,
        default = 0,
        controlChange = MidiControlChange.BANK_SELECT_MSB,
        reflectedField = MidiPart::bankMsb
    ),
    BANK_LSB(
        2,
        R.string.midi_mp_BANK_LSB,
        default = 0,
        controlChange = MidiControlChange.BANK_SELECT_LSB,
        reflectedField = MidiPart::bankLsb
    ),
    PROG_NUMBER(
        3,
        R.string.midi_mp_PROG_NUMBER,
        default = 0,
        reflectedField = MidiPart::programNumber
    ),                      // Default is part num
    RCV_CHANNEL( // Default is part num, 0x7f also acceptable as OFF
        4,
        R.string.midi_mp_RCV_CHANNEL,
        max = 15,
        default = 0,
        reflectedField = MidiPart::receiveChannel
    ),
    POLY_MODE(
        5,
        R.string.midi_mp_POLY_MODE,
        max = 1,
        default = 1,
        controlChange = MidiControlChange.POLY_MODE,
        reflectedField = MidiPart::polyMode
    ),
    KEY_ON_ASSIGN(
        6,
        R.string.midi_mp_KEY_ON_ASSIGN,
        max = 2,
        default = 1,
        reflectedField = MidiPart::keyOnAssign
    ),
    PART_MODE( // For drum, default is 2
        7,
        R.string.midi_mp_PART_MODE,
        max = 3,
        default = 0,
        reflectedField = MidiPart::partMode
    ),
    NOTE_SHIFT(
        8,
        R.string.midi_mp_NOTE_SHIFT,
        0x28,
        0x58,
        0x40,
        reflectedField = MidiPart::noteShift
    ),
    VOLUME(
        0x0b,
        R.string.midi_mp_VOLUME,
        controlChange = MidiControlChange.CH_VOLUME,
        reflectedField = MidiPart::volume
    ),
    VEL_SENS_DEPTH(
        0x0c,
        R.string.midi_mp_VEL_SENS_DEPTH,
        reflectedField = MidiPart::velSensDepth
    ),
    VEL_SENS_OFFSET(
        0x0d,
        R.string.midi_mp_VEL_SENS_OFFSET,
        reflectedField = MidiPart::velSenseOffset
    ),
    PAN( // 0: Random, L1 - C64 - R127
        0x0e,
        R.string.midi_mp_PAN,
        controlChange = MidiControlChange.PAN,
        reflectedField = MidiPart::pan
    ),
    NOTE_LIMIT_LOW(
        0x0f,
        R.string.midi_mp_NOTE_LIMIT_LOW,
        default = 0,
        reflectedField = MidiPart::noteLimitLo
    ),
    NOTE_LIMIT_HIGH(
        0x10,
        R.string.midi_mp_NOTE_LIMIT_HIGH,
        default = 127,
        reflectedField = MidiPart::noteLimitHi
    ),
    DRY_LEVEL(0x11, R.string.midi_mp_DRY_LEVEL, default = 127, reflectedField = MidiPart::dryLevel),
    CHORUS_SEND(
        0x12,
        R.string.midi_mp_CHORUS_SEND,
        default = 0,
        controlChange = MidiControlChange.CHORUS,
        reflectedField = MidiPart::chorusSend
    ),
    REVERB_SEND(
        0x13,
        R.string.midi_mp_REVERB_SEND,
        default = 0x28,
        controlChange = MidiControlChange.REVERB_SEND,
        reflectedField = MidiPart::reverbSend
    ),
    VARI_SEND(
        0x14,
        R.string.midi_mp_VARI_SEND,
        default = 0,
        controlChange = MidiControlChange.DETUNE,
        reflectedField = MidiPart::variationSend
    ),
    VIBRATO_RATE(
        0x15,
        R.string.midi_mp_VIBRATO_RATE,
        nrpn = NRPN.VIBRATO_RATE,
        reflectedField = MidiPart::vibratoRate
    ),
    VIBRATO_DEPTH(
        0x16,
        R.string.midi_mp_VIBRATO_DEPTH,
        nrpn = NRPN.VIBRATO_DEPTH,
        reflectedField = MidiPart::vibratoDepth
    ),
    VIBRATO_DELAY(
        0x17,
        R.string.midi_mp_VIBRATO_DELAY,
        nrpn = NRPN.VIBRATO_DELAY,
        reflectedField = MidiPart::vibratoDelay
    ),
    CUTOFF_FREQ(
        0x18,
        R.string.midi_mp_CUTOFF_FREQ,
        controlChange = MidiControlChange.CUTOFF,
        nrpn = NRPN.CUTOFF_FREQ,
        reflectedField = MidiPart::cutoffFreq
    ),
    RESONANCE(
        0x19,
        R.string.midi_mp_RESONANCE,
        controlChange = MidiControlChange.RESONANCE,
        nrpn = NRPN.RESONANCE,
        reflectedField = MidiPart::resonance
    ),
    EG_ATTACK_TIME(
        0x1a,
        R.string.midi_mp_EG_ATTACK_TIME,
        controlChange = MidiControlChange.AMP_ATTACK,
        nrpn = NRPN.EG_ATTACK,
        reflectedField = MidiPart::egAttackTime
    ),
    EG_DECAY_TIME(
        0x1b,
        R.string.midi_mp_EG_DECAY_TIME,
        nrpn = NRPN.EG_DECAY,
        reflectedField = MidiPart::egDecayTime
    ),
    EG_RELEASE_TIME(
        0x1c,
        R.string.midi_mp_EG_RELEASE_TIME,
        controlChange = MidiControlChange.AMP_RELEASE,
        nrpn = NRPN.EG_RELEASE,
        reflectedField = MidiPart::egReleaseTime
    ),
    MW_PITCH_CTRL(
        0x1d,
        R.string.midi_mp_MW_PITCH_CTRL,
        min = 0x28,
        max = 0x58,
        reflectedField = MidiPart::mwPitchControl
    ),
    MW_FILTER_CTRL(
        0x1e,
        R.string.midi_mp_MW_FILTER_CTRL,
        reflectedField = MidiPart::mwFilterControl
    ),
    MW_AMP_CTRL(
        0x1f,
        R.string.midi_mp_MW_AMP_CTRL,
        reflectedField = MidiPart::mwAmplControl
    ),
    MW_LFO_PMOD_DEPTH(
        0x20,
        R.string.midi_mp_MW_LFO_PMOD_DEPTH,
        default = 0x0a,
        reflectedField = MidiPart::mwLfoPmodDepth
    ),
    MW_LFO_FMOD_DEPTH(
        0x21,
        R.string.midi_mp_MW_LFO_FMOD_DEPTH,
        default = 0,
        reflectedField = MidiPart::mwLfoFmodDepth
    ),
    MW_LFO_AMOD_DEPTH(
        0x22,
        R.string.midi_mp_MW_LFO_AMOD_DEPTH,
        default = 0,
        reflectedField = MidiPart::mwLfoAmodDepth
    ),
    BEND_PITCH_CTRL(
        0x23,
        R.string.midi_mp_BEND_PITCH_CTRL,
        0x28,
        0x58,
        0x42,
        reflectedField = MidiPart::bendPitchContrl
    ),
    BEND_FILTER_CTRL(
        0x24,
        R.string.midi_mp_BEND_FILTER_CTRL,
        reflectedField = MidiPart::bendFilterContrl
    ),
    BEND_AMP_CTRL(
        0x25,
        R.string.midi_mp_BEND_AMP_CTRL,
        reflectedField = MidiPart::bendAmplContrl
    ),
    BEND_LFO_PMOD_DEPTH(
        0x26,
        R.string.midi_mp_BEND_LFO_PMOD_DEPTH,
        reflectedField = MidiPart::bendLfoPmodDepth
    ),
    BEND_LFO_FMOD_DEPTH(
        0x27,
        R.string.midi_mp_BEND_LFO_FMOD_DEPTH,
        reflectedField = MidiPart::bendLfoFmodDepth
    ),
    BEND_LFO_AMOD_DEPTH(
        0x28,
        R.string.midi_mp_BEND_LFO_AMOD_DEPTH,
        reflectedField = MidiPart::bendLfoAmodDepth
    ),
    RCV_PITCH_BEND(
        0x30,
        R.string.midi_mp_RCV_PITCH_BEND,
        max = 1,
        default = 1,
        reflectedField = MidiPart::rcvPitchBend
    ),
    RCV_CH_AFTER_TOUCH(
        0x31,
        R.string.midi_mp_RCV_CH_AFTER_TOUCH,
        max = 1,
        default = 1,
        reflectedField = MidiPart::rcvChAfterTouch
    ),
    RCV_PROGRAM_CHANGE(
        0x32,
        R.string.midi_mp_RCV_PROGRAM_CHANGE,
        max = 1,
        default = 1,
        reflectedField = MidiPart::rcvProgramChange
    ),
    RCV_CONTROL_CHANGE(
        0x33,
        R.string.midi_mp_RCV_CONTROL_CHANGE,
        max = 1,
        default = 1,
        reflectedField = MidiPart::rcvControlChange
    ),
    RCV_POLY_AFTER_TOUCH(
        0x34,
        R.string.midi_mp_RCV_POLY_AFTER_TOUCH,
        max = 1,
        default = 1,
        reflectedField = MidiPart::rcvPolyAfterTouch
    ),
    RCV_NOTE_MESSAGE(
        0x35,
        R.string.midi_mp_RCV_NOTE_MESSAGE,
        max = 1,
        default = 1,
        reflectedField = MidiPart::rcvNoteMessage
    ),
    RCV_RPN(0x36, R.string.midi_mp_RCV_RPN, 0, 1, 1, null, null, MidiPart::rcvRpn),
    RCV_NRPN( // Default for XG = 1, GM = 0
        0x37,
        R.string.midi_mp_RCV_NRPN,
        max = 1,
        default = 1,
        reflectedField = MidiPart::rcvNrpn
    ),
    RCV_MOD(0x38, R.string.midi_mp_RCV_MOD, 0, 1, 1, null, null, MidiPart::rcvMod),
    RCV_VOLUME(0x39, R.string.midi_mp_RCV_VOLUME, 0, 1, 1, null, null, MidiPart::rcvVolume),
    RCV_PAN(0x3a, R.string.midi_mp_RCV_PAN, 0, 1, 1, null, null, MidiPart::rcvPan),
    RCV_EXPRESSION(
        0x3b,
        R.string.midi_mp_RCV_EXPRESSION,
        max = 1,
        default = 1,
        reflectedField = MidiPart::rcvExpression
    ),
    RCV_HOLD1(0x3c, R.string.midi_mp_RCV_HOLD1, 0, 1, 1, null, null, MidiPart::rcvHold),
    RCV_PORTA(0x3d, R.string.midi_mp_RCV_PORTA, 0, 1, 1, null, null, MidiPart::rcvPorta),
    RCV_SUST(0x3e, R.string.midi_mp_RCV_SUST, 0, 1, 1, null, null, MidiPart::rcvSust),
    RCV_SOFT_PEDAL(
        0x3f,
        R.string.midi_mp_RCV_SOFT_PEDAL,
        max = 1,
        default = 1,
        reflectedField = MidiPart::rcvSoftPedal
    ),
    RCV_BANK_SELECT(
        0x40,
        R.string.midi_mp_RCV_BANK_SELECT,
        max = 1,
        default = 1,
        reflectedField = MidiPart::rcvBankSelect
    ), // Default for XG = 1, GM = 0
    SCALE_TUNE_C(
        0x41,
        R.string.midi_mp_SCALE_TUNE_C,
        reflectedField = MidiPart::scaleTuneC
    ),
    SCALE_TUNE_CS(
        0x42,
        R.string.midi_mp_SCALE_TUNE_CS,
        reflectedField = MidiPart::scaleTuneCS
    ),
    SCALE_TUNE_D(
        0x43,
        R.string.midi_mp_SCALE_TUNE_D,
        reflectedField = MidiPart::scaleTuneD
    ),
    SCALE_TUNE_DS(
        0x44,
        R.string.midi_mp_SCALE_TUNE_DS,
        reflectedField = MidiPart::scaleTuneDS
    ),
    SCALE_TUNE_E(
        0x45,
        R.string.midi_mp_SCALE_TUNE_E,
        reflectedField = MidiPart::scaleTuneE
    ),
    SCALE_TUNE_F(
        0x46,
        R.string.midi_mp_SCALE_TUNE_F,
        reflectedField = MidiPart::scaleTuneF
    ),
    SCALE_TUNE_FS(
        0x47,
        R.string.midi_mp_SCALE_TUNE_FS,
        reflectedField = MidiPart::scaleTuneFS
    ),
    SCALE_TUNE_G(
        0x48,
        R.string.midi_mp_SCALE_TUNE_G,
        reflectedField = MidiPart::scaleTuneG
    ),
    SCALE_TUNE_GS(
        0x49,
        R.string.midi_mp_SCALE_TUNE_GS,
        reflectedField = MidiPart::scaleTuneGS
    ),
    SCALE_TUNE_A(
        0x4a,
        R.string.midi_mp_SCALE_TUNE_A,
        reflectedField = MidiPart::scaleTuneA
    ),
    SCALE_TUNE_AS(
        0x4b,
        R.string.midi_mp_SCALE_TUNE_AS,
        reflectedField = MidiPart::scaleTuneAS
    ),
    SCALE_TUNE_B(
        0x4c,
        R.string.midi_mp_SCALE_TUNE_B,
        reflectedField = MidiPart::scaleTuneB
    ),
    CAT_PITCH_CTRL(
        0x4d,
        R.string.midi_mp_CAT_PITCH_CTRL,
        0x28,
        0x58,
        0x40,
        reflectedField = MidiPart::catPitchControl
    ),
    CAT_FILTER_CTRL(
        0x4e,
        R.string.midi_mp_CAT_FILTER_CTRL,
        reflectedField = MidiPart::catFilterControl
    ),
    CAT_AMP_CTRL(
        0x4f,
        R.string.midi_mp_CAT_AMP_CTRL,
        reflectedField = MidiPart::catAmplControl
    ),
    CAT_LFO_PMOD_DEPTH(
        0x50,
        R.string.midi_mp_CAT_LFO_PMOD_DEPTH,
        default = 0,
        reflectedField = MidiPart::catLfoPmodDepth
    ),
    CAT_LFO_FMOD_DEPTH(
        0x51,
        R.string.midi_mp_CAT_LFO_FMOD_DEPTH,
        default = 0,
        reflectedField = MidiPart::catLfoFmodDepth
    ),
    CAT_LFO_AMOD_DEPTH(
        0x52,
        R.string.midi_mp_CAT_LFO_AMOD_DEPTH,
        default = 0,
        reflectedField = MidiPart::catLfoAmodDepth
    ),
    PAT_PITCH_CTRL(
        0x53,
        R.string.midi_mp_PAT_PITCH_CTRL,
        0x28,
        0x58,
        0x40,
        reflectedField = MidiPart::patPitchControl
    ),
    PAT_FILTER_CTRL(
        0x54,
        R.string.midi_mp_PAT_FILTER_CTRL,
        reflectedField = MidiPart::patFilterControl
    ),
    PAT_AMP_CTRL(
        0x55,
        R.string.midi_mp_PAT_AMP_CTRL,
        reflectedField = MidiPart::patAmplControl
    ),
    PAT_LFO_PMOD_DEPTH(
        0x56,
        R.string.midi_mp_PAT_LFO_PMOD_DEPTH,
        default = 0,
        reflectedField = MidiPart::patLfoPmodDepth
    ),
    PAT_LFO_FMOD_DEPTH(
        0x57,
        R.string.midi_mp_PAT_LFO_FMOD_DEPTH,
        default = 0,
        reflectedField = MidiPart::patLfoFmodDepth
    ),
    PAT_LFO_AMOD_DEPTH(
        0x58,
        R.string.midi_mp_PAT_LFO_AMOD_DEPTH,
        default = 0,
        reflectedField = MidiPart::patLfoAmodDepth
    ),
    AC1_CTRL_NUMBER(
        0x59,
        R.string.midi_mp_AC1_CTRL_NUMBER,
        max = 0x5f,
        default = 0x10,
        reflectedField = MidiPart::ac1CtrlNumber
    ),
    AC1_PITCH_CTRL(
        0x5a,
        R.string.midi_mp_AC1_PITCH_CTRL,
        0x28,
        0x58,
        reflectedField = MidiPart::ac1PitchCtrl
    ),
    AC1_FILTER_CTRL(
        0x5b,
        R.string.midi_mp_AC1_FILTER_CTRL,
        reflectedField = MidiPart::ac1FilterCtrl
    ),
    AC1_AMP_CTRL(
        0x5c,
        R.string.midi_mp_AC1_AMP_CTRL,
        reflectedField = MidiPart::ac1AmpCtrl
    ),
    AC1_LFO_PMOD_DEPTH(
        0x5d,
        R.string.midi_mp_AC1_LFO_PMOD_DEPTH,
        default = 0,
        reflectedField = MidiPart::ac1LfoPmodDepth
    ),
    AC1_LFO_FMOD_DEPTH(
        0x5e,
        R.string.midi_mp_AC1_LFO_FMOD_DEPTH,
        default = 0,
        reflectedField = MidiPart::ac1LfoFmodDepth
    ),
    AC1_LFO_AMOD_DEPTH(
        0x5f,
        R.string.midi_mp_AC1_LFO_AMOD_DEPTH,
        default = 0,
        reflectedField = MidiPart::ac1LfoAmodDepth
    ),
    AC2_CTRL_NUMBER(
        0x60,
        R.string.midi_mp_AC2_CTRL_NUMBER,
        max = 0x5f,
        default = 0x11,
        reflectedField = MidiPart::ac2CtrlNumber
    ),
    AC2_PITCH_CTRL(
        0x61,
        R.string.midi_mp_AC2_PITCH_CTRL,
        0x28,
        0x58,
        reflectedField = MidiPart::ac2PitchCtrl
    ),
    AC2_FILTER_CTRL(
        0x62,
        R.string.midi_mp_AC2_FILTER_CTRL,
        reflectedField = MidiPart::ac2FilterCtrl
    ),
    AC2_AMP_CTRL(
        0x63,
        R.string.midi_mp_AC2_AMP_CTRL,
        reflectedField = MidiPart::ac2AmpCtrl
    ),
    AC2_LFO_PMOD_DEPTH(
        0x64,
        R.string.midi_mp_AC2_LFO_PMOD_DEPTH,
        default = 0,
        reflectedField = MidiPart::ac2LfoPmodDepth
    ),
    AC2_LFO_FMOD_DEPTH(
        0x65,
        R.string.midi_mp_AC2_LFO_FMOD_DEPTH,
        default = 0,
        reflectedField = MidiPart::ac2LfoFmodDepth
    ),
    AC2_LFO_AMOD_DEPTH(
        0x66,
        R.string.midi_mp_AC2_LFO_AMOD_DEPTH,
        default = 0,
        reflectedField = MidiPart::ac2LfoAmodDepth
    ),
    PORTA_SWITCH(
        0x67,
        R.string.midi_mp_PORTA_SWITCH,
        max = 1,
        default = 0,
        controlChange = MidiControlChange.PORTA,
        reflectedField = MidiPart::portaSwitch
    ),
    PORTA_TIME(0x68, R.string.midi_mp_PORTA_TIME, 0, 127, 0, null, null, MidiPart::portaTime),
    PITCH_EG_INIT_LVL(
        0x69,
        R.string.midi_mp_PITCH_EG_INIT_LVL,
        reflectedField = MidiPart::pitchEgInitLvl
    ),
    PITCH_EG_ATTACK_TIME(
        0x6a,
        R.string.midi_mp_PITCH_EG_ATTACK_TIME,
        reflectedField = MidiPart::pitchEgAttackTime
    ),
    PITCH_EG_REL_LVL(
        0x6b,
        R.string.midi_mp_PITCH_EG_REL_LVL,
        reflectedField = MidiPart::pitchEgRelLvl
    ),
    PITCH_EG_REL_TIME(
        0x6c,
        R.string.midi_mp_PITCH_EG_REL_TIME,
        reflectedField = MidiPart::pitchEgRelTime
    ),
    VEL_LIMIT_LOW(
        0x6d,
        R.string.midi_mp_VEL_LIMIT_LOW,
        1,
        default = 1,
        reflectedField = MidiPart::velocityLimitLo
    ),
    VEL_LIMIT_HIGH(
        0x6e,
        R.string.midi_mp_VEL_LIMIT_HIGH,
        1,
        127,
        127,
        reflectedField = MidiPart::velocityLimitHi
    )
}