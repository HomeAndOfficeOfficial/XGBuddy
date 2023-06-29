package com.example.xgbuddy.data.xg

import com.example.xgbuddy.R
import com.example.xgbuddy.util.DataFormatUtil

/**
 * In addition to this, there will need to be an enum defining all the parameters as they pertain
 * to the XG parameter change sysex message. I'm not quite sure how to handle mapping the parameters
 * to the controls yet, but I know that the complete list will need to be defined. (Table 1-3 in the
 * manual).
 */

enum class EffectParameter(
    val nameRes: Int,
    val min: Int,
    val max: Int,
    val dataAssignFormatter: DataFormatUtil.DataAssignFormatter,
    val isAssignable: Boolean
) {
    DRY_WET(R.string.fxp_dry_wet, 1, 127, DataFormatUtil.dryWetAssignFormatter, true),
    REVERB_TIME(R.string.fxp_reverb_time, 0, 69, DataFormatUtil.reverbTimeFormatter, false),
    DIFFUSION(R.string.fxp_diffusion, 0, 10, DataFormatUtil.noFormat, false),
    INIT_DELAY(R.string.fxp_init_delay, 0, 63, DataFormatUtil.delayTimeFormatter, false),
    HPF_CUTOFF(R.string.fxp_hpf_cutoff, 0, 52, DataFormatUtil.eqFreqFormatter, false),
    LPF_CUTOFF(R.string.fxp_lpf_cutoff, 34, 60, DataFormatUtil.eqFreqFormatter, false),
    REV_DELAY(R.string.fxp_reverb_delay, 0, 63, DataFormatUtil.delayTimeFormatter, false),
    DENSITY(R.string.fxp_density, 0, 3, DataFormatUtil.noFormat, false),
    ER_REV_BALANCE(
        R.string.fxp_er_rev_balance,
        1,
        127,
        DataFormatUtil.dryWetAssignFormatter,
        false
    ),
    FEEDBACK_LVL(R.string.fxp_feedback_level, 1, 127, DataFormatUtil.signed127Formatter, false),
    LCH_DELAY_1(R.string.fxp_lch_delay_1, 1, 3550, DataFormatUtil.millisecondFormatter, false),
    LCH_FEEDBACK_LVL(
        R.string.fxp_lch_feedback_level,
        1,
        127,
        DataFormatUtil.signed127Formatter,
        false
    ),
    RCH_DELAY_1(R.string.fxp_rch_delay_1, 1, 3550, DataFormatUtil.millisecondFormatter, false),
    RCH_FEEDBACK_LVL(
        R.string.fxp_rch_feedback_level,
        1,
        127,
        DataFormatUtil.signed127Formatter,
        false
    ),
    HIGH_DAMP(
        R.string.fxp_high_damp,
        1,
        10,
        DataFormatUtil.DataAssignFormatter { "${it / 10f}" },
        false
    ),
    LCH_DELAY_2(R.string.fxp_lch_delay_2, 1, 3550, DataFormatUtil.millisecondFormatter, false),
    RCH_DELAY_2(R.string.fxp_rch_delay_2, 1, 3550, DataFormatUtil.millisecondFormatter, false),
    DELAY2_LEVEL(R.string.fxp_delay_2_lvl, 0, 127, DataFormatUtil.noFormat, false),
    EQ_LOW_FREQ(R.string.fxp_eq_low_freq, 8, 40, DataFormatUtil.eqFreqFormatter, false),
    EQ_LOW_GAIN(R.string.fxp_eq_low_gain, 52, 76, DataFormatUtil.decibleFormatter, false),
    EQ_HIGH_FREQ(R.string.fxp_eq_high_freq, 28, 58, DataFormatUtil.eqFreqFormatter, false),
    EQ_HIGH_GAIN(R.string.fxp_eq_high_gain, 52, 76, DataFormatUtil.decibleFormatter, false),
    WIDTH(R.string.fxp_width, 0, 37, DataFormatUtil.reverbDimensionFormatter, false),
    HEIGHT(R.string.fxp_height, 0, 73, DataFormatUtil.reverbDimensionFormatter, false),
    DEPTH(R.string.fxp_depth, 0, 104, DataFormatUtil.reverbDimensionFormatter, false),
    WALL_VARY(R.string.fxp_wall_vary, 0, 30, DataFormatUtil.noFormat, false),
    LR_DELAY(R.string.fxp_lr_delay, 1, 3550, DataFormatUtil.reverbDimensionFormatter, false),
    RL_DELAY(R.string.fxp_rl_delay, 1, 3550, DataFormatUtil.reverbDimensionFormatter, false),
    INPUT_SELECT(R.string.fxp_input_select, 0, 2, DataFormatUtil.lrSelectFormatter, false),
    CCH_DELAY(R.string.fxp_cch_delay, 1, 7150, DataFormatUtil.millisecondFormatter, false),
    LCH_DELAY_715(R.string.fxp_lch_delay_1, 1, 7150, DataFormatUtil.millisecondFormatter, false),
    RCH_DELAY_715(R.string.fxp_rch_delay_1, 1, 7150, DataFormatUtil.millisecondFormatter, false),
    FEEDBACK_DELAY_1_715(
        R.string.fxp_feedback_delay_1,
        1,
        7150,
        DataFormatUtil.millisecondFormatter,
        false
    ),
    CCH_LEVEL(R.string.fxp_cch_level, 0, 127, DataFormatUtil.noFormat, false),
    ER_TYPE(R.string.fxp_type, 0, 5, DataFormatUtil.erTypeFormatter, false),
    ROOM_SIZE(R.string.fxp_room_size, 0, 44, DataFormatUtil.roomSizeFormatter, false),
    LIVENESS(R.string.fxp_liveness, 0, 10, DataFormatUtil.noFormat, false),
    REV_GATE_TYPE(R.string.fxp_gate_type, 0, 1, DataFormatUtil.gateTypeFormatter, false),
    DELAY_TIME(R.string.fxp_delay_time, 0, 127, DataFormatUtil.karaokeDelayFormatter, false),
    FEEDBACK_DELAY_2_715(
        R.string.fxp_feedback_delay_1,
        1,
        7150,
        DataFormatUtil.millisecondFormatter,
        false
    ),
    FEEDBACK_DELAY_2(
        R.string.fxp_feedback_delay_2,
        1,
        7150,
        DataFormatUtil.millisecondFormatter,
        false
    ),
    LFO_FREQ(R.string.fxp_lfo_freq, 0, 127, DataFormatUtil.lfoFreqFormatter, true),
    LFO_DEPTH(R.string.fxp_lfo_depth, 0, 127, DataFormatUtil.noFormat, false),
    DELAY_OFFSET(R.string.fxp_delay_offset, 0, 127, DataFormatUtil.modDelayOffsetFormatter, false),
    FLANGE_DELAY_OFFSET(
        R.string.fxp_delay_offset,
        0,
        63,
        DataFormatUtil.modDelayOffsetFormatter,
        false
    ),
    INPUT_MODE(R.string.fxp_input_mode, 0, 1, DataFormatUtil.monoSteroFormatter, false),
    AM_DEPTH(R.string.fxp_am_depth, 0, 127, DataFormatUtil.noFormat, false),
    PM_DEPTH(R.string.fxp_pm_depth, 0, 127, DataFormatUtil.noFormat, false),
    LFO_PHASE_DIFF(R.string.fxp_lfo_phase_diff, 4, 124, DataFormatUtil.phaseDegreeFormatter, false),
    LR_DEPTH(R.string.fxp_lr_depth, 0, 127, DataFormatUtil.noFormat, false),
    FR_DEPTH(R.string.fxp_fr_depth, 0, 127, DataFormatUtil.noFormat, false),
    PAN_DIRECTION(R.string.fxp_pan_direction, 0, 5, DataFormatUtil.panDirectionFormatter, false),
    PHASE_SHIFT_OFFSET(R.string.fxp_phase_shift_offset, 0, 127, DataFormatUtil.noFormat, false),
    STAGE(R.string.fxp_stage, 3, 10, DataFormatUtil.phaserStageFormatter, false),
    DRIVE(R.string.fxp_drive, 0, 127, DataFormatUtil.noFormat, true),
    OUTPUT_LEVEL(R.string.fxp_output_lvl, 0, 127, DataFormatUtil.noFormat, false),
    EQ_MID_FREQ(R.string.fxp_eq_mid_freq, 28, 54, DataFormatUtil.eqFreqFormatter, false),
    EQ_MID_GAIN(R.string.fxp_eq_mid_gain, 52, 76, DataFormatUtil.decibleFormatter, false),
    EQ_MID_WIDTH(R.string.fxp_eq_mid_width, 10, 120, DataFormatUtil.eqTenthKHzFormatter, false),
    EDGE(R.string.fxp_edge, 0, 127, DataFormatUtil.noFormat, false),
    CUTOFF_FREQ_OFFSET(R.string.fxp_cutoff_offset, 0, 127, DataFormatUtil.noFormat, true),
    RESONANCE(R.string.fxp_resonance, 10, 120, DataFormatUtil.eqTenthKHzFormatter, false),
    AMP_TYPE(R.string.fxp_amp_type, 0, 3, DataFormatUtil.ampTypeFormatter, false),
    PITCH(R.string.fxp_pitch, 40, 88, DataFormatUtil.pitchFormatter, false),
    FINE(R.string.fxp_fine, 14, 114, DataFormatUtil.fineFormatter, false)

}