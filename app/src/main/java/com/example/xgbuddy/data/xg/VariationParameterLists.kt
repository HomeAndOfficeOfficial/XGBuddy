package com.example.xgbuddy.data.xg

object VariationParameterLists {
    val basicReverb: Array<EffectParameter?> = arrayOf(
        EffectParameter.REVERB_TIME,
        EffectParameter.DIFFUSION,
        EffectParameter.INIT_DELAY,
        EffectParameter.HPF_CUTOFF,
        EffectParameter.LPF_CUTOFF,
        null,
        null,
        null,
        null,
        EffectParameter.DRY_WET,
        EffectParameter.REV_DELAY,
        EffectParameter.DENSITY,
        EffectParameter.ER_REV_BALANCE,
        null,
        EffectParameter.FEEDBACK_LVL,
        null
    )

    val dimensionalReverb: Array<EffectParameter?> = arrayOf(
        EffectParameter.REVERB_TIME,
        EffectParameter.DIFFUSION,
        EffectParameter.INIT_DELAY,
        EffectParameter.HPF_CUTOFF,
        EffectParameter.LPF_CUTOFF,
        EffectParameter.WIDTH,
        EffectParameter.HEIGHT,
        EffectParameter.DEPTH,
        EffectParameter.WALL_VARY,
        EffectParameter.DRY_WET,
        EffectParameter.REV_DELAY,
        EffectParameter.DENSITY,
        EffectParameter.ER_REV_BALANCE,
        null,
        EffectParameter.FEEDBACK_LVL,
        null
    )

    val delayLCR: Array<EffectParameter?> = arrayOf(
        EffectParameter.LCH_DELAY_715,
        EffectParameter.RCH_DELAY_715,
        EffectParameter.CCH_DELAY,
        EffectParameter.FEEDBACK_DELAY_1_715,
        EffectParameter.FEEDBACK_LVL,
        EffectParameter.CCH_LEVEL,
        EffectParameter.HIGH_DAMP,
        null,
        null,
        EffectParameter.DRY_WET,
        null,
        null,
        EffectParameter.EQ_LOW_FREQ,
        EffectParameter.EQ_LOW_GAIN,
        EffectParameter.EQ_HIGH_FREQ,
        EffectParameter.EQ_HIGH_GAIN
    )

    val delayLR: Array<EffectParameter?> = arrayOf(
        EffectParameter.LCH_DELAY_715,
        EffectParameter.RCH_DELAY_715,
        EffectParameter.FEEDBACK_DELAY_1_715,
        EffectParameter.FEEDBACK_DELAY_2_715,
        EffectParameter.FEEDBACK_LVL,
        EffectParameter.HIGH_DAMP,
        null,
        null,
        null,
        EffectParameter.DRY_WET,
        null,
        null,
        EffectParameter.EQ_LOW_FREQ,
        EffectParameter.EQ_LOW_GAIN,
        EffectParameter.EQ_HIGH_FREQ,
        EffectParameter.EQ_HIGH_GAIN
    )

    val echo: Array<EffectParameter?> = arrayOf(
        EffectParameter.LCH_DELAY_1,
        EffectParameter.LCH_FEEDBACK_LVL,
        EffectParameter.RCH_DELAY_1,
        EffectParameter.RCH_FEEDBACK_LVL,
        EffectParameter.HIGH_DAMP,
        EffectParameter.LCH_DELAY_2,
        EffectParameter.RCH_DELAY_2,
        EffectParameter.DELAY2_LEVEL,
        null,
        EffectParameter.DRY_WET,
        null,
        null,
        EffectParameter.EQ_LOW_FREQ,
        EffectParameter.EQ_LOW_GAIN,
        EffectParameter.EQ_HIGH_FREQ,
        EffectParameter.EQ_HIGH_GAIN
    )

    val xDelay: Array<EffectParameter?> = arrayOf(
        EffectParameter.LR_DELAY,
        EffectParameter.RL_DELAY,
        EffectParameter.FEEDBACK_LVL,
        EffectParameter.INPUT_SELECT,
        EffectParameter.HIGH_DAMP,
        null,
        null,
        null,
        null,
        EffectParameter.DRY_WET,
        null,
        null,
        EffectParameter.EQ_LOW_FREQ,
        EffectParameter.EQ_LOW_GAIN,
        EffectParameter.EQ_HIGH_FREQ,
        EffectParameter.EQ_HIGH_GAIN
    )

