package com.example.xgbuddy.data

import com.example.xgbuddy.R

enum class MidiParameter(val addrLo: Byte, val descriptionRes: Int, val min: Byte, val max: Byte, val default: Byte) {
    ELEMENT_RESERVE(0, R.string.midi_mp_el_reserve, 0, 32, 2), // For drum, default is 0
    BANK_MSB(
        1,
        R.string.midi_mp_BANK_MSB,
        0,
        127,
        0
    ),                        // For drum, default is 127
    BANK_LSB(2, R.string.midi_mp_BANK_LSB, 0, 127, 0),
    PROG_NUMBER(
        3,
        R.string.midi_mp_PROG_NUMBER,
        0,
        127,
        0
    ),                      // Default is part num
    RCV_CHANNEL(
        4,
        R.string.midi_mp_RCV_CHANNEL,
        0,
        15,
        0
    ),                  // Default is part num, 0x7f also acceptable as OFF
    POLY_MODE(5, R.string.midi_mp_POLY_MODE, 0, 1, 1),
    KEY_ON_ASSIGN(6, R.string.midi_mp_KEY_ON_ASSIGN, 0, 2, 1),
    PART_MODE(
        7,
        R.string.midi_mp_PART_MODE,
        0,
        3,
        0
    ),                        // For drum, default is 2
    NOTE_SHIFT(8, R.string.midi_mp_NOTE_SHIFT, 0x28, 0x58, 0x40),

