package com.yamahaw.xgbuddy.data.xg.effect

import java.util.EnumMap

object VariationParameterLists {
    val basicReverb: EnumMap<EffectParameterData, EffectParameter> =
        EnumMap<EffectParameterData, EffectParameter>(EffectParameterData::class.java).apply {
            put(EffectParameterData.REVERB_PARAM_1, EffectParameter.REVERB_TIME)
            put(EffectParameterData.REVERB_PARAM_2, EffectParameter.DIFFUSION)
            put(EffectParameterData.REVERB_PARAM_3, EffectParameter.INIT_DELAY)
            put(EffectParameterData.REVERB_PARAM_4, EffectParameter.HPF_CUTOFF)
            put(EffectParameterData.REVERB_PARAM_5, EffectParameter.LPF_CUTOFF)
            put(EffectParameterData.REVERB_PARAM_10, EffectParameter.DRY_WET)
            put(EffectParameterData.REVERB_PARAM_11, EffectParameter.REV_DELAY)
            put(EffectParameterData.REVERB_PARAM_12, EffectParameter.DENSITY)
            put(EffectParameterData.REVERB_PARAM_13, EffectParameter.ER_REV_BALANCE)
            put(EffectParameterData.REVERB_PARAM_15, EffectParameter.FEEDBACK_LVL)
        }

    val basicReverbVari: EnumMap<EffectParameterData, EffectParameter> =
        EnumMap<EffectParameterData, EffectParameter>(EffectParameterData::class.java).apply {
            put(EffectParameterData.VARIATION_PARAM_1, EffectParameter.REVERB_TIME)
            put(EffectParameterData.VARIATION_PARAM_2, EffectParameter.DIFFUSION)
            put(EffectParameterData.VARIATION_PARAM_3, EffectParameter.INIT_DELAY)
            put(EffectParameterData.VARIATION_PARAM_4, EffectParameter.HPF_CUTOFF)
            put(EffectParameterData.VARIATION_PARAM_5, EffectParameter.LPF_CUTOFF)
            put(EffectParameterData.VARIATION_PARAM_10, EffectParameter.DRY_WET)
            put(EffectParameterData.VARIATION_PARAM_11, EffectParameter.REV_DELAY)
            put(EffectParameterData.VARIATION_PARAM_12, EffectParameter.DENSITY)
            put(EffectParameterData.VARIATION_PARAM_13, EffectParameter.ER_REV_BALANCE)
            put(EffectParameterData.VARIATION_PARAM_15, EffectParameter.FEEDBACK_LVL)
        }

    val dimensionalReverb: EnumMap<EffectParameterData, EffectParameter> =
        EnumMap<EffectParameterData, EffectParameter>(EffectParameterData::class.java).apply {
            put(EffectParameterData.REVERB_PARAM_1, EffectParameter.REVERB_TIME)
            put(EffectParameterData.REVERB_PARAM_2, EffectParameter.DIFFUSION)
            put(EffectParameterData.REVERB_PARAM_3, EffectParameter.INIT_DELAY)
            put(EffectParameterData.REVERB_PARAM_4, EffectParameter.HPF_CUTOFF)
            put(EffectParameterData.REVERB_PARAM_5, EffectParameter.LPF_CUTOFF)
            put(EffectParameterData.REVERB_PARAM_6, EffectParameter.WIDTH)
            put(EffectParameterData.REVERB_PARAM_7, EffectParameter.HEIGHT)
            put(EffectParameterData.REVERB_PARAM_8, EffectParameter.DEPTH)
            put(EffectParameterData.REVERB_PARAM_9, EffectParameter.WALL_VARY)
            put(EffectParameterData.REVERB_PARAM_10, EffectParameter.DRY_WET)
            put(EffectParameterData.REVERB_PARAM_11, EffectParameter.REV_DELAY)
            put(EffectParameterData.REVERB_PARAM_12, EffectParameter.DENSITY)
            put(EffectParameterData.REVERB_PARAM_13, EffectParameter.ER_REV_BALANCE)
            put(EffectParameterData.REVERB_PARAM_15, EffectParameter.FEEDBACK_LVL)
        }