    val earlyRef: Array<EffectParameter?> = arrayOf(
        EffectParameter.ER_TYPE,
        EffectParameter.ROOM_SIZE,
        EffectParameter.DIFFUSION,
        EffectParameter.INIT_DELAY,
        EffectParameter.FEEDBACK_LVL,
        EffectParameter.HPF_CUTOFF,
        EffectParameter.LPF_CUTOFF,
        null,
        null,
        EffectParameter.DRY_WET,
        EffectParameter.LIVENESS,
        EffectParameter.DENSITY,
        EffectParameter.HIGH_DAMP,
        null,
        null,
        null
    )

    val gateReverb: Array<EffectParameter?> = arrayOf(
        EffectParameter.REV_GATE_TYPE,
        EffectParameter.ROOM_SIZE,
        EffectParameter.DIFFUSION,
        EffectParameter.INIT_DELAY,
        EffectParameter.FEEDBACK_LVL,
        EffectParameter.HPF_CUTOFF,
        EffectParameter.LPF_CUTOFF,
        null,
        null,
        EffectParameter.DRY_WET,
        EffectParameter.LIVENESS,
        EffectParameter.DENSITY,
        EffectParameter.HIGH_DAMP,
        null,
        null,
        null
    )

    val karaoke: Array<EffectParameter?> = arrayOf(
        EffectParameter.DELAY_TIME,
        EffectParameter.FEEDBACK_LVL,
        EffectParameter.HPF_CUTOFF,
        EffectParameter.LPF_CUTOFF,
        null,
        null,
        null,
        null,
        null,
        EffectParameter.DRY_WET,
        null,
        null,
        null,
        null,
        null,
        null
    )

    val rotarySpk: Array<EffectParameter?> = arrayOf(
        EffectParameter.LFO_FREQ,
        EffectParameter.LFO_DEPTH,
        null,
        null,
        null,
        EffectParameter.EQ_LOW_FREQ,
        EffectParameter.EQ_LOW_GAIN,
        EffectParameter.EQ_HIGH_FREQ,
        EffectParameter.EQ_HIGH_GAIN,
        EffectParameter.DRY_WET,
        null,
        null,
        null,
        null,
        null,
        null
    )

    val chorus: Array<EffectParameter?> = arrayOf(
        EffectParameter.LFO_FREQ,
        EffectParameter.LFO_DEPTH,
        EffectParameter.FEEDBACK_LVL,
        EffectParameter.DELAY_OFFSET,
        null,
        EffectParameter.EQ_LOW_FREQ,
        EffectParameter.EQ_LOW_GAIN,
        EffectParameter.EQ_HIGH_FREQ,
        EffectParameter.EQ_HIGH_GAIN,
        EffectParameter.DRY_WET,
        null,
        null,
        null,
        null,
        EffectParameter.INPUT_MODE,
        null
    )

    val tremolo: Array<EffectParameter?> = arrayOf(
        EffectParameter.LFO_FREQ,
        EffectParameter.AM_DEPTH,
        EffectParameter.PM_DEPTH,
        null,
        null,
        EffectParameter.EQ_LOW_FREQ,
        EffectParameter.EQ_LOW_GAIN,
        EffectParameter.EQ_HIGH_FREQ,
        EffectParameter.EQ_HIGH_GAIN,
        null,
        null,
        null,
        null,
        EffectParameter.LFO_PHASE_DIFF,
        EffectParameter.INPUT_MODE,
        null
    )

    val flanger: Array<EffectParameter?> = arrayOf(
        EffectParameter.LFO_FREQ,
        EffectParameter.LFO_DEPTH,
        EffectParameter.FEEDBACK_LVL,
        EffectParameter.FLANGE_DELAY_OFFSET,
        null,
        EffectParameter.EQ_LOW_FREQ,
        EffectParameter.EQ_LOW_GAIN,
        EffectParameter.EQ_HIGH_FREQ,
        EffectParameter.EQ_HIGH_GAIN,
        EffectParameter.DRY_WET,
        null,
        null,
        null,
        EffectParameter.LFO_PHASE_DIFF,
        null,
        null
    )

    val autoPan: Array<EffectParameter?> = arrayOf(
        EffectParameter.LFO_FREQ,
        EffectParameter.LR_DEPTH,
        EffectParameter.FR_DEPTH,
        EffectParameter.PAN_DIRECTION,
        null,
        EffectParameter.EQ_LOW_FREQ,
        EffectParameter.EQ_LOW_GAIN,
        EffectParameter.EQ_HIGH_FREQ,
        EffectParameter.EQ_HIGH_GAIN,
        null,
        null,
        null,
        null,
        null,
        null,
        null
    )