    //    DETUNE(9, R.string.midi_mp_DETUNE, ), /* TODO: Create special case for detune */
    VOLUME(0x0b, R.string.midi_mp_VOLUME, 0, 127, 64),
    VEL_SENS_DEPTH(0x0c, R.string.midi_mp_VEL_SENS_DEPTH, 0, 127, 64),
    VEL_SENS_OFFSET(0x0d, R.string.midi_mp_VEL_SENS_OFFSET, 0, 127, 64),
    PAN(0x0e, R.string.midi_mp_PAN, 0, 127, 64), // 0: Random, L1 - C64 - R127
    NOTE_LIMIT_LOW(0x0f, R.string.midi_mp_NOTE_LIMIT_LOW, 0, 127, 0),
    NOTE_LIMIT_HIGH(0x10, R.string.midi_mp_NOTE_LIMIT_HIGH, 0, 127, 127),
    DRY_LEVEL(0x11, R.string.midi_mp_DRY_LEVEL, 0, 127, 127),
    CHORUS_SEND(0x12, R.string.midi_mp_CHORUS_SEND, 0, 127, 0),
    REVERB_SEND(0x13, R.string.midi_mp_REVERB_SEND, 0, 127, 0x28),
    VARI_SEND(0x14, R.string.midi_mp_VARI_SEND, 0, 127, 0),
    VIBRATO_RATE(0x15, R.string.midi_mp_VIBRATO_RATE, 0, 127, 0x40),
    VIBRATO_DEPTH(0x16, R.string.midi_mp_VIBRATO_DEPTH, 0, 127, 0x40),
    VIBRATO_DELAY(0x17, R.string.midi_mp_VIBRATO_DELAY, 0, 127, 0x40),
    CUTOFF_FREQ(0x18, R.string.midi_mp_CUTOFF_FREQ, 0, 127, 0x40),
    RESONANCE(0x19, R.string.midi_mp_RESONANCE, 0, 127, 0x40),
    EG_ATTACK_TIME(0x1a, R.string.midi_mp_EG_ATTACK_TIME, 0, 127, 0x40),
    EG_DECAY_TIME(0x1b, R.string.midi_mp_EG_DECAY_TIME, 0, 127, 0x40),
    EG_RELEASE_TIME(0x1c, R.string.midi_mp_EG_RELEASE_TIME, 0, 127, 0x40),
    MW_PITCH_CTRL(0x1d, R.string.midi_mp_MW_PITCH_CTRL, 0x28, 0x58, 0x40),
    MW_FILTER_CTRL(0x1e, R.string.midi_mp_MW_FILTER_CTRL, 0, 127, 0x40),
    MW_AMP_CTRL(0x1f, R.string.midi_mp_MW_AMP_CTRL, 0, 127, 0x40),
    MW_LFO_PMOD_DEPTH(0x20, R.string.midi_mp_MW_LFO_PMOD_DEPTH, 0, 127, 0x0a),
    MW_LFO_FMOD_DEPTH(0x21, R.string.midi_mp_MW_LFO_FMOD_DEPTH, 0, 127, 0),
    MW_LFO_AMOD_DEPTH(0x22, R.string.midi_mp_MW_LFO_AMOD_DEPTH, 0, 127, 0),
    BEND_PITCH_CTRL(0x23, R.string.midi_mp_BEND_PITCH_CTRL, 0x28, 0x58, 0x42),
    BEND_FILTER_CTRL(0x24, R.string.midi_mp_BEND_FILTER_CTRL, 0, 127, 0x40),
    BEND_AMP_CTRL(0x25, R.string.midi_mp_BEND_AMP_CTRL, 0, 127, 0x40),
    BEND_LFO_PMOD_DEPTH(0x26, R.string.midi_mp_BEND_LFO_PMOD_DEPTH, 0, 127, 0x40),
    BEND_LFO_FMOD_DEPTH(0x27, R.string.midi_mp_BEND_LFO_FMOD_DEPTH, 0, 127, 0x40),
    BEND_LFO_AMOD_DEPTH(0x28, R.string.midi_mp_BEND_LFO_AMOD_DEPTH, 0, 127, 0x40),
    RCV_PITCH_BEND(0x30, R.string.midi_mp_RCV_PITCH_BEND, 0, 1, 1),
    RCV_CH_AFTER_TOUCH(0x31, R.string.midi_mp_RCV_CH_AFTER_TOUCH, 0, 1, 1),
    RCV_PROGRAM_CHANGE(0x32, R.string.midi_mp_RCV_PROGRAM_CHANGE, 0, 1, 1),
    RCV_CONTROL_CHANGE(0x33, R.string.midi_mp_RCV_CONTROL_CHANGE, 0, 1, 1),
    RCV_POLY_AFTER_TOUCH(0x34, R.string.midi_mp_RCV_POLY_AFTER_TOUCH, 0, 1, 1),
    RCV_NOTE_MESSAGE(0x35, R.string.midi_mp_RCV_NOTE_MESSAGE, 0, 1, 1),
    RCV_RPN(0x36, R.string.midi_mp_RCV_RPN, 0, 1, 1),
    RCV_NRPN(0x37, R.string.midi_mp_RCV_NRPN, 0, 1, 1), // Default for XG = 1, GM = 0
    RCV_MOD(0x38, R.string.midi_mp_RCV_MOD, 0, 1, 1),
    RCV_VOLUME(0x39, R.string.midi_mp_RCV_VOLUME, 0, 1, 1),
    RCV_PAN(0x3a, R.string.midi_mp_RCV_PAN, 0, 1, 1),
    RCV_EXPRESSION(0x3b, R.string.midi_mp_RCV_EXPRESSION, 0, 1, 1),
    RCV_HOLD1(0x3c, R.string.midi_mp_RCV_HOLD1, 0, 1, 1),
    RCV_PORTA(0x3d, R.string.midi_mp_RCV_PORTA, 0, 1, 1),
    RCV_SUST(0x3e, R.string.midi_mp_RCV_SUST, 0, 1, 1),
    RCV_SOFT_PEDAL(0x3f, R.string.midi_mp_RCV_SOFT_PEDAL, 0, 1, 1),
    RCV_BANK_SELECT(0x40, R.string.midi_mp_RCV_BANK_SELECT, 0, 1, 1), // Default for XG = 1, GM = 0
    SCALE_TUNE_C(0x41, R.string.midi_mp_SCALE_TUNE_C, 0, 127, 0x40),
    SCALE_TUNE_CS(0x42, R.string.midi_mp_SCALE_TUNE_CS, 0, 127, 0x40),
    SCALE_TUNE_D(0x43, R.string.midi_mp_SCALE_TUNE_D, 0, 127, 0x40),
    SCALE_TUNE_DS(0x44, R.string.midi_mp_SCALE_TUNE_DS, 0, 127, 0x40),
    SCALE_TUNE_E(0x45, R.string.midi_mp_SCALE_TUNE_E, 0, 127, 0x40),
    SCALE_TUNE_F(0x46, R.string.midi_mp_SCALE_TUNE_F, 0, 127, 0x40),
    SCALE_TUNE_FS(0x47, R.string.midi_mp_SCALE_TUNE_FS, 0, 127, 0x40),
    SCALE_TUNE_G(0x48, R.string.midi_mp_SCALE_TUNE_G, 0, 127, 0x40),
    SCALE_TUNE_GS(0x49, R.string.midi_mp_SCALE_TUNE_GS, 0, 127, 0x40),
    SCALE_TUNE_A(0x4a, R.string.midi_mp_SCALE_TUNE_A, 0, 127, 0x40),
    SCALE_TUNE_AS(0x4b, R.string.midi_mp_SCALE_TUNE_AS, 0, 127, 0x40),
    SCALE_TUNE_B(0x4c, R.string.midi_mp_SCALE_TUNE_B, 0, 127, 0x40),
    CAT_PITCH_CTRL(0x4d, R.string.midi_mp_CAT_PITCH_CTRL, 0x28, 0x58, 0x40),
    CAT_FILTER_CTRL(0x4e, R.string.midi_mp_CAT_FILTER_CTRL, 0, 127, 0x40),
    CAT_AMP_CTRL(0x4f, R.string.midi_mp_CAT_AMP_CTRL, 0, 127, 0x40),
    CAT_LFO_PMOD_DEPTH(0x50, R.string.midi_mp_CAT_LFO_PMOD_DEPTH, 0, 127, 0),
    CAT_LFO_FMOD_DEPTH(0x51, R.string.midi_mp_CAT_LFO_FMOD_DEPTH, 0, 127, 0),
    CAT_LFO_AMOD_DEPTH(0x52, R.string.midi_mp_CAT_LFO_AMOD_DEPTH, 0, 127, 0),
    PAT_PITCH_CTRL(0x53, R.string.midi_mp_PAT_PITCH_CTRL, 0x28, 0x58, 0x40),
    PAT_FILTER_CTRL(0x54, R.string.midi_mp_PAT_FILTER_CTRL, 0, 127, 0x40),
    PAT_AMP_CTRL(0x55, R.string.midi_mp_PAT_AMP_CTRL, 0, 127, 0x40),
    PAT_LFO_PMOD_DEPTH(0x56, R.string.midi_mp_PAT_LFO_PMOD_DEPTH, 0, 127, 0),
    PAT_LFO_FMOD_DEPTH(0x57, R.string.midi_mp_PAT_LFO_FMOD_DEPTH, 0, 127, 0),
    PAT_LFO_AMOD_DEPTH(0x58, R.string.midi_mp_PAT_LFO_AMOD_DEPTH, 0, 127, 0),
    AC1_CTRL_NUMBER(0x59, R.string.midi_mp_AC1_CTRL_NUMBER, 0, 0x5f, 0x10),
    AC1_PITCH_CTRL(0x5a, R.string.midi_mp_AC1_PITCH_CTRL, 0x28, 0x58, 0x40),
    AC1_FILTER_CTRL(0x5b, R.string.midi_mp_AC1_FILTER_CTRL, 0, 127, 0x40),
    AC1_AMP_CTRL(0x5c, R.string.midi_mp_AC1_AMP_CTRL, 0, 127, 0x40),
    AC1_LFO_PMOD_DEPTH(0x5d, R.string.midi_mp_AC1_LFO_PMOD_DEPTH, 0, 127, 0),
    AC1_LFO_FMOD_DEPTH(0x5e, R.string.midi_mp_AC1_LFO_FMOD_DEPTH, 0, 127, 0),
    AC1_LFO_AMOD_DEPTH(0x5f, R.string.midi_mp_AC1_LFO_AMOD_DEPTH, 0, 127, 0),
    AC2_CTRL_NUMBER(0x60, R.string.midi_mp_AC2_CTRL_NUMBER, 0, 0x5f, 0x11),
    AC2_PITCH_CTRL(0x61, R.string.midi_mp_AC2_PITCH_CTRL, 0x28, 0x58, 0x40),
    AC2_FILTER_CTRL(0x62, R.string.midi_mp_AC2_FILTER_CTRL, 0, 127, 0x40),
    AC2_AMP_CTRL(0x63, R.string.midi_mp_AC2_AMP_CTRL, 0, 127, 0x40),
    AC2_LFO_PMOD_DEPTH(0x64, R.string.midi_mp_AC2_LFO_PMOD_DEPTH, 0, 127, 0),
    AC2_LFO_FMOD_DEPTH(0x65, R.string.midi_mp_AC2_LFO_FMOD_DEPTH, 0, 127, 0),
    AC2_LFO_AMOD_DEPTH(0x66, R.string.midi_mp_AC2_LFO_AMOD_DEPTH, 0, 127, 0),
    PORTA_SWITCH(0x67, R.string.midi_mp_PORTA_SWITCH, 0, 1, 0),
    PORTA_TIME(0x68, R.string.midi_mp_PORTA_TIME, 0, 127, 0),
    PITCH_EG_INIT_LVL(0x69, R.string.midi_mp_PITCH_EG_INIT_LVL, 0, 127, 0x40),
    PITCH_EG_ATTACK_TIME(0x6a, R.string.midi_mp_PITCH_EG_ATTACK_TIME, 0, 127, 0x40),
    PITCH_EG_REL_LVL(0x6b, R.string.midi_mp_PITCH_EG_REL_LVL, 0, 127, 0x40),
    PITCH_EG_REL_TIME(0x6c, R.string.midi_mp_PITCH_EG_REL_TIME, 0, 127, 0x40),
    VEL_LIMIT_LOW(0x6d, R.string.midi_mp_VEL_LIMIT_LOW, 1, 127, 1),
    VEL_LIMIT_HIGH(0x6e, R.string.midi_mp_VEL_LIMIT_HIGH, 1, 127, 127)
}