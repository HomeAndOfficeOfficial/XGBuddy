package com.yamahaw.xgbuddy.util

import com.yamahaw.xgbuddy.data.xg.effect.EffectDataAssignTables

object DataFormatUtil {

    val noFormat = DataAssignFormatter { "$it" }

    val dryWetAssignFormatter = DataAssignFormatter {
        "${(((it - 1) / 126f) * 100).toInt()}%"
    }

    val signed127Formatter = DataAssignFormatter {
        "${-64 + it}"
    }

    val millisecondFormatter = DataAssignFormatter {
        "${it / 10f}"
    }

    val decibelFormatter = DataAssignFormatter {
        "${-12 + it - 52}dB"
    }

    val reverbTimeFormatter = DataAssignFormatter {
        EffectDataAssignTables.reverbTime[it]
    }

    val reverbDimensionFormatter = DataAssignFormatter {
        EffectDataAssignTables.reverbDimension[it]
    }

    val erTypeFormatter = DataAssignFormatter {
        when (it) {
            0 -> "S-H"
            1 -> "L-H"
            2 -> "Rdm"
            3 -> "Rvs"
            4 -> "Plt"
            5 -> "Spr"
            else -> ""
        }
    }

    val lfoFreqFormatter = DataAssignFormatter {
        "${EffectDataAssignTables.lfoFreq[it]}"
    }

    val modDelayOffsetFormatter = DataAssignFormatter {
        "${EffectDataAssignTables.modDelayOffset[it]}"
    }

    val monoSteroFormatter = DataAssignFormatter {
        if (it == 0) {
            "mono"
        } else {
            "stereo"
        }
    }

    val phaseDegreeFormatter = DataAssignFormatter {
        "${-180 + (3 * it) - 12}°"
    }

    val roomSizeFormatter = DataAssignFormatter {
        EffectDataAssignTables.roomSize[it]
    }

    val delayTimeFormatter = DataAssignFormatter {
        EffectDataAssignTables.delayTime[it]
    }

    val eqFreqFormatter = DataAssignFormatter {
        EffectDataAssignTables.eqFrequency[it]
    }

    val lrSelectFormatter = DataAssignFormatter {
        when (it) {
            0 -> "L"
            1 -> "R"
            else -> "L&R"
        }
    }

    val phaserStageFormatter = DataAssignFormatter {
        if (it in 3..5) {
            "phaser1"
        } else {
            "phaser2"
        }
    }

    val panDirectionFormatter = DataAssignFormatter {
        when (it) {
            0 -> "L<->R"
            1 -> "L->R"
            2 -> "L<-R"
            3 -> "Lturn"
            4 -> "Rturn"
            5 -> "L/R"
            else -> ""
        }
    }

    val gateTypeFormatter = DataAssignFormatter {
        if (it == 0)
            "TypeA"
        else
            "TypeB"
    }

    val karaokeDelayFormatter = DataAssignFormatter {
        EffectDataAssignTables.karaokeDelay[it]
    }

    val eqTenthKHzFormatter = DataAssignFormatter {
        "${it / 10f}"
    }

    val ampTypeFormatter = DataAssignFormatter {
        when (it) {
            0 -> "Off"
            1 -> "Stack"
            2 -> "Combo"
            3 -> "Tube"
            else -> ""
        }
    }

    val pitchFormatter = DataAssignFormatter {
        "${-24 + it - 40}"
    }

    val fineFormatter = DataAssignFormatter {
        "${-50 + it - 14}"
    }

    val zeroOffFormatter = DataAssignFormatter {
        if (it == 0) {
            "Off"
        } else {
            "$it"
        }
    }

    val panFormatter = DataAssignFormatter {
        when (it) {
            0 -> "Rnd."
            in 1..63 -> "L${64 - it}"
            64 -> "C"
            else -> "R${it - 64}"
        }
    }

    val keyAssignFormatter = DataAssignFormatter {
        when (it) {
            0 -> "Single"
            1 -> "Multi"
            2 -> "Inst"
            else -> "?"
        }
    }

    val polyMonoFormatter = DataAssignFormatter {
        if (it == 0)
            "Mono"
        else
            "Poly"
    }

    val partModeFormatter = DataAssignFormatter {
        when (it) {
            0 -> "Normal"
            1 -> "GM Drum"
            2 -> "XG Drum 1"
            3 -> "XG Drum 2"
            else -> "?"
        }
    }

    val filterFormatter = DataAssignFormatter {
        "${-9600 + it * 150}"
    }

    val signedPercentFormatter = DataAssignFormatter {
        "${-100 + (it * 1.57).toInt()}"
    }

    val onOffFormatter = DataAssignFormatter {
        if (it == 0)
            "Off"
        else
            "On"
    }

    val filterCurveFormatter = DataAssignFormatter {
        if (it == 0)
            "Linear"
        else
            "Exp."
    }

    val waveShapeFormatter = DataAssignFormatter {
        when (it) {
            0 -> "Saw"
            1 -> "Tri"
            2 -> "S&H"
            else -> "?"
        }
    }

    val signed63Base = DataAssignFormatter {
        "${-63 + it}"
    }

    val pitchScaleFormatter = DataAssignFormatter {
        when (it) {
            0 -> "100%"
            1 -> "50%"
            2 -> "20%"
            3 -> "10%"
            4 -> "5%"
            5 -> "0%"
            else -> "?"
        }
    }

    val pegDepthFormatter = DataAssignFormatter {
        when (it) {
            0 -> "1/2 Oct"
            1 -> "1 Oct"
            2 -> "2 Oct"
            3 -> "4 Oct"
            else -> "?"
        }
    }

    val pan7Formatter = DataAssignFormatter {
        when (it) {
            in 0..6 -> "L${7 - it}"
            7 -> "C"
            in 8..14 -> "R${it - 7}"
            else -> "Scaling"
        }
    }

    val masterTuneFormatter = DataAssignFormatter {
        "${(-1024 + it) / 10f}"
    }

    fun interface DataAssignFormatter {
        fun format(value: Int): String
    }
}