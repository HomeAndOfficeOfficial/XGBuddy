package com.example.xgbuddy.util

import com.example.xgbuddy.data.xg.EffectDataAssignTables

object DataFormatUtil {

    val dryWetAssignFormatter = DataAssignFormatter {
        "${(it - 1 / 126) * 100}"
    }

    val signed127Formatter = DataAssignFormatter {
        "${-64 + it}"
    }

    val millisecondFormatter = DataAssignFormatter {
        "${it / 10f}ms"
    }

    val decibleFormatter = DataAssignFormatter {
        "${-12 + it - 52}dB"
    }

    val reverbTimeFormatter = DataAssignFormatter {
        "${EffectDataAssignTables.reverbTime[it]}s"
    }

    val reverbDimensionFormatter = DataAssignFormatter {
        "${EffectDataAssignTables.reverbDimension[it]}m"
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
        "${EffectDataAssignTables.lfoFreq[it]}Hz"
    }

    val modDelayOffsetFormatter = DataAssignFormatter {
        "${EffectDataAssignTables.modDelayOffset[it]}ms"
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
        "${EffectDataAssignTables.roomSize[it]}m"
    }

    val delayTimeFormatter = DataAssignFormatter {
        "${EffectDataAssignTables.delayTime[it]}ms"
    }

    val eqFreqFormatter = DataAssignFormatter {
        "${EffectDataAssignTables.eqFrequency[it]}Hz"
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
        "${EffectDataAssignTables.karaokeDelay[it]}ms"
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
            0 -> "Random"
            in 1..63 -> "L${64 - it}"
            64 -> "C"
            else -> "R${it - 64}"
        }
    }

    val keyAssignFormatter = DataAssignFormatter {
        if (it == 0) {
            "Single"
        } else {
            "Multi"
        }
    }

    fun interface DataAssignFormatter {
        fun format(value: Int): String
    }
}