    val delayLCR: EnumMap<EffectParameterData, EffectParameter> =
        EnumMap<EffectParameterData, EffectParameter>(EffectParameterData::class.java).apply {
            put(EffectParameterData.VARIATION_PARAM_1, EffectParameter.LCH_DELAY_715)
            put(EffectParameterData.VARIATION_PARAM_2, EffectParameter.RCH_DELAY_715)
            put(EffectParameterData.VARIATION_PARAM_3, EffectParameter.CCH_DELAY)
            put(EffectParameterData.VARIATION_PARAM_4, EffectParameter.FEEDBACK_DELAY_1_715)
            put(EffectParameterData.VARIATION_PARAM_5, EffectParameter.FEEDBACK_LVL)
            put(EffectParameterData.VARIATION_PARAM_6, EffectParameter.CCH_LEVEL)
            put(EffectParameterData.VARIATION_PARAM_7, EffectParameter.HIGH_DAMP)
            put(EffectParameterData.VARIATION_PARAM_10, EffectParameter.DRY_WET)
            put(EffectParameterData.VARIATION_PARAM_13, EffectParameter.EQ_LOW_FREQ)
            put(EffectParameterData.VARIATION_PARAM_14, EffectParameter.EQ_LOW_GAIN)
            put(EffectParameterData.VARIATION_PARAM_15, EffectParameter.EQ_HIGH_FREQ)
            put(EffectParameterData.VARIATION_PARAM_16, EffectParameter.EQ_HIGH_GAIN)
        }

    val delayLR: EnumMap<EffectParameterData, EffectParameter> =
        EnumMap<EffectParameterData, EffectParameter>(EffectParameterData::class.java).apply {
            put(EffectParameterData.VARIATION_PARAM_1, EffectParameter.LCH_DELAY_715)
            put(EffectParameterData.VARIATION_PARAM_2, EffectParameter.RCH_DELAY_715)
            put(EffectParameterData.VARIATION_PARAM_3, EffectParameter.FEEDBACK_DELAY_1_715)
            put(EffectParameterData.VARIATION_PARAM_4, EffectParameter.FEEDBACK_DELAY_2_715)
            put(EffectParameterData.VARIATION_PARAM_5, EffectParameter.FEEDBACK_LVL)
            put(EffectParameterData.VARIATION_PARAM_6, EffectParameter.HIGH_DAMP)
            put(EffectParameterData.VARIATION_PARAM_10, EffectParameter.DRY_WET)
            put(EffectParameterData.VARIATION_PARAM_13, EffectParameter.EQ_LOW_FREQ)
            put(EffectParameterData.VARIATION_PARAM_14, EffectParameter.EQ_LOW_GAIN)
            put(EffectParameterData.VARIATION_PARAM_15, EffectParameter.EQ_HIGH_FREQ)
            put(EffectParameterData.VARIATION_PARAM_16, EffectParameter.EQ_HIGH_GAIN)
        }

    val echo: EnumMap<EffectParameterData, EffectParameter> =
        EnumMap<EffectParameterData, EffectParameter>(EffectParameterData::class.java).apply {
            put(EffectParameterData.VARIATION_PARAM_1, EffectParameter.LCH_DELAY_1)
            put(EffectParameterData.VARIATION_PARAM_2, EffectParameter.LCH_FEEDBACK_LVL)
            put(EffectParameterData.VARIATION_PARAM_3, EffectParameter.RCH_DELAY_1)
            put(EffectParameterData.VARIATION_PARAM_4, EffectParameter.RCH_FEEDBACK_LVL)
            put(EffectParameterData.VARIATION_PARAM_5, EffectParameter.HIGH_DAMP)
            put(EffectParameterData.VARIATION_PARAM_6, EffectParameter.LCH_DELAY_2)
            put(EffectParameterData.VARIATION_PARAM_7, EffectParameter.RCH_DELAY_2)
            put(EffectParameterData.VARIATION_PARAM_8, EffectParameter.DELAY2_LEVEL)
            put(EffectParameterData.VARIATION_PARAM_10, EffectParameter.DRY_WET)
            put(EffectParameterData.VARIATION_PARAM_13, EffectParameter.EQ_LOW_FREQ)
            put(EffectParameterData.VARIATION_PARAM_14, EffectParameter.EQ_LOW_GAIN)
            put(EffectParameterData.VARIATION_PARAM_15, EffectParameter.EQ_HIGH_FREQ)
            put(EffectParameterData.VARIATION_PARAM_16, EffectParameter.EQ_HIGH_GAIN)
        }

