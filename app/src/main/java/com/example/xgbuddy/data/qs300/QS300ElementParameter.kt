package com.example.xgbuddy.data.qs300

import com.example.xgbuddy.R
import com.example.xgbuddy.data.MidiConstants
import com.example.xgbuddy.util.DataFormatUtil
import kotlin.reflect.KMutableProperty

enum class QS300ElementParameter(
    val baseAddress: UByte,
    val default: Byte,
    val min: Byte,
    val max: Byte,
    val descriptionRes: Int,
    val reflectedField: KMutableProperty<Byte>,
    val formatter: DataFormatUtil.DataAssignFormatter = DataFormatUtil.signed127Formatter
) {
    WAVE_HI(0x3du, 7, 7, 13, R.string.qs300_el_wave_hi, QS300Element::waveHi),
    WAVE_LO(0x3eu, 0, 0, 6, R.string.qs300_el_wave_lo, QS300Element::waveLo),
    NOTE_LO(0x3fu, 0, 0, 127, R.string.qs300_el_note_lo, QS300Element::noteLo),
    NOTE_HI(0x40u, 127, 0, 127, R.string.qs300_el_note_hi, QS300Element::noteHi),
    VEL_LO(0x41u, 0, 0, 127, R.string.qs300_el_vel_lo, QS300Element::velLo),
    VEL_HI(0x42u, 127, 0, 127, R.string.qs300_el_vel_hi, QS300Element::velHi),
    FILTER_EG_VEL_CURVE(
        0x43u,
        0,
        0,
        127,
        R.string.qs300_el_feg_vel_curve,
        QS300Element::filterEGVelCurve
    ),
    LFO_WAVE(0x44u, 0, 0, 2, R.string.qs300_el_lfo_wave, QS300Element::lfoWave),
    LFO_PHASE_INIT(0x45u, 0, 0, 1, R.string.qs300_el_lfo_phase_init, QS300Element::lfoPhaseInit),
    LFO_SPEED(0x46u, 0, 0, 63, R.string.qs300_el_lfo_speed, QS300Element::lfoSpeed),
    LFO_DELAY(0x47u, 0, 0, 127, R.string.qs300_el_lfo_delay, QS300Element::lfoDelay),
    LFO_FADE_TIME(0x48u, 0, 0, 127, R.string.qs300_el_lfo_fade, QS300Element::lfoFadeTime),
    LFO_PMD_DEPTH(0x49u, 0, 0, 63, R.string.qs300_el_lfo_pmd_depth, QS300Element::lfoPmdDepth),
    LFO_CMD_DEPTH(0x4au, 0, 0, 15, R.string.qs300_el_lfo_cmd_depth, QS300Element::lfoCmdDepth),
    LFO_AMD_DEPTH(0x4bu, 0, 0, 31, R.string.qs300_el_lfo_amd_depth, QS300Element::lfoAmdDepth),
    NOTE_SHIFT(0x4cu, 64, 32, 96, R.string.qs300_el_note_shift, QS300Element::noteShift), // -32..32
    DETUNE(0x4du, 64, 14, 114, R.string.qs300_el_detune, QS300Element::detune), // -50 .. 50
    PITCH_SCALING(0x4eu, 0, 0, 5, R.string.qs300_el_pitch_scaling, QS300Element::pitchScaling),
    PITCH_SCALING_CENTER(
        0x4fu,
        63,
        0,
        127,
        R.string.qs300_el_pitch_scale_center,
        QS300Element::pitchScalingCenter
    ),
    PITCH_EG_DEPTH(0x50u, 1, 0, 3, R.string.qs300_el_peg_depth, QS300Element::pitchEgDepth),
    VEL_PEG_LVL_SENS(
        0x51u,
        64,
        57,
        71,
        R.string.qs300_el_vel_peg_lvl_sens,
        QS300Element::velPegLvlSens
    ),
    VEL_PEG_RATE_SENS(
        0x52u,
        64,
        57,
        71,
        R.string.qs300_el_vel_peg_rate_sens,
        QS300Element::velPegRateSens
    ),
    PEG_RATE_SCALING(
        0x53u,
        64,
        57,
        71,
        R.string.qs300_el_peg_rate_scaling,
        QS300Element::pegRateScaling
    ),
    PEG_RATE_SCALING_CENTER(
        0x54u,
        64,
        57,
        71,
        R.string.qs300_el_peg_rate_scale_center,
        QS300Element::pegRateScalingCenter
    ),
    PEG_RATE_1(0x55u, 0, 0, 63, R.string.qs300_el_peg_rate_1, QS300Element::pegRate1),
    PEG_RATE_2(0x56u, 0, 0, 63, R.string.qs300_el_peg_rate_2, QS300Element::pegRate2),
    PEG_RATE_3(0x57u, 0, 0, 63, R.string.qs300_el_peg_rate_3, QS300Element::pegRate3),
    PEG_RATE_4(0x58u, 0, 0, 63, R.string.qs300_el_peg_rate_4, QS300Element::pegRate4),
    PEG_LEVEL_0(0x59u, 0, 0, 127, R.string.qs300_el_peg_lvl_0, QS300Element::pegLvl0),
    PEG_LEVEL_1(0x5au, 0, 0, 127, R.string.qs300_el_peg_lvl_1, QS300Element::pegLvl1),
    PEG_LEVEL_2(0x5bu, 0, 0, 127, R.string.qs300_el_peg_lvl_2, QS300Element::pegLvl2),
    PEG_LEVEL_3(0x5cu, 0, 0, 127, R.string.qs300_el_peg_lvl_3, QS300Element::pegLvl3),
    PEG_LEVEL_4(0x5du, 0, 0, 127, R.string.qs300_el_peg_lvl_4, QS300Element::pegLvl4),
    FILTER_RES(0x5eu, 0, 0, 63, R.string.qs300_el_filter_res, QS300Element::filterRes),
    VEL_SENS(0x5fu, 3, 0, 7, R.string.qs300_el_vel_sens, QS300Element::velSens),
    CUTOFF_FREQ(0x60u, 63, 0, 127, R.string.qs300_el_cutoff_freq, QS300Element::cutoffFreq),
    CUTOFF_SCALING_BREAK_1(
        0x61u,
        63,
        0,
        127,
        R.string.qs300_el_cutoff_scale_bp_1,
        QS300Element::cutoffScalingBreak1
    ),
    CUTOFF_SCALING_BREAK_2(
        0x62u,
        63,
        0,
        127,
        R.string.qs300_el_cutoff_scale_bp_2,
        QS300Element::cutoffScalingBreak2
    ),
    CUTOFF_SCALING_BREAK_3(
        0x63u,
        63,
        0,
        127,
        R.string.qs300_el_cutoff_scale_bp_3,
        QS300Element::cutoffScalingBreak3
    ),
    CUTOFF_SCALING_BREAK_4(
        0x64u,
        63,
        0,
        127,
        R.string.qs300_el_cutoff_scale_bp_4,
        QS300Element::cutoffScalingBreak4
    ),
    CUTOFF_SCALING_OFFSET_1(
        0x65u,
        63,
        0,
        127,
        R.string.qs300_el_cutoff_scale_off_1,
        QS300Element::cutoffScalingOffset1
    ),
    CUTOFF_SCALING_OFFSET_2(
        0x66u,
        63,
        0,
        127,
        R.string.qs300_el_cutoff_scale_off_2,
        QS300Element::cutoffScalingOffset2
    ),
    CUTOFF_SCALING_OFFSET_3(
        0x67u,
        63,
        0,
        127,
        R.string.qs300_el_cutoff_scale_off_3,
        QS300Element::cutoffScalingOffset3
    ),
    CUTOFF_SCALING_OFFSET_4(
        0x68u,
        63,
        0,
        127,
        R.string.qs300_el_cutoff_scale_off_4,
        QS300Element::cutoffScalingOffset4
    ),
    VEL_FEG_LVL_SENS(
        0x69u,
        64,
        57,
        71,
        R.string.qs300_el_vel_feg_lvl_sens,
        QS300Element::velFegLvlSens
    ),
    VEL_FEG_RATE_SENS(
        0x6au,
        64,
        57,
        71,
        R.string.qs300_el_vel_feg_rate_sens,
        QS300Element::velFegRateSens
    ),
    FEG_RATE_SCALING(
        0x6bu,
        64,
        57,
        71,
        R.string.qs300_el_feg_rate_scaling,
        QS300Element::fegRateScaling
    ),
    FEG_RATE_SCALING_CENTER(
        0x6cu,
        63,
        0,
        127,
        R.string.qs300_el_feg_rate_scale_center,
        QS300Element::fegRateScalingCenter
    ),
    FEG_RATE_1(0x6du, 0, 0, 63, R.string.qs300_el_feg_rate_1, QS300Element::fegRate1), FEG_RATE_2(
        0x6eu,
        0,
        0,
        63,
        R.string.qs300_el_feg_rate_2,
        QS300Element::fegRate2
    ),
    FEG_RATE_3(0x6fu, 0, 0, 63, R.string.qs300_el_feg_rate_3, QS300Element::fegRate3),
    FEG_RATE_4(0x70u, 0, 0, 63, R.string.qs300_el_feg_rate_4, QS300Element::fegRate4),
    FEG_LVL_0(0x71u, 0, 0, 127, R.string.qs300_el_feg_lvl_0, QS300Element::fegLvl0),
    FEG_LVL_1(0x72u, 0, 0, 127, R.string.qs300_el_feg_lvl_1, QS300Element::fegLvl1),
    FEG_LVL_2(0x73u, 0, 0, 127, R.string.qs300_el_feg_lvl_2, QS300Element::fegLvl2),
    FEG_LVL_3(0x74u, 0, 0, 127, R.string.qs300_el_feg_lvl_3, QS300Element::fegLvl3),
    FEG_LVL_4(0x75u, 0, 0, 127, R.string.qs300_el_feg_lvl_4, QS300Element::fegLvl4),
    ELEMENT_LVL(0x76u, 100, 0, 127, R.string.qs300_el_element_lvl, QS300Element::elementLvl),
    LVL_SCALING_BREAK_1(
        0x77u,
        63,
        0,
        127,
        R.string.qs300_el_lvl_scale_bp_1,
        QS300Element::lvlScalingBreak1
    ),
    LVL_SCALING_BREAK_2(
        0x78u,
        63,
        0,
        127,
        R.string.qs300_el_lvl_scale_bp_2,
        QS300Element::lvlScalingBreak2
    ),
    LVL_SCALING_BREAK_3(
        0x79u,
        63,
        0,
        127,
        R.string.qs300_el_lvl_scale_bp_3,
        QS300Element::lvlScalingBreak3
    ),
    LVL_SCALING_BREAK_4(
        0x7au,
        63,
        0,
        127,
        R.string.qs300_el_lvl_scale_bp_4,
        QS300Element::lvlScalingBreak4
    ),
    LVL_SCALING_OFFSET_1(
        0x7bu,
        63,
        0,
        127,
        R.string.qs300_el_lvl_scale_off_1,
        QS300Element::lvlScalingOffset1
    ),
    LVL_SCALING_OFFSET_2(
        0x7cu,
        63,
        0,
        127,
        R.string.qs300_el_lvl_scale_off_2,
        QS300Element::lvlScalingOffset2
    ),
    LVL_SCALING_OFFSET_3(
        0x7du,
        63,
        0,
        127,
        R.string.qs300_el_lvl_scale_off_3,
        QS300Element::lvlScalingOffset3
    ),
    LVL_SCALING_OFFSET_4(
        0x7eu,
        63,
        0,
        127,
        R.string.qs300_el_lvl_scale_off_4,
        QS300Element::lvlScalingOffset4
    ),
    VEL_CURVE(0x7fu, 2, 0, 6, R.string.qs300_el_vel_curve, QS300Element::velCurve),
    PAN(0x80u, 7, 0, 15, R.string.qs300_el_pan, QS300Element::pan),
    AEG_RATE_SCALING(
        0x81u,
        64,
        57,
        71,
        R.string.qs300_el_aeg_rate_scaling,
        QS300Element::aegRateScaling
    ),
    AEG_SCALING_CENTER(
        0x82u,
        63,
        0,
        127,
        R.string.qs300_el_aeg_rate_scale_center,
        QS300Element::aegScalingCenter
    ),
    AEG_KEY_ON_DELAY(
        0x83u,
        0,
        0,
        15,
        R.string.qs300_el_aeg_key_on_delay,
        QS300Element::aegKeyOnDelay
    ),
    AEG_ATTACK_RATE(0x84u, 63, 0, 127, R.string.qs300_el_aeg_attack, QS300Element::aegAttackRate),
    AEG_DECAY_RATE_1(
        0x85u,
        63,
        0,
        127,
        R.string.qs300_el_aeg_decay_rate_1,
        QS300Element::aegDecayRate1
    ),
    AEG_DECAY_RATE_2(
        0x86u,
        63,
        0,
        127,
        R.string.qs300_el_aeg_decay_rate_2,
        QS300Element::aegDecayRate2
    ),
    AEG_RELEASE_RATE(
        0x87u,
        63,
        0,
        127,
        R.string.qs300_el_aeg_release,
        QS300Element::aegReleaseRate
    ),
    AEG_DECAY_LVL_1(
        0x88u,
        63,
        0,
        127,
        R.string.qs300_el_aeg_decay_lvl_1,
        QS300Element::aegDecayLvl1
    ),
    AEG_DECAY_LVL_2(
        0x89u,
        63,
        0,
        127,
        R.string.qs300_el_aeg_decay_lvl_2,
        QS300Element::aegDecayLvl2
    ),
    ADDRESS_OFFSET_HI(
        0x8au,
        7,
        7,
        13,
        R.string.qs300_el_addr_off_hi,
        QS300Element::addressOffsetHi
    ),
    ADDRESS_OFFSET_LOW(
        0x8bu,
        0,
        0,
        6,
        R.string.qs300_el_addr_off_lo,
        QS300Element::addressOffsetLo
    ),
    RESONANCE_SENS(0x8cu, 64, 57, 71, R.string.qs300_el_res_sens, QS300Element::resonanceSens);

    fun getAddress(elementNumber: Int) = baseAddress + (elementNumber * 0x50).toUByte()

    //    fun getBaseAddress() = baseAddress
    fun getParamChangeAddressLo() =
        baseAddress - MidiConstants.OFFSET_QS300_ELEMENT_PARAM_CHANGE_ADDR
}