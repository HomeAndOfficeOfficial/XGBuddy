package com.example.xgbuddy.data.qs300

import com.example.xgbuddy.R
import com.example.xgbuddy.util.DataFormatUtil
import kotlin.reflect.KMutableProperty

enum class QS300ElementParameter(
    val nameRes: Int,
    val baseAddress: UByte,
    val min: Byte = 0,
    val max: Byte = 127,
    val default: Byte = 0,
    val reflectedField: KMutableProperty<Byte>,
    val formatter: DataFormatUtil.DataAssignFormatter = DataFormatUtil.noFormat
) {
    WAVE_HI(R.string.qs300_el_wave_hi, 0x3du, 7, 13, 7, QS300Element::waveHi),
    WAVE_LO(R.string.qs300_el_wave_lo, 0x3eu, max = 6, reflectedField = QS300Element::waveLo),
    NOTE_LO(R.string.qs300_el_note_lo, 0x3fu, reflectedField = QS300Element::noteLo),
    NOTE_HI(R.string.qs300_el_note_hi, 0x40u, default = 127, reflectedField = QS300Element::noteHi),
    VEL_LO(R.string.qs300_el_vel_lo, 0x41u, reflectedField = QS300Element::velLo),
    VEL_HI(R.string.qs300_el_vel_hi, 0x42u, default = 127, reflectedField = QS300Element::velHi),
    FILTER_EG_VEL_CURVE(
        R.string.qs300_el_feg_vel_curve,
        0x43u,
        max = 1,
        reflectedField = QS300Element::filterEGVelCurve,
        formatter = DataFormatUtil.filterCurveFormatter
    ),
    LFO_WAVE(
        R.string.qs300_el_lfo_wave,
        0x44u,
        max = 2,
        reflectedField = QS300Element::lfoWave,
        formatter = DataFormatUtil.waveShapeFormatter
    ),
    LFO_PHASE_INIT(
        R.string.qs300_el_lfo_phase_init,
        0x45u,
        max = 1,
        reflectedField = QS300Element::lfoPhaseInit,
        formatter = DataFormatUtil.onOffFormatter
    ),
    LFO_SPEED(
        R.string.qs300_el_lfo_speed,
        0x46u,
        max = 63,
        reflectedField = QS300Element::lfoSpeed
    ),
    LFO_DELAY(R.string.qs300_el_lfo_delay, 0x47u, reflectedField = QS300Element::lfoDelay),
    LFO_FADE_TIME(R.string.qs300_el_lfo_fade, 0x48u, reflectedField = QS300Element::lfoFadeTime),
    LFO_PMD_DEPTH(
        R.string.qs300_el_lfo_pmd_depth,
        0x49u,
        max = 63,
        reflectedField = QS300Element::lfoPmdDepth
    ),
    LFO_CMD_DEPTH(
        R.string.qs300_el_lfo_cmd_depth,
        0x4au,
        max = 15,
        reflectedField = QS300Element::lfoCmdDepth
    ),
    LFO_AMD_DEPTH(
        R.string.qs300_el_lfo_amd_depth,
        0x4bu,
        max = 31,
        reflectedField = QS300Element::lfoAmdDepth
    ),
    NOTE_SHIFT(R.string.qs300_el_note_shift, 0x4cu, 32, 96, 64, QS300Element::noteShift), // -32..32
    DETUNE(
        R.string.qs300_el_detune,
        0x4du,
        14,
        114,
        64,
        QS300Element::detune,
        DataFormatUtil.signed64Base
    ), // -50 .. 50
    PITCH_SCALING(
        R.string.qs300_el_pitch_scaling,
        0x4eu,
        max = 5,
        reflectedField = QS300Element::pitchScaling,
        formatter = DataFormatUtil.pitchScaleFormatter
    ),
    PITCH_SCALING_CENTER(
        R.string.qs300_el_pitch_scale_center,
        0x4fu,
        default = 63,
        reflectedField = QS300Element::pitchScalingCenter
    ),
    PITCH_EG_DEPTH(
        R.string.qs300_el_peg_depth,
        0x50u,
        0,
        3,
        1,
        QS300Element::pitchEgDepth,
        DataFormatUtil.pegDepthFormatter
    ),
    VEL_PEG_LVL_SENS(
        R.string.qs300_el_vel_peg_lvl_sens,
        0x51u,
        57,
        71,
        64,
        QS300Element::velPegLvlSens,
        DataFormatUtil.signed64Base
    ),
    VEL_PEG_RATE_SENS(
        R.string.qs300_el_vel_peg_rate_sens,
        0x52u,
        57,
        71,
        64,
        QS300Element::velPegRateSens,
        DataFormatUtil.signed64Base
    ),
    PEG_RATE_SCALING(
        R.string.qs300_el_peg_rate_scaling,
        0x53u,
        57,
        71,
        64,
        QS300Element::pegRateScaling,
        DataFormatUtil.signed64Base
    ),
    PEG_RATE_SCALING_CENTER(
        R.string.qs300_el_peg_rate_scale_center,
        0x54u,
        57,
        71,
        64,
        QS300Element::pegRateScalingCenter,
        DataFormatUtil.signed64Base
    ),
    PEG_RATE_1(
        R.string.qs300_el_peg_rate_1,
        0x55u,
        max = 63,
        reflectedField = QS300Element::pegRate1
    ),
    PEG_RATE_2(
        R.string.qs300_el_peg_rate_2,
        0x56u,
        max = 63,
        reflectedField = QS300Element::pegRate2
    ),
    PEG_RATE_3(
        R.string.qs300_el_peg_rate_3,
        0x57u,
        max = 63,
        reflectedField = QS300Element::pegRate3
    ),
    PEG_RATE_4(
        R.string.qs300_el_peg_rate_4,
        0x58u,
        max = 63,
        reflectedField = QS300Element::pegRate4
    ),
    PEG_LEVEL_0(R.string.qs300_el_peg_lvl_0, 0x59u, reflectedField = QS300Element::pegLvl0),
    PEG_LEVEL_1(R.string.qs300_el_peg_lvl_1, 0x5au, reflectedField = QS300Element::pegLvl1),
    PEG_LEVEL_2(R.string.qs300_el_peg_lvl_2, 0x5bu, reflectedField = QS300Element::pegLvl2),
    PEG_LEVEL_3(R.string.qs300_el_peg_lvl_3, 0x5cu, reflectedField = QS300Element::pegLvl3),
    PEG_LEVEL_4(R.string.qs300_el_peg_lvl_4, 0x5du, reflectedField = QS300Element::pegLvl4),
    FILTER_RES(
        R.string.qs300_el_filter_res,
        0x5eu,
        max = 63,
        reflectedField = QS300Element::filterRes
    ),
    VEL_SENS(
        R.string.qs300_el_vel_sens,
        0x5fu,
        max = 7,
        default = 3,
        reflectedField = QS300Element::velSens
    ),
    CUTOFF_FREQ(
        R.string.qs300_el_cutoff_freq,
        0x60u,
        default = 63,
        reflectedField = QS300Element::cutoffFreq
    ),
    CUTOFF_SCALING_BREAK_1(
        R.string.qs300_el_cutoff_scale_bp_1,
        0x61u,
        default = 63,
        reflectedField = QS300Element::cutoffScalingBreak1
    ),
    CUTOFF_SCALING_BREAK_2(
        R.string.qs300_el_cutoff_scale_bp_2,
        0x62u,
        default = 63,
        reflectedField = QS300Element::cutoffScalingBreak2
    ),
    CUTOFF_SCALING_BREAK_3(
        R.string.qs300_el_cutoff_scale_bp_3,
        0x63u,
        default = 63,
        reflectedField = QS300Element::cutoffScalingBreak3
    ),
    CUTOFF_SCALING_BREAK_4(
        R.string.qs300_el_cutoff_scale_bp_4,
        0x64u,
        default = 63,
        reflectedField = QS300Element::cutoffScalingBreak4
    ),
    CUTOFF_SCALING_OFFSET_1(
        R.string.qs300_el_cutoff_scale_off_1,
        0x65u,
        default = 63,
        reflectedField = QS300Element::cutoffScalingOffset1
    ),
    CUTOFF_SCALING_OFFSET_2(
        R.string.qs300_el_cutoff_scale_off_2,
        0x66u,
        default = 63,
        reflectedField = QS300Element::cutoffScalingOffset2
    ),
    CUTOFF_SCALING_OFFSET_3(
        R.string.qs300_el_cutoff_scale_off_3,
        0x67u,
        default = 63,
        reflectedField = QS300Element::cutoffScalingOffset3
    ),
    CUTOFF_SCALING_OFFSET_4(
        R.string.qs300_el_cutoff_scale_off_4,
        0x68u,
        default = 63,
        reflectedField = QS300Element::cutoffScalingOffset4
    ),
    VEL_FEG_LVL_SENS(
        R.string.qs300_el_vel_feg_lvl_sens,
        0x69u,
        57,
        71,
        64,
        QS300Element::velFegLvlSens
    ),
    VEL_FEG_RATE_SENS(
        R.string.qs300_el_vel_feg_rate_sens,
        0x6au,
        57,
        71,
        64,
        QS300Element::velFegRateSens,
        DataFormatUtil.signed64Base
    ),
    FEG_RATE_SCALING(
        R.string.qs300_el_feg_rate_scaling,
        0x6bu,
        57,
        71,
        64,
        QS300Element::fegRateScaling,
        DataFormatUtil.signed64Base
    ),
    FEG_RATE_SCALING_CENTER(
        R.string.qs300_el_feg_rate_scale_center,
        0x6cu,
        default = 63,
        reflectedField = QS300Element::fegRateScalingCenter
    ),
    FEG_RATE_1(R.string.qs300_el_feg_rate_1, 0x6du, 0, 63, 0, QS300Element::fegRate1), FEG_RATE_2(
        R.string.qs300_el_feg_rate_2,
        0x6eu,
        max = 63,
        reflectedField = QS300Element::fegRate2
    ),
    FEG_RATE_3(
        R.string.qs300_el_feg_rate_3,
        0x6fu,
        max = 63,
        reflectedField = QS300Element::fegRate3
    ),
    FEG_RATE_4(
        R.string.qs300_el_feg_rate_4,
        0x70u,
        max = 63,
        reflectedField = QS300Element::fegRate4
    ),
    FEG_LVL_0(R.string.qs300_el_feg_lvl_0, 0x71u, reflectedField = QS300Element::fegLvl0),
    FEG_LVL_1(R.string.qs300_el_feg_lvl_1, 0x72u, reflectedField = QS300Element::fegLvl1),
    FEG_LVL_2(R.string.qs300_el_feg_lvl_2, 0x73u, reflectedField = QS300Element::fegLvl2),
    FEG_LVL_3(R.string.qs300_el_feg_lvl_3, 0x74u, reflectedField = QS300Element::fegLvl3),
    FEG_LVL_4(R.string.qs300_el_feg_lvl_4, 0x75u, reflectedField = QS300Element::fegLvl4),
    ELEMENT_LVL(
        R.string.qs300_el_element_lvl,
        0x76u,
        default = 100,
        reflectedField = QS300Element::elementLvl
    ),
    LVL_SCALING_BREAK_1(
        R.string.qs300_el_lvl_scale_bp_1,
        0x77u,
        default = 63,
        reflectedField = QS300Element::lvlScalingBreak1
    ),
    LVL_SCALING_BREAK_2(
        R.string.qs300_el_lvl_scale_bp_2,
        0x78u,
        default = 63,
        reflectedField = QS300Element::lvlScalingBreak2
    ),
    LVL_SCALING_BREAK_3(
        R.string.qs300_el_lvl_scale_bp_3,
        0x79u,
        default = 63,
        reflectedField = QS300Element::lvlScalingBreak3
    ),
    LVL_SCALING_BREAK_4(
        R.string.qs300_el_lvl_scale_bp_4,
        0x7au,
        default = 63,
        reflectedField = QS300Element::lvlScalingBreak4
    ),
    LVL_SCALING_OFFSET_1(
        R.string.qs300_el_lvl_scale_off_1,
        0x7bu,
        default = 63,
        reflectedField = QS300Element::lvlScalingOffset1,
        formatter = DataFormatUtil.signed63Base
    ),
    LVL_SCALING_OFFSET_2(
        R.string.qs300_el_lvl_scale_off_2,
        0x7cu,
        default = 63,
        reflectedField = QS300Element::lvlScalingOffset2,
        formatter = DataFormatUtil.signed63Base
    ),
    LVL_SCALING_OFFSET_3(
        R.string.qs300_el_lvl_scale_off_3,
        0x7du,
        default = 63,
        reflectedField = QS300Element::lvlScalingOffset3,
        formatter = DataFormatUtil.signed63Base
    ),
    LVL_SCALING_OFFSET_4(
        R.string.qs300_el_lvl_scale_off_4,
        0x7eu,
        default = 63,
        reflectedField = QS300Element::lvlScalingOffset4,
        formatter = DataFormatUtil.signed63Base
    ),
    VEL_CURVE(R.string.qs300_el_vel_curve, 0x7fu, 0, 6, 2, QS300Element::velCurve),
    PAN(R.string.qs300_el_pan, 0x80u, 0, 15, 7, QS300Element::pan, DataFormatUtil.pan7Formatter),
    AEG_RATE_SCALING(
        R.string.qs300_el_aeg_rate_scaling,
        0x81u,
        57,
        71,
        64,
        QS300Element::aegRateScaling,
        DataFormatUtil.signed64Base
    ),
    AEG_SCALING_CENTER(
        R.string.qs300_el_aeg_rate_scale_center,
        0x82u,
        default = 63,
        reflectedField = QS300Element::aegScalingCenter
    ),
    AEG_KEY_ON_DELAY(
        R.string.qs300_el_aeg_key_on_delay,
        0x83u,
        max = 15,
        reflectedField = QS300Element::aegKeyOnDelay
    ),
    AEG_ATTACK_RATE(R.string.qs300_el_aeg_attack, 0x84u, 0, 127, 63, QS300Element::aegAttackRate),
    AEG_DECAY_RATE_1(
        R.string.qs300_el_aeg_decay_rate_1,
        0x85u,
        default = 63,
        reflectedField = QS300Element::aegDecayRate1
    ),
    AEG_DECAY_RATE_2(
        R.string.qs300_el_aeg_decay_rate_2,
        0x86u,
        default = 63,
        reflectedField = QS300Element::aegDecayRate2
    ),
    AEG_RELEASE_RATE(
        R.string.qs300_el_aeg_release,
        0x87u,
        default = 63,
        reflectedField = QS300Element::aegReleaseRate
    ),
    AEG_DECAY_LVL_1(
        R.string.qs300_el_aeg_decay_lvl_1,
        0x88u,
        default = 63,
        reflectedField = QS300Element::aegDecayLvl1
    ),
    AEG_DECAY_LVL_2(
        R.string.qs300_el_aeg_decay_lvl_2,
        0x89u,
        default = 63,
        reflectedField = QS300Element::aegDecayLvl2
    ),
    ADDRESS_OFFSET_HI(
        R.string.qs300_el_addr_off_hi,
        0x8au,
        7,
        13,
        7,
        QS300Element::addressOffsetHi
    ),
    ADDRESS_OFFSET_LOW(
        R.string.qs300_el_addr_off_lo,
        0x8bu,
        max = 6,
        reflectedField = QS300Element::addressOffsetLo
    ),
    RESONANCE_SENS(
        R.string.qs300_el_res_sens,
        0x8cu,
        57,
        71,
        64,
        QS300Element::resonanceSens,
        DataFormatUtil.signed64Base
    );
}