    val xDelay: EnumMap<EffectParameterData, EffectParameter> =
        EnumMap<EffectParameterData, EffectParameter>(EffectParameterData::class.java).apply {
            put(EffectParameterData.VARIATION_PARAM_1, EffectParameter.LR_DELAY)
            put(EffectParameterData.VARIATION_PARAM_2, EffectParameter.RL_DELAY)
            put(EffectParameterData.VARIATION_PARAM_3, EffectParameter.FEEDBACK_LVL)
            put(EffectParameterData.VARIATION_PARAM_4, EffectParameter.INPUT_SELECT)
            put(EffectParameterData.VARIATION_PARAM_5, EffectParameter.HIGH_DAMP)
            put(EffectParameterData.VARIATION_PARAM_10, EffectParameter.DRY_WET)
            put(EffectParameterData.VARIATION_PARAM_13, EffectParameter.EQ_LOW_FREQ)
            put(EffectParameterData.VARIATION_PARAM_14, EffectParameter.EQ_LOW_GAIN)
            put(EffectParameterData.VARIATION_PARAM_15, EffectParameter.EQ_HIGH_FREQ)
            put(EffectParameterData.VARIATION_PARAM_16, EffectParameter.EQ_HIGH_GAIN)
        }

    val earlyRef: EnumMap<EffectParameterData, EffectParameter> =
        EnumMap<EffectParameterData, EffectParameter>(EffectParameterData::class.java).apply {
            put(EffectParameterData.VARIATION_PARAM_1, EffectParameter.ER_TYPE)
            put(EffectParameterData.VARIATION_PARAM_2, EffectParameter.ROOM_SIZE)
            put(EffectParameterData.VARIATION_PARAM_3, EffectParameter.DIFFUSION)
            put(EffectParameterData.VARIATION_PARAM_4, EffectParameter.INIT_DELAY)
            put(EffectParameterData.VARIATION_PARAM_5, EffectParameter.FEEDBACK_LVL)
            put(EffectParameterData.VARIATION_PARAM_6, EffectParameter.HPF_CUTOFF)
            put(EffectParameterData.VARIATION_PARAM_7, EffectParameter.LPF_CUTOFF)
            put(EffectParameterData.VARIATION_PARAM_10, EffectParameter.DRY_WET)
            put(EffectParameterData.VARIATION_PARAM_11, EffectParameter.LIVENESS)
            put(EffectParameterData.VARIATION_PARAM_12, EffectParameter.DENSITY)
            put(EffectParameterData.VARIATION_PARAM_13, EffectParameter.HIGH_DAMP)
        }

    val gateReverb: EnumMap<EffectParameterData, EffectParameter> =
        EnumMap<EffectParameterData, EffectParameter>(EffectParameterData::class.java).apply {
            put(EffectParameterData.VARIATION_PARAM_1, EffectParameter.REV_GATE_TYPE)
            put(EffectParameterData.VARIATION_PARAM_2, EffectParameter.ROOM_SIZE)
            put(EffectParameterData.VARIATION_PARAM_3, EffectParameter.DIFFUSION)
            put(EffectParameterData.VARIATION_PARAM_4, EffectParameter.INIT_DELAY)
            put(EffectParameterData.VARIATION_PARAM_5, EffectParameter.FEEDBACK_LVL)
            put(EffectParameterData.VARIATION_PARAM_6, EffectParameter.HPF_CUTOFF)
            put(EffectParameterData.VARIATION_PARAM_7, EffectParameter.LPF_CUTOFF)
            put(EffectParameterData.VARIATION_PARAM_10, EffectParameter.DRY_WET)
            put(EffectParameterData.VARIATION_PARAM_11, EffectParameter.LIVENESS)
            put(EffectParameterData.VARIATION_PARAM_12, EffectParameter.DENSITY)
            put(EffectParameterData.VARIATION_PARAM_13, EffectParameter.HIGH_DAMP)
        }