    val symphonic: Array<EffectParameter?> = arrayOf(
        EffectParameter.LFO_FREQ,
        EffectParameter.LFO_DEPTH,
        EffectParameter.DELAY_OFFSET,
        null,
        null,
        EffectParameter.EQ_LOW_FREQ,
        EffectParameter.EQ_LOW_GAIN,
        EffectParameter.EQ_HIGH_FREQ,
        EffectParameter.EQ_HIGH_GAIN,
        EffectParameter.DRY_WET,
        null,
        null,
        null,
        null,
        null,
        null
    )

    val phaser: Array<EffectParameter?> = arrayOf(
        EffectParameter.LFO_FREQ,
        EffectParameter.LFO_DEPTH,
        EffectParameter.PHASE_SHIFT_OFFSET,
        EffectParameter.FEEDBACK_LVL,
        EffectParameter.EQ_LOW_FREQ,
        EffectParameter.EQ_LOW_GAIN,
        EffectParameter.EQ_HIGH_FREQ,
        EffectParameter.EQ_HIGH_GAIN,
        EffectParameter.DRY_WET,
        EffectParameter.STAGE,
        EffectParameter.DIFFUSION, // TODO: Verify is this diffusion or mono/stereo?
        EffectParameter.LFO_PHASE_DIFF,
        null,
        null,
        null
    )

    val distortion: Array<EffectParameter?> = arrayOf(
        EffectParameter.DRIVE,
        EffectParameter.EQ_LOW_FREQ,
        EffectParameter.EQ_LOW_GAIN,
        EffectParameter.LPF_CUTOFF,
        EffectParameter.OUTPUT_LEVEL,
        null,
        EffectParameter.EQ_MID_FREQ,
        EffectParameter.EQ_MID_GAIN,
        EffectParameter.EQ_MID_WIDTH,
        EffectParameter.DRY_WET,
        EffectParameter.EDGE, null,
        null,
        null,
        null,
        null
    )

    val autoWah: Array<EffectParameter?> = arrayOf(
        EffectParameter.LFO_FREQ,
        EffectParameter.LFO_DEPTH,
        EffectParameter.CUTOFF_FREQ_OFFSET,
        EffectParameter.RESONANCE,
        null,
        EffectParameter.EQ_LOW_FREQ,
        EffectParameter.EQ_LOW_GAIN,
        EffectParameter.EQ_HIGH_FREQ,
        EffectParameter.EQ_HIGH_GAIN,
        EffectParameter.DRY_WET,
        null,
        null,
        null,
        null,
        null,
        null
    )

    val ampSim: Array<EffectParameter?> = arrayOf(
        EffectParameter.DRIVE,
        EffectParameter.AMP_TYPE,
        EffectParameter.LPF_CUTOFF,
        EffectParameter.OUTPUT_LEVEL,
        null,
        null,
        null,
        null,
        null,
        EffectParameter.DRY_WET,
        EffectParameter.EDGE,
        null,
        null,
        null,
        null,
        null
    )

    val pitchChange: Array<EffectParameter?> = arrayOf(
        EffectParameter.PITCH,
        EffectParameter.INIT_DELAY,
        EffectParameter.FINE,
        null,
        null,
        EffectParameter.EQ_LOW_FREQ,
        EffectParameter.EQ_LOW_GAIN,
        EffectParameter.EQ_HIGH_FREQ,
        EffectParameter.EQ_HIGH_GAIN,
        EffectParameter.DRY_WET,
        null,
        null,
        null,
        null,
        null,
        null
    )

    val threeBandEq: Array<EffectParameter?> = arrayOf(
        EffectParameter.EQ_LOW_GAIN,
        EffectParameter.EQ_MID_FREQ,
        EffectParameter.EQ_MID_WIDTH,
        EffectParameter.EQ_HIGH_GAIN,
        EffectParameter.EQ_LOW_FREQ,
        EffectParameter.EQ_HIGH_FREQ,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null
    )

    val twoBandEq: Array<EffectParameter?> = arrayOf(
        EffectParameter.EQ_LOW_FREQ,
        EffectParameter.EQ_LOW_GAIN,
        EffectParameter.EQ_HIGH_FREQ,
        EffectParameter.EQ_HIGH_GAIN,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null
    )
}