package com.example.xgbuddy.data

import com.example.xgbuddy.R

enum class EffectParameter(
    val nameRes: Int,
    val min: Int,
    val max: Int,
    val dataAssignFormatter: DataAssignFormatter?,
    val isAssignable: Boolean
) {
    DRY_WET(R.string.fxp_dry_wet, 1, 127, DataAssignFormatter { "" }, true),
    REVERB_TIME(R.string.fxp_reverb_time, 0, 69, DataAssignFormatter { "" }, false),
    DIFFUSION(R.string.fxp_diffusion, 0, 10, null, false),
    INIT_DELAY(R.string.fxp_init_delay, 0, 63, DataAssignFormatter { "" }, false),
    HPF_CUTOFF(R.string.fxp_hpf_cutoff, 0, 52, DataAssignFormatter { "" }, false),
    LPF_CUTOFF(R.string.fxp_lpf_cutoff, 34, 60, DataAssignFormatter { "" }, false),
    REV_DELAY(R.string.fxp_reverb_delay, 0, 63, DataAssignFormatter { "" }, false),
    DENSITY(R.string.fxp_density, 0, 3, null, false),
    ER_REV_BALANCE(R.string.fxp_er_rev_balance, 1, 127, DataAssignFormatter { "" }, false),
    FEEDBACK_LVL(R.string.fxp_feedback_level, 1, 127, DataAssignFormatter { "" }, false),
    LCH_DELAY_1(R.string.fxp_lch_delay_1, 1, 3550, DataAssignFormatter { "" }, false),
    LCH_FEEDBACK_LVL(R.string.fxp_lch_feedback_level, 1, 127, DataAssignFormatter { "" }, false),
    RCH_DELAY_1(R.string.fxp_rch_delay_1, 1, 3550, DataAssignFormatter { "" }, false),
    RCH_FEEDBACK_LVL(R.string.fxp_rch_feedback_level, 1, 127, DataAssignFormatter { "" }, false),
    HIGH_DAMP(R.string.fxp_high_damp, 1, 10, DataAssignFormatter { "" }, false),
    LCH_DELAY_2(R.string.fxp_lch_delay_2, 1, 3550, DataAssignFormatter { "" }, false),
    RCH_DELAY_2(R.string.fxp_rch_delay_2, 1, 3550, DataAssignFormatter { "" }, false),
    DELAY2_LEVEL(R.string.fxp_delay_2_lvl, 0, 127, null, false),
    EQ_LOW_FREQ(R.string.fxp_eq_low_freq, 8, 40, DataAssignFormatter { "" }, false),
    EQ_LOW_GAIN(R.string.fxp_eq_low_gain, 52, 76, DataAssignFormatter { "" }, false),
    EQ_HIGH_FREQ(R.string.fxp_eq_high_freq, 28, 58, DataAssignFormatter { "" }, false),
    EQ_HIGH_GAIN(R.string.fxp_eq_high_gain, 52, 76, DataAssignFormatter { "" }, false),
    WIDTH(R.string.fxp_width, 0, 37, DataAssignFormatter { "" }, false),
    HEIGHT(R.string.fxp_height, 0, 73, DataAssignFormatter { "" }, false),
    DEPTH(R.string.fxp_depth, 0, 104, DataAssignFormatter { "" }, false),
    WALL_VARY(R.string.fxp_wall_vary, 0, 30, null, false),
    LR_DELAY(R.string.fxp_lr_delay, 1, 3550, DataAssignFormatter { "" }, false),
    RL_DELAY(R.string.fxp_rl_delay, 1, 3550, DataAssignFormatter { "" }, false),
    INPUT_SELECT(R.string.fxp_input_select, 0, 2, DataAssignFormatter { "" }, false),
    CCH_DELAY(R.string.fxp_cch_delay, 1, 7150, DataAssignFormatter { "" }, false),
    LCH_DELAY_715(R.string.fxp_lch_delay_1, 1, 7150, DataAssignFormatter { "" }, false),
    RCH_DELAY_715(R.string.fxp_rch_delay_1, 1, 7150, DataAssignFormatter { "" }, false),
    FEEDBACK_DELAY_715(R.string.fxp_feedback_delay_1, 1, 7150, DataAssignFormatter { "" }, false),
    CCH_LEVEL(R.string.fxp_cch_level, 0, 127, null, false),
    ER_TYPE(R.string.fxp_type, 0, 5, DataAssignFormatter { "" }, false),
    ROOM_SIZE(R.string.fxp_room_size, 0, 44, DataAssignFormatter { "" }, false),
    LIVENESS(R.string.fxp_liveness, 0, 10, null, false),
    FEEDBACK_DELAY_1_715(R.string.fxp_feedback_delay_1, 1, 7150, DataAssignFormatter { "" }, false),
    FEEDBACK_DELAY_2(R.string.fxp_feedback_delay_2, 1, 7150, DataAssignFormatter { "" }, false),
    LFO_FREQ(R.string.fxp_lfo_freq, 0, 127, DataAssignFormatter { "" }, true),
    LFO_DEPTH(R.string.fxp_lfo_depth, 0, 127, null, false),
    DELAY_OFFSET(R.string.fxp_delay_offset, 0, 127, DataAssignFormatter { "" }, false),
    INPUT_MODE(R.string.fxp_input_mode, 0, 1, DataAssignFormatter { "" }, false),
    AM_DEPTH(R.string.fxp_am_depth, 0, 127, null, false),
    PM_DEPTH(R.string.fxp_pm_depth, 0, 127, null, false),
    LFO_PHASE_DIFF(R.string.fxp_lfo_phase_diff, 4, 124, DataAssignFormatter { "" }, false),
    LR_DEPTH(R.string.fxp_lr_depth, 0, 127, null, false),
    FR_DEPTH(R.string.fxp_fr_depth, 0, 127, null, false),
    PAN_DIRECTION(R.string.fxp_pan_direction, 0, 5, DataAssignFormatter { "" }, false),
    PHASE_SHIFT_OFFSET(R.string.fxp_phase_shift_offset, 0, 127, null, false),
    STAGE(R.string.fxp_stage, 3, 10, DataAssignFormatter { "" }, false);

    /**
     * TODO: Since a lot of these formatters will be the same, make an object class that can provide
     *  an instance of the appropriate one. Will probably make sense to move the interface into its
     *  own file too in that case.
     */
    fun interface DataAssignFormatter {
        fun format(value: Int): String
    }
}