    val karaoke: EnumMap<EffectParameterData, EffectParameter> =
        EnumMap<EffectParameterData, EffectParameter>(EffectParameterData::class.java).apply {
            put(EffectParameterData.VARIATION_PARAM_1, EffectParameter.DELAY_TIME)
            put(EffectParameterData.VARIATION_PARAM_2, EffectParameter.FEEDBACK_LVL)
            put(EffectParameterData.VARIATION_PARAM_3, EffectParameter.HPF_CUTOFF)
            put(EffectParameterData.VARIATION_PARAM_4, EffectParameter.LPF_CUTOFF)
            put(EffectParameterData.VARIATION_PARAM_10, EffectParameter.DRY_WET)
        }

    val rotarySpk: EnumMap<EffectParameterData, EffectParameter> =
        EnumMap<EffectParameterData, EffectParameter>(EffectParameterData::class.java).apply {
            put(EffectParameterData.VARIATION_PARAM_1, EffectParameter.LFO_FREQ)
            put(EffectParameterData.VARIATION_PARAM_2, EffectParameter.LFO_DEPTH)
            put(EffectParameterData.VARIATION_PARAM_6, EffectParameter.EQ_LOW_FREQ)
            put(EffectParameterData.VARIATION_PARAM_7, EffectParameter.EQ_LOW_GAIN)
            put(EffectParameterData.VARIATION_PARAM_8, EffectParameter.EQ_HIGH_FREQ)
            put(EffectParameterData.VARIATION_PARAM_9, EffectParameter.EQ_HIGH_GAIN)
            put(EffectParameterData.VARIATION_PARAM_10, EffectParameter.DRY_WET)
        }

    val chorus: EnumMap<EffectParameterData, EffectParameter> =
        EnumMap<EffectParameterData, EffectParameter>(EffectParameterData::class.java).apply {
            put(EffectParameterData.CHORUS_PARAMETER_1, EffectParameter.LFO_FREQ)
            put(EffectParameterData.CHORUS_PARAMETER_2, EffectParameter.LFO_DEPTH)
            put(EffectParameterData.CHORUS_PARAMETER_3, EffectParameter.FEEDBACK_LVL)
            put(EffectParameterData.CHORUS_PARAMETER_4, EffectParameter.DELAY_OFFSET)
            put(EffectParameterData.CHORUS_PARAMETER_6, EffectParameter.EQ_LOW_FREQ)
            put(EffectParameterData.CHORUS_PARAMETER_7, EffectParameter.EQ_LOW_GAIN)
            put(EffectParameterData.CHORUS_PARAMETER_8, EffectParameter.EQ_HIGH_FREQ)
            put(EffectParameterData.CHORUS_PARAMETER_9, EffectParameter.EQ_HIGH_GAIN)
            put(EffectParameterData.CHORUS_PARAMETER_10, EffectParameter.DRY_WET)
            put(EffectParameterData.CHORUS_PARAMETER_14, EffectParameter.INPUT_MODE)
        }

    val chorusVari: EnumMap<EffectParameterData, EffectParameter> =
        EnumMap<EffectParameterData, EffectParameter>(EffectParameterData::class.java).apply {
            put(EffectParameterData.VARIATION_PARAM_1, EffectParameter.LFO_FREQ)
            put(EffectParameterData.VARIATION_PARAM_2, EffectParameter.LFO_DEPTH)
            put(EffectParameterData.VARIATION_PARAM_3, EffectParameter.FEEDBACK_LVL)
            put(EffectParameterData.VARIATION_PARAM_4, EffectParameter.DELAY_OFFSET)
            put(EffectParameterData.VARIATION_PARAM_6, EffectParameter.EQ_LOW_FREQ)
            put(EffectParameterData.VARIATION_PARAM_7, EffectParameter.EQ_LOW_GAIN)
            put(EffectParameterData.VARIATION_PARAM_8, EffectParameter.EQ_HIGH_FREQ)
            put(EffectParameterData.VARIATION_PARAM_9, EffectParameter.EQ_HIGH_GAIN)
            put(EffectParameterData.VARIATION_PARAM_10, EffectParameter.DRY_WET)
            put(EffectParameterData.VARIATION_PARAM_14, EffectParameter.INPUT_MODE)
        }

