package com.example.xgbuddy.data.gm

import com.example.xgbuddy.R
import com.example.xgbuddy.data.MidiControlChange
import com.example.xgbuddy.data.xg.NRPN
import kotlin.reflect.KMutableProperty

/**
 * Since this works a little different than the qs300 params in that some are just regular CC
 * parameters, some are RPN/NRPN parameters, and some are neither probably, I think I'll have to add
 * a few more fields to the constructor unfortunately.
 *
 * 1. ControlChange? <- if parameter is a CC param, it can use the controlChange to affect the voice
 * 2. NRPN? <- if parameter is controlled by NRPN, it should probabl y use it
 * 3. RPN?
 */

enum class MidiParameter(
    val addrLo: Byte, // For use in bulk dump and XG Param Change sysex
    val descriptionRes: Int,
    val min: Byte,
    val max: Byte,
    val default: Byte,
    val controlChange: MidiControlChange?,
    val nrpn: NRPN?,
    val reflectedField: KMutableProperty<Byte>
) {
    ELEMENT_RESERVE(
        0,
        R.string.midi_mp_el_reserve,
        0,
        32,
        2,
        null,
        null, MidiPart::elementReserve
    ), // For drum, default is 0
    BANK_MSB(
        1,
        R.string.midi_mp_BANK_MSB,
        0,
        127,
        0,
        MidiControlChange.BANK_SELECT_MSB,
        null, MidiPart::bankMsb
    ),                        // For drum, default is 127
    BANK_LSB(
        2,
        R.string.midi_mp_BANK_LSB,
        0,
        127,
        0,
        MidiControlChange.BANK_SELECT_LSB,
        null, MidiPart::bankLsb
    ),
    PROG_NUMBER(
        3,
        R.string.midi_mp_PROG_NUMBER,
        0,
        127,
        0, null, null, MidiPart::programNumber
    ),                      // Default is part num
    RCV_CHANNEL(
        4,
        R.string.midi_mp_RCV_CHANNEL,
        0,
        15,
        0, null, null, MidiPart::receiveChannel
    ),                  // Default is part num, 0x7f also acceptable as OFF
    POLY_MODE(
        5,
        R.string.midi_mp_POLY_MODE,
        0,
        1,
        1,
        MidiControlChange.POLY_MODE,
        null, MidiPart::polyMode
    ),
    KEY_ON_ASSIGN(6, R.string.midi_mp_KEY_ON_ASSIGN, 0, 2, 1, null, null, MidiPart::keyOnAssign),
    PART_MODE(
        7,
        R.string.midi_mp_PART_MODE,
        0,
        3,
        0, null, null, MidiPart::partMode
    ),                        // For drum, default is 2
    NOTE_SHIFT(8, R.string.midi_mp_NOTE_SHIFT, 0x28, 0x58, 0x40, null, null, MidiPart::noteShift),

    //    DETUNE(9, R.string.midi_mp_DETUNE, , null, MidiPart::elementReserve), /* TODO: Create special case for detune */
    VOLUME(
        0x0b,
        R.string.midi_mp_VOLUME,
        0,
        127,
        64,
        MidiControlChange.CH_VOLUME,
        null, MidiPart::volume
    ),
    VEL_SENS_DEPTH(
        0x0c,
        R.string.midi_mp_VEL_SENS_DEPTH,
        0,
        127,
        64,
        null,
        null,
        MidiPart::velSensDepth
    ),
    VEL_SENS_OFFSET(
        0x0d,
        R.string.midi_mp_VEL_SENS_OFFSET,
        0,
        127,
        64,
        null,
        null, MidiPart::velSenseOffset
    ),
    PAN(
        0x0e,
        R.string.midi_mp_PAN,
        0,
        127,
        64,
        MidiControlChange.PAN,
        null, MidiPart::pan
    ), // 0: Random, L1 - C64 - R127
    NOTE_LIMIT_LOW(
        0x0f,
        R.string.midi_mp_NOTE_LIMIT_LOW,
        0,
        127,
        0,
        null,
        null,
        MidiPart::noteLimitLo
    ),
    NOTE_LIMIT_HIGH(
        0x10,
        R.string.midi_mp_NOTE_LIMIT_HIGH,
        0,
        127,
        127,
        null,
        null, MidiPart::noteLimitHi
    ),
    DRY_LEVEL(0x11, R.string.midi_mp_DRY_LEVEL, 0, 127, 127, null, null, MidiPart::dryLevel),
    CHORUS_SEND(
        0x12,
        R.string.midi_mp_CHORUS_SEND,
        0,
        127,
        0,
        MidiControlChange.CHORUS,
        null, MidiPart::chorusSend
    ),
    REVERB_SEND(
        0x13,
        R.string.midi_mp_REVERB_SEND,
        0,
        127,
        0x28,
        MidiControlChange.REVERB_SEND,
        null, MidiPart::reverbSend
    ),
    VARI_SEND(
        0x14,
        R.string.midi_mp_VARI_SEND,
        0,
        127,
        0,
        MidiControlChange.DETUNE,
        null, MidiPart::variationSend
    ),
    VIBRATO_RATE(
        0x15,
        R.string.midi_mp_VIBRATO_RATE,
        0,
        127,
        0x40,
        null,
        NRPN.VIBRATO_RATE,
        MidiPart::vibratoRate
    ),
    VIBRATO_DEPTH(
        0x16,
        R.string.midi_mp_VIBRATO_DEPTH,
        0,
        127,
        0x40,
        null,
        NRPN.VIBRATO_DEPTH,
        MidiPart::vibratoDepth
    ),
    VIBRATO_DELAY(
        0x17,
        R.string.midi_mp_VIBRATO_DELAY,
        0,
        127,
        0x40,
        null,
        NRPN.VIBRATO_DELAY,
        MidiPart::vibratoDelay
    ),
    CUTOFF_FREQ(
        0x18,
        R.string.midi_mp_CUTOFF_FREQ,
        0,
        127,
        0x40,
        MidiControlChange.CUTOFF,
        NRPN.CUTOFF_FREQ, MidiPart::cutoffFreq
    ),
    RESONANCE(
        0x19,
        R.string.midi_mp_RESONANCE,
        0,
        127,
        0x40,
        MidiControlChange.RESONANCE,
        NRPN.RESONANCE, MidiPart::resonance
    ),
    EG_ATTACK_TIME(
        0x1a,
        R.string.midi_mp_EG_ATTACK_TIME,
        0,
        127,
        0x40,
        MidiControlChange.AMP_ATTACK,
        NRPN.EG_ATTACK, MidiPart::egAttackTime
    ),
    EG_DECAY_TIME(
        0x1b,
        R.string.midi_mp_EG_DECAY_TIME,
        0,
        127,
        0x40,
        null,
        NRPN.EG_DECAY,
        MidiPart::egDecayTime
    ),
    EG_RELEASE_TIME(
        0x1c,
        R.string.midi_mp_EG_RELEASE_TIME,
        0,
        127,
        0x40,
        MidiControlChange.AMP_RELEASE,
        NRPN.EG_RELEASE, MidiPart::egReleaseTime
    ),
    MW_PITCH_CTRL(
        0x1d,
        R.string.midi_mp_MW_PITCH_CTRL,
        0x28,
        0x58,
        0x40,
        null,
        null, MidiPart::mwPitchControl
    ),
    MW_FILTER_CTRL(
        0x1e,
        R.string.midi_mp_MW_FILTER_CTRL,
        0,
        127,
        0x40,
        null,
        null, MidiPart::mwFilterControl
    ),
    MW_AMP_CTRL(
        0x1f,
        R.string.midi_mp_MW_AMP_CTRL,
        0,
        127,
        0x40,
        null,
        null,
        MidiPart::mwAmplControl
    ),
    MW_LFO_PMOD_DEPTH(
        0x20,
        R.string.midi_mp_MW_LFO_PMOD_DEPTH,
        0,
        127,
        0x0a,
        null,
        null, MidiPart::mwLfoPmodDepth
    ),
    MW_LFO_FMOD_DEPTH(
        0x21,
        R.string.midi_mp_MW_LFO_FMOD_DEPTH,
        0,
        127,
        0, null,
        null, MidiPart::mwLfoFmodDepth
    ),
    MW_LFO_AMOD_DEPTH(
        0x22,
        R.string.midi_mp_MW_LFO_AMOD_DEPTH,
        0,
        127,
        0, null,
        null, MidiPart::mwLfoAmodDepth
    ),
    BEND_PITCH_CTRL(
        0x23,
        R.string.midi_mp_BEND_PITCH_CTRL,
        0x28,
        0x58,
        0x42, null,
        null, MidiPart::bendPitchContrl
    ),
    BEND_FILTER_CTRL(
        0x24,
        R.string.midi_mp_BEND_FILTER_CTRL,
        0,
        127,
        0x40, null,
        null, MidiPart::bendFilterContrl
    ),
    BEND_AMP_CTRL(
        0x25,
        R.string.midi_mp_BEND_AMP_CTRL,
        0,
        127,
        0x40,
        null,
        null, MidiPart::bendAmplContrl
    ),
    BEND_LFO_PMOD_DEPTH(
        0x26,
        R.string.midi_mp_BEND_LFO_PMOD_DEPTH,
        0,
        127,
        0x40, null,
        null, MidiPart::bendLfoPmodDepth
    ),
    BEND_LFO_FMOD_DEPTH(
        0x27,
        R.string.midi_mp_BEND_LFO_FMOD_DEPTH,
        0,
        127,
        0x40, null,
        null, MidiPart::bendLfoFmodDepth
    ),
    BEND_LFO_AMOD_DEPTH(
        0x28,
        R.string.midi_mp_BEND_LFO_AMOD_DEPTH,
        0,
        127,
        0x40, null,
        null, MidiPart::bendLfoAmodDepth
    ),
    RCV_PITCH_BEND(
        0x30,
        R.string.midi_mp_RCV_PITCH_BEND,
        0,
        1,
        1,
        null,
        null,
        MidiPart::rcvPitchBend
    ),
    RCV_CH_AFTER_TOUCH(
        0x31,
        R.string.midi_mp_RCV_CH_AFTER_TOUCH,
        0,
        1,
        1, null,
        null, MidiPart::rcvChAfterTouch
    ),
    RCV_PROGRAM_CHANGE(
        0x32,
        R.string.midi_mp_RCV_PROGRAM_CHANGE,
        0,
        1,
        1, null,
        null, MidiPart::rcvProgramChange
    ),
    RCV_CONTROL_CHANGE(
        0x33,
        R.string.midi_mp_RCV_CONTROL_CHANGE,
        0,
        1,
        1, null,
        null, MidiPart::rcvControlChange
    ),
    RCV_POLY_AFTER_TOUCH(
        0x34,
        R.string.midi_mp_RCV_POLY_AFTER_TOUCH,
        0,
        1,
        1, null,
        null, MidiPart::rcvPolyAfterTouch
    ),
    RCV_NOTE_MESSAGE(
        0x35,
        R.string.midi_mp_RCV_NOTE_MESSAGE,
        0,
        1,
        1,
        null,
        null, MidiPart::rcvNoteMessage
    ),
    RCV_RPN(0x36, R.string.midi_mp_RCV_RPN, 0, 1, 1, null, null, MidiPart::rcvRpn),
    RCV_NRPN(
        0x37,
        R.string.midi_mp_RCV_NRPN,
        0,
        1,
        1, null,
        null, MidiPart::rcvNrpn
    ), // Default for XG = 1, GM = 0
    RCV_MOD(0x38, R.string.midi_mp_RCV_MOD, 0, 1, 1, null, null, MidiPart::rcvMod),
    RCV_VOLUME(0x39, R.string.midi_mp_RCV_VOLUME, 0, 1, 1, null, null, MidiPart::rcvVolume),
    RCV_PAN(0x3a, R.string.midi_mp_RCV_PAN, 0, 1, 1, null, null, MidiPart::rcvPan),
    RCV_EXPRESSION(
        0x3b,
        R.string.midi_mp_RCV_EXPRESSION,
        0,
        1,
        1,
        null,
        null,
        MidiPart::rcvExpression
    ),
    RCV_HOLD1(0x3c, R.string.midi_mp_RCV_HOLD1, 0, 1, 1, null, null, MidiPart::rcvHold),
    RCV_PORTA(0x3d, R.string.midi_mp_RCV_PORTA, 0, 1, 1, null, null, MidiPart::rcvPorta),
    RCV_SUST(0x3e, R.string.midi_mp_RCV_SUST, 0, 1, 1, null, null, MidiPart::rcvSust),
    RCV_SOFT_PEDAL(
        0x3f,
        R.string.midi_mp_RCV_SOFT_PEDAL,
        0,
        1,
        1,
        null,
        null,
        MidiPart::rcvSoftPedal
    ),
    RCV_BANK_SELECT(
        0x40,
        R.string.midi_mp_RCV_BANK_SELECT,
        0,
        1,
        1, null,
        null, MidiPart::rcvBankSelect
    ), // Default for XG = 1, GM = 0
    SCALE_TUNE_C(
        0x41,
        R.string.midi_mp_SCALE_TUNE_C,
        0,
        127,
        0x40,
        null,
        null,
        MidiPart::scaleTuneC
    ),
    SCALE_TUNE_CS(
        0x42,
        R.string.midi_mp_SCALE_TUNE_CS,
        0,
        127,
        0x40,
        null,
        null,
        MidiPart::scaleTuneCS
    ),
    SCALE_TUNE_D(
        0x43,
        R.string.midi_mp_SCALE_TUNE_D,
        0,
        127,
        0x40,
        null,
        null,
        MidiPart::scaleTuneD
    ),
    SCALE_TUNE_DS(
        0x44,
        R.string.midi_mp_SCALE_TUNE_DS,
        0,
        127,
        0x40,
        null,
        null,
        MidiPart::scaleTuneDS
    ),
    SCALE_TUNE_E(
        0x45,
        R.string.midi_mp_SCALE_TUNE_E,
        0,
        127,
        0x40,
        null,
        null,
        MidiPart::scaleTuneE
    ),
    SCALE_TUNE_F(
        0x46,
        R.string.midi_mp_SCALE_TUNE_F,
        0,
        127,
        0x40,
        null,
        null,
        MidiPart::scaleTuneF
    ),
    SCALE_TUNE_FS(
        0x47,
        R.string.midi_mp_SCALE_TUNE_FS,
        0,
        127,
        0x40,
        null,
        null,
        MidiPart::scaleTuneFS
    ),
    SCALE_TUNE_G(
        0x48,
        R.string.midi_mp_SCALE_TUNE_G,
        0,
        127,
        0x40,
        null,
        null,
        MidiPart::scaleTuneG
    ),
    SCALE_TUNE_GS(
        0x49,
        R.string.midi_mp_SCALE_TUNE_GS,
        0,
        127,
        0x40,
        null,
        null,
        MidiPart::scaleTuneGS
    ),
    SCALE_TUNE_A(
        0x4a,
        R.string.midi_mp_SCALE_TUNE_A,
        0,
        127,
        0x40,
        null,
        null,
        MidiPart::scaleTuneA
    ),
    SCALE_TUNE_AS(
        0x4b,
        R.string.midi_mp_SCALE_TUNE_AS,
        0,
        127,
        0x40,
        null,
        null,
        MidiPart::scaleTuneAS
    ),
    SCALE_TUNE_B(
        0x4c,
        R.string.midi_mp_SCALE_TUNE_B,
        0,
        127,
        0x40,
        null,
        null,
        MidiPart::scaleTuneB
    ),
    CAT_PITCH_CTRL(
        0x4d,
        R.string.midi_mp_CAT_PITCH_CTRL,
        0x28,
        0x58,
        0x40, null,
        null, MidiPart::catPitchControl
    ),
    CAT_FILTER_CTRL(
        0x4e,
        R.string.midi_mp_CAT_FILTER_CTRL,
        0,
        127,
        0x40, null,
        null, MidiPart::catFilterControl
    ),
    CAT_AMP_CTRL(
        0x4f,
        R.string.midi_mp_CAT_AMP_CTRL,
        0,
        127,
        0x40,
        null,
        null,
        MidiPart::catAmplControl
    ),
    CAT_LFO_PMOD_DEPTH(
        0x50,
        R.string.midi_mp_CAT_LFO_PMOD_DEPTH,
        0,
        127,
        0, null,
        null, MidiPart::catLfoPmodDepth
    ),
    CAT_LFO_FMOD_DEPTH(
        0x51,
        R.string.midi_mp_CAT_LFO_FMOD_DEPTH,
        0,
        127,
        0,
        null,
        null, MidiPart::catLfoFmodDepth
    ),
    CAT_LFO_AMOD_DEPTH(
        0x52,
        R.string.midi_mp_CAT_LFO_AMOD_DEPTH,
        0,
        127,
        0, null,
        null, MidiPart::catLfoAmodDepth
    ),
    PAT_PITCH_CTRL(
        0x53,
        R.string.midi_mp_PAT_PITCH_CTRL,
        0x28,
        0x58,
        0x40, null,
        null, MidiPart::patPitchControl
    ),
    PAT_FILTER_CTRL(
        0x54,
        R.string.midi_mp_PAT_FILTER_CTRL,
        0,
        127,
        0x40, null,
        null, MidiPart::patFilterControl
    ),
    PAT_AMP_CTRL(
        0x55,
        R.string.midi_mp_PAT_AMP_CTRL,
        0,
        127,
        0x40,
        null,
        null,
        MidiPart::patAmplControl
    ),
    PAT_LFO_PMOD_DEPTH(
        0x56,
        R.string.midi_mp_PAT_LFO_PMOD_DEPTH,
        0,
        127,
        0, null,
        null, MidiPart::patLfoPmodDepth
    ),
    PAT_LFO_FMOD_DEPTH(
        0x57,
        R.string.midi_mp_PAT_LFO_FMOD_DEPTH,
        0,
        127,
        0, null,
        null, MidiPart::patLfoFmodDepth
    ),
    PAT_LFO_AMOD_DEPTH(
        0x58,
        R.string.midi_mp_PAT_LFO_AMOD_DEPTH,
        0,
        127,
        0, null,
        null, MidiPart::patLfoAmodDepth
    ),
    AC1_CTRL_NUMBER(
        0x59,
        R.string.midi_mp_AC1_CTRL_NUMBER,
        0,
        0x5f,
        0x10,
        null,
        null, MidiPart::ac1CtrlNumber
    ),
    AC1_PITCH_CTRL(
        0x5a,
        R.string.midi_mp_AC1_PITCH_CTRL,
        0x28,
        0x58,
        0x40,
        null,
        null, MidiPart::ac1PitchCtrl
    ),
    AC1_FILTER_CTRL(
        0x5b,
        R.string.midi_mp_AC1_FILTER_CTRL,
        0,
        127,
        0x40,
        null,
        null, MidiPart::ac1FilterCtrl
    ),
    AC1_AMP_CTRL(
        0x5c,
        R.string.midi_mp_AC1_AMP_CTRL,
        0,
        127,
        0x40,
        null,
        null,
        MidiPart::ac1AmpCtrl
    ),
    AC1_LFO_PMOD_DEPTH(
        0x5d,
        R.string.midi_mp_AC1_LFO_PMOD_DEPTH,
        0,
        127,
        0, null,
        null, MidiPart::ac1LfoPmodDepth
    ),
    AC1_LFO_FMOD_DEPTH(
        0x5e,
        R.string.midi_mp_AC1_LFO_FMOD_DEPTH,
        0,
        127,
        0, null,
        null, MidiPart::ac1LfoFmodDepth
    ),
    AC1_LFO_AMOD_DEPTH(
        0x5f,
        R.string.midi_mp_AC1_LFO_AMOD_DEPTH,
        0,
        127,
        0, null,
        null, MidiPart::ac1LfoAmodDepth
    ),
    AC2_CTRL_NUMBER(
        0x60,
        R.string.midi_mp_AC2_CTRL_NUMBER,
        0,
        0x5f,
        0x11,
        null,
        null, MidiPart::ac2CtrlNumber
    ),
    AC2_PITCH_CTRL(
        0x61,
        R.string.midi_mp_AC2_PITCH_CTRL,
        0x28,
        0x58,
        0x40,
        null,
        null, MidiPart::ac2PitchCtrl
    ),
    AC2_FILTER_CTRL(
        0x62,
        R.string.midi_mp_AC2_FILTER_CTRL,
        0,
        127,
        0x40,
        null,
        null, MidiPart::ac2FilterCtrl
    ),
    AC2_AMP_CTRL(
        0x63,
        R.string.midi_mp_AC2_AMP_CTRL,
        0,
        127,
        0x40,
        null,
        null,
        MidiPart::ac2AmpCtrl
    ),
    AC2_LFO_PMOD_DEPTH(
        0x64,
        R.string.midi_mp_AC2_LFO_PMOD_DEPTH,
        0,
        127,
        0, null,
        null, MidiPart::ac2LfoPmodDepth
    ),
    AC2_LFO_FMOD_DEPTH(
        0x65,
        R.string.midi_mp_AC2_LFO_FMOD_DEPTH,
        0,
        127,
        0, null,
        null, MidiPart::ac2LfoFmodDepth
    ),
    AC2_LFO_AMOD_DEPTH(
        0x66,
        R.string.midi_mp_AC2_LFO_AMOD_DEPTH,
        0,
        127,
        0, null,
        null, MidiPart::ac2LfoAmodDepth
    ),
    PORTA_SWITCH(
        0x67,
        R.string.midi_mp_PORTA_SWITCH,
        0,
        1,
        0,
        MidiControlChange.PORTA,
        null, MidiPart::portaSwitch
    ),
    PORTA_TIME(0x68, R.string.midi_mp_PORTA_TIME, 0, 127, 0, null, null, MidiPart::portaTime),
    PITCH_EG_INIT_LVL(
        0x69,
        R.string.midi_mp_PITCH_EG_INIT_LVL,
        0,
        127,
        0x40, null,
        null, MidiPart::pitchEgInitLvl
    ),
    PITCH_EG_ATTACK_TIME(
        0x6a,
        R.string.midi_mp_PITCH_EG_ATTACK_TIME,
        0,
        127,
        0x40, null,
        null, MidiPart::pitchEgAttackTime
    ),
    PITCH_EG_REL_LVL(
        0x6b,
        R.string.midi_mp_PITCH_EG_REL_LVL,
        0,
        127,
        0x40, null,
        null, MidiPart::pitchEgRelLvl
    ),
    PITCH_EG_REL_TIME(
        0x6c,
        R.string.midi_mp_PITCH_EG_REL_TIME,
        0,
        127,
        0x40, null,
        null, MidiPart::pitchEgRelTime
    ),
    VEL_LIMIT_LOW(
        0x6d,
        R.string.midi_mp_VEL_LIMIT_LOW,
        1,
        127,
        1,
        null,
        null,
        MidiPart::velocityLimitLo
    ),
    VEL_LIMIT_HIGH(
        0x6e,
        R.string.midi_mp_VEL_LIMIT_HIGH,
        1,
        127,
        127,
        null,
        null, MidiPart::velocityLimitHi
    )
}