    val tremolo: EnumMap<EffectParameterData, EffectParameter> =
        EnumMap<EffectParameterData, EffectParameter>(EffectParameterData::class.java).apply {
            put(EffectParameterData.VARIATION_PARAM_1, EffectParameter.LFO_FREQ)
            put(EffectParameterData.VARIATION_PARAM_2, EffectParameter.AM_DEPTH)
            put(EffectParameterData.VARIATION_PARAM_3, EffectParameter.PM_DEPTH)
            put(EffectParameterData.VARIATION_PARAM_6, EffectParameter.EQ_LOW_FREQ)
            put(EffectParameterData.VARIATION_PARAM_7, EffectParameter.EQ_LOW_GAIN)
            put(EffectParameterData.VARIATION_PARAM_8, EffectParameter.EQ_HIGH_FREQ)
            put(EffectParameterData.VARIATION_PARAM_9, EffectParameter.EQ_HIGH_GAIN)
            put(EffectParameterData.VARIATION_PARAM_14, EffectParameter.LFO_PHASE_DIFF)
            put(EffectParameterData.VARIATION_PARAM_15, EffectParameter.INPUT_MODE)
        }
    val flanger: EnumMap<EffectParameterData, EffectParameter> =
        EnumMap<EffectParameterData, EffectParameter>(EffectParameterData::class.java).apply {
            put(EffectParameterData.CHORUS_PARAMETER_1, EffectParameter.LFO_FREQ)
            put(EffectParameterData.CHORUS_PARAMETER_2, EffectParameter.LFO_DEPTH)
            put(EffectParameterData.CHORUS_PARAMETER_3, EffectParameter.FEEDBACK_LVL)
            put(EffectParameterData.CHORUS_PARAMETER_4, EffectParameter.FLANGE_DELAY_OFFSET)
            put(EffectParameterData.CHORUS_PARAMETER_6, EffectParameter.EQ_LOW_FREQ)
            put(EffectParameterData.CHORUS_PARAMETER_7, EffectParameter.EQ_LOW_GAIN)
            put(EffectParameterData.CHORUS_PARAMETER_8, EffectParameter.EQ_HIGH_FREQ)
            put(EffectParameterData.CHORUS_PARAMETER_9, EffectParameter.EQ_HIGH_GAIN)
            put(EffectParameterData.CHORUS_PARAMETER_10, EffectParameter.DRY_WET)
            put(EffectParameterData.CHORUS_PARAMETER_14, EffectParameter.LFO_PHASE_DIFF)
        }

    val flangerVari: EnumMap<EffectParameterData, EffectParameter> =
        EnumMap<EffectParameterData, EffectParameter>(EffectParameterData::class.java).apply {
            put(EffectParameterData.VARIATION_PARAM_1, EffectParameter.LFO_FREQ)
            put(EffectParameterData.VARIATION_PARAM_2, EffectParameter.LFO_DEPTH)
            put(EffectParameterData.VARIATION_PARAM_3, EffectParameter.FEEDBACK_LVL)
            put(EffectParameterData.VARIATION_PARAM_4, EffectParameter.FLANGE_DELAY_OFFSET)
            put(EffectParameterData.VARIATION_PARAM_6, EffectParameter.EQ_LOW_FREQ)
            put(EffectParameterData.VARIATION_PARAM_7, EffectParameter.EQ_LOW_GAIN)
            put(EffectParameterData.VARIATION_PARAM_8, EffectParameter.EQ_HIGH_FREQ)
            put(EffectParameterData.VARIATION_PARAM_9, EffectParameter.EQ_HIGH_GAIN)
            put(EffectParameterData.VARIATION_PARAM_10, EffectParameter.DRY_WET)
            put(EffectParameterData.VARIATION_PARAM_14, EffectParameter.LFO_PHASE_DIFF)
        }

    val autoPan: EnumMap<EffectParameterData, EffectParameter> =
        EnumMap<EffectParameterData, EffectParameter>(EffectParameterData::class.java).apply {
            put(EffectParameterData.VARIATION_PARAM_1, EffectParameter.LFO_FREQ)
            put(EffectParameterData.VARIATION_PARAM_2, EffectParameter.LR_DEPTH)
            put(EffectParameterData.VARIATION_PARAM_3, EffectParameter.FR_DEPTH)
            put(EffectParameterData.VARIATION_PARAM_4, EffectParameter.PAN_DIRECTION)
            put(EffectParameterData.VARIATION_PARAM_6, EffectParameter.EQ_LOW_FREQ)
            put(EffectParameterData.VARIATION_PARAM_7, EffectParameter.EQ_LOW_GAIN)
            put(EffectParameterData.VARIATION_PARAM_8, EffectParameter.EQ_HIGH_FREQ)
            put(EffectParameterData.VARIATION_PARAM_9, EffectParameter.EQ_HIGH_GAIN)
        }
    val symphonic: EnumMap<EffectParameterData, EffectParameter> =
        EnumMap<EffectParameterData, EffectParameter>(EffectParameterData::class.java).apply {
            put(EffectParameterData.VARIATION_PARAM_1, EffectParameter.LFO_FREQ)
            put(EffectParameterData.VARIATION_PARAM_2, EffectParameter.LFO_DEPTH)
            put(EffectParameterData.VARIATION_PARAM_3, EffectParameter.DELAY_OFFSET)
            put(EffectParameterData.VARIATION_PARAM_6, EffectParameter.EQ_LOW_FREQ)
            put(EffectParameterData.VARIATION_PARAM_7, EffectParameter.EQ_LOW_GAIN)
            put(EffectParameterData.VARIATION_PARAM_8, EffectParameter.EQ_HIGH_FREQ)
            put(EffectParameterData.VARIATION_PARAM_9, EffectParameter.EQ_HIGH_GAIN)
            put(EffectParameterData.VARIATION_PARAM_10, EffectParameter.DRY_WET)
        }

    val phaser: EnumMap<EffectParameterData, EffectParameter> =
        EnumMap<EffectParameterData, EffectParameter>(EffectParameterData::class.java).apply {
            put(EffectParameterData.VARIATION_PARAM_1, EffectParameter.LFO_FREQ)
            put(EffectParameterData.VARIATION_PARAM_2, EffectParameter.LFO_DEPTH)
            put(EffectParameterData.VARIATION_PARAM_3, EffectParameter.PHASE_SHIFT_OFFSET)
            put(EffectParameterData.VARIATION_PARAM_4, EffectParameter.FEEDBACK_LVL)
            put(EffectParameterData.VARIATION_PARAM_5, EffectParameter.EQ_LOW_FREQ)
            put(EffectParameterData.VARIATION_PARAM_6, EffectParameter.EQ_LOW_GAIN)
            put(EffectParameterData.VARIATION_PARAM_7, EffectParameter.EQ_HIGH_FREQ)
            put(EffectParameterData.VARIATION_PARAM_8, EffectParameter.EQ_HIGH_GAIN)
            put(EffectParameterData.VARIATION_PARAM_9, EffectParameter.DRY_WET)
            put(EffectParameterData.VARIATION_PARAM_10, EffectParameter.STAGE)
            put(
                EffectParameterData.VARIATION_PARAM_11,
                EffectParameter.DIFFUSION,
            )// TODO: Ver )ify is this diffusion or mono/stereo?
            put(EffectParameterData.VARIATION_PARAM_12, EffectParameter.LFO_PHASE_DIFF)
        }
    val distortion: EnumMap<EffectParameterData, EffectParameter> =
        EnumMap<EffectParameterData, EffectParameter>(EffectParameterData::class.java).apply {
            put(EffectParameterData.VARIATION_PARAM_1, EffectParameter.DRIVE)
            put(EffectParameterData.VARIATION_PARAM_2, EffectParameter.EQ_LOW_FREQ)
            put(EffectParameterData.VARIATION_PARAM_3, EffectParameter.EQ_LOW_GAIN)
            put(EffectParameterData.VARIATION_PARAM_4, EffectParameter.LPF_CUTOFF)
            put(EffectParameterData.VARIATION_PARAM_5, EffectParameter.OUTPUT_LEVEL)
            put(EffectParameterData.VARIATION_PARAM_7, EffectParameter.EQ_MID_FREQ)
            put(EffectParameterData.VARIATION_PARAM_8, EffectParameter.EQ_MID_GAIN)
            put(EffectParameterData.VARIATION_PARAM_9, EffectParameter.EQ_MID_WIDTH)
            put(EffectParameterData.VARIATION_PARAM_10, EffectParameter.DRY_WET)
            put(EffectParameterData.VARIATION_PARAM_11, EffectParameter.EDGE)
        }
    val autoWah: EnumMap<EffectParameterData, EffectParameter> =
        EnumMap<EffectParameterData, EffectParameter>(EffectParameterData::class.java).apply {
            put(EffectParameterData.VARIATION_PARAM_1, EffectParameter.LFO_FREQ)
            put(EffectParameterData.VARIATION_PARAM_2, EffectParameter.LFO_DEPTH)
            put(EffectParameterData.VARIATION_PARAM_3, EffectParameter.CUTOFF_FREQ_OFFSET)
            put(EffectParameterData.VARIATION_PARAM_4, EffectParameter.RESONANCE)
            put(EffectParameterData.VARIATION_PARAM_6, EffectParameter.EQ_LOW_FREQ)
            put(EffectParameterData.VARIATION_PARAM_7, EffectParameter.EQ_LOW_GAIN)
            put(EffectParameterData.VARIATION_PARAM_8, EffectParameter.EQ_HIGH_FREQ)
            put(EffectParameterData.VARIATION_PARAM_9, EffectParameter.EQ_HIGH_GAIN)
            put(EffectParameterData.VARIATION_PARAM_10, EffectParameter.DRY_WET)
        }

    val ampSim: EnumMap<EffectParameterData, EffectParameter> =
        EnumMap<EffectParameterData, EffectParameter>(EffectParameterData::class.java).apply {
            put(EffectParameterData.VARIATION_PARAM_1, EffectParameter.DRIVE)
            put(EffectParameterData.VARIATION_PARAM_2, EffectParameter.AMP_TYPE)
            put(EffectParameterData.VARIATION_PARAM_3, EffectParameter.LPF_CUTOFF)
            put(EffectParameterData.VARIATION_PARAM_4, EffectParameter.OUTPUT_LEVEL)
            put(EffectParameterData.VARIATION_PARAM_10, EffectParameter.DRY_WET)
            put(EffectParameterData.VARIATION_PARAM_11, EffectParameter.EDGE)
        }

    val pitchChange: EnumMap<EffectParameterData, EffectParameter> =
        EnumMap<EffectParameterData, EffectParameter>(EffectParameterData::class.java).apply {
            put(EffectParameterData.VARIATION_PARAM_1, EffectParameter.PITCH)
            put(EffectParameterData.VARIATION_PARAM_2, EffectParameter.INIT_DELAY)
            put(EffectParameterData.VARIATION_PARAM_3, EffectParameter.FINE)
            put(EffectParameterData.VARIATION_PARAM_6, EffectParameter.EQ_LOW_FREQ)
            put(EffectParameterData.VARIATION_PARAM_7, EffectParameter.EQ_LOW_GAIN)
            put(EffectParameterData.VARIATION_PARAM_8, EffectParameter.EQ_HIGH_FREQ)
            put(EffectParameterData.VARIATION_PARAM_9, EffectParameter.EQ_HIGH_GAIN)
            put(EffectParameterData.VARIATION_PARAM_10, EffectParameter.DRY_WET)
        }

    val threeBandEq: EnumMap<EffectParameterData, EffectParameter> =
        EnumMap<EffectParameterData, EffectParameter>(EffectParameterData::class.java).apply {
            put(EffectParameterData.VARIATION_PARAM_1, EffectParameter.EQ_LOW_GAIN)
            put(EffectParameterData.VARIATION_PARAM_2, EffectParameter.EQ_MID_FREQ)
            put(EffectParameterData.VARIATION_PARAM_3, EffectParameter.EQ_MID_GAIN)
            put(EffectParameterData.VARIATION_PARAM_4, EffectParameter.EQ_MID_WIDTH)
            put(EffectParameterData.VARIATION_PARAM_5, EffectParameter.EQ_HIGH_GAIN)
            put(EffectParameterData.VARIATION_PARAM_6, EffectParameter.EQ_LOW_FREQ)
            put(EffectParameterData.VARIATION_PARAM_7, EffectParameter.EQ_HIGH_FREQ)
        }

    val twoBandEq: EnumMap<EffectParameterData, EffectParameter> =
        EnumMap<EffectParameterData, EffectParameter>(EffectParameterData::class.java).apply {
            put(EffectParameterData.VARIATION_PARAM_1, EffectParameter.EQ_LOW_FREQ)
            put(EffectParameterData.VARIATION_PARAM_2, EffectParameter.EQ_LOW_GAIN)
            put(EffectParameterData.VARIATION_PARAM_3, EffectParameter.EQ_HIGH_FREQ)
            put(EffectParameterData.VARIATION_PARAM_4, EffectParameter.EQ_HIGH_GAIN)
        }
}