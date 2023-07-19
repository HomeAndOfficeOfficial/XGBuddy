package com.yamahaw.xgbuddy.data.qs300

import androidx.annotation.Keep
import com.yamahaw.xgbuddy.data.buddy.MidiData

@Keep
data class QS300Element(val elementNumber: Int) : MidiData() {
    var waveHi: Byte = QS300ElementParameter.WAVE_HI.default
    var waveLo: Byte = QS300ElementParameter.WAVE_LO.default
    var noteLo: Byte = QS300ElementParameter.NOTE_LO.default
    var noteHi: Byte = QS300ElementParameter.NOTE_HI.default
    var velLo: Byte = QS300ElementParameter.VEL_LO.default
    var velHi: Byte = QS300ElementParameter.VEL_HI.default
    var filterEGVelCurve: Byte = QS300ElementParameter.FILTER_EG_VEL_CURVE.default
    var lfoWave: Byte = QS300ElementParameter.LFO_WAVE.default
    var lfoPhaseInit: Byte = QS300ElementParameter.LFO_PHASE_INIT.default
    var lfoSpeed: Byte = QS300ElementParameter.LFO_SPEED.default
    var lfoDelay: Byte = QS300ElementParameter.LFO_DELAY.default
    var lfoFadeTime: Byte = QS300ElementParameter.LFO_FADE_TIME.default
    var lfoPmdDepth: Byte = QS300ElementParameter.LFO_PMD_DEPTH.default
    var lfoCmdDepth: Byte = QS300ElementParameter.LFO_CMD_DEPTH.default
    var lfoAmdDepth: Byte = QS300ElementParameter.LFO_AMD_DEPTH.default
    var noteShift: Byte = QS300ElementParameter.NOTE_SHIFT.default
    var detune: Byte = QS300ElementParameter.DETUNE.default
    var pitchScaling: Byte = QS300ElementParameter.PITCH_SCALING.default
    var pitchScalingCenter: Byte = QS300ElementParameter.PITCH_SCALING_CENTER.default
    var pitchEgDepth: Byte = QS300ElementParameter.PITCH_EG_DEPTH.default
    var velPegLvlSens: Byte = QS300ElementParameter.VEL_PEG_LVL_SENS.default
    var velPegRateSens: Byte = QS300ElementParameter.VEL_PEG_RATE_SENS.default
    var pegRateScaling: Byte = QS300ElementParameter.PEG_RATE_SCALING.default
    var pegRateScalingCenter: Byte = QS300ElementParameter.PEG_RATE_SCALING_CENTER.default
    var pegRate1: Byte = QS300ElementParameter.PEG_RATE_1.default
    var pegRate2: Byte = QS300ElementParameter.PEG_RATE_2.default
    var pegRate3: Byte = QS300ElementParameter.PEG_RATE_3.default
    var pegRate4: Byte = QS300ElementParameter.PEG_RATE_4.default
    var pegLvl0: Byte = QS300ElementParameter.PEG_LEVEL_0.default
    var pegLvl1: Byte = QS300ElementParameter.PEG_LEVEL_1.default
    var pegLvl2: Byte = QS300ElementParameter.PEG_LEVEL_2.default
    var pegLvl3: Byte = QS300ElementParameter.PEG_LEVEL_3.default
    var pegLvl4: Byte = QS300ElementParameter.PEG_LEVEL_4.default
    var filterRes: Byte = QS300ElementParameter.FILTER_RES.default
    var velSens: Byte = QS300ElementParameter.VEL_SENS.default
    var cutoffFreq: Byte = QS300ElementParameter.CUTOFF_FREQ.default
    var cutoffScalingBreak1: Byte = QS300ElementParameter.CUTOFF_SCALING_BREAK_1.default
    var cutoffScalingBreak2: Byte = QS300ElementParameter.CUTOFF_SCALING_BREAK_2.default
    var cutoffScalingBreak3: Byte = QS300ElementParameter.CUTOFF_SCALING_BREAK_3.default
    var cutoffScalingBreak4: Byte = QS300ElementParameter.CUTOFF_SCALING_BREAK_4.default
    var cutoffScalingOffset1: Byte = QS300ElementParameter.CUTOFF_SCALING_OFFSET_1.default
    var cutoffScalingOffset2: Byte = QS300ElementParameter.CUTOFF_SCALING_OFFSET_2.default
    var cutoffScalingOffset3: Byte = QS300ElementParameter.CUTOFF_SCALING_OFFSET_3.default
    var cutoffScalingOffset4: Byte = QS300ElementParameter.CUTOFF_SCALING_OFFSET_4.default
    var velFegLvlSens: Byte = QS300ElementParameter.VEL_FEG_LVL_SENS.default
    var velFegRateSens: Byte = QS300ElementParameter.VEL_FEG_RATE_SENS.default
    var fegRateScaling: Byte = QS300ElementParameter.FEG_RATE_SCALING.default
    var fegRateScalingCenter: Byte = QS300ElementParameter.FEG_RATE_SCALING_CENTER.default
    var fegRate1: Byte = QS300ElementParameter.FEG_RATE_1.default
    var fegRate2: Byte = QS300ElementParameter.FEG_RATE_2.default
    var fegRate3: Byte = QS300ElementParameter.FEG_RATE_3.default
    var fegRate4: Byte = QS300ElementParameter.FEG_RATE_4.default
    var fegLvl0: Byte = QS300ElementParameter.FEG_LVL_0.default
    var fegLvl1: Byte = QS300ElementParameter.FEG_LVL_1.default
    var fegLvl2: Byte = QS300ElementParameter.FEG_LVL_2.default
    var fegLvl3: Byte = QS300ElementParameter.FEG_LVL_3.default
    var fegLvl4: Byte = QS300ElementParameter.FEG_LVL_4.default
    var elementLvl: Byte = QS300ElementParameter.ELEMENT_LVL.default
    var lvlScalingBreak1: Byte = QS300ElementParameter.LVL_SCALING_BREAK_1.default
    var lvlScalingBreak2: Byte = QS300ElementParameter.LVL_SCALING_BREAK_2.default
    var lvlScalingBreak3: Byte = QS300ElementParameter.LVL_SCALING_BREAK_3.default
    var lvlScalingBreak4: Byte = QS300ElementParameter.LVL_SCALING_BREAK_4.default
    var lvlScalingOffset1: Byte = QS300ElementParameter.LVL_SCALING_OFFSET_1.default
    var lvlScalingOffset2: Byte = QS300ElementParameter.LVL_SCALING_OFFSET_2.default
    var lvlScalingOffset3: Byte = QS300ElementParameter.LVL_SCALING_OFFSET_3.default
    var lvlScalingOffset4: Byte = QS300ElementParameter.LVL_SCALING_OFFSET_4.default
    var velCurve: Byte = QS300ElementParameter.VEL_CURVE.default
    var pan: Byte = QS300ElementParameter.PAN.default
    var aegRateScaling: Byte = QS300ElementParameter.AEG_RATE_SCALING.default
    var aegScalingCenter: Byte = QS300ElementParameter.AEG_SCALING_CENTER.default
    var aegKeyOnDelay: Byte = QS300ElementParameter.AEG_KEY_ON_DELAY.default
    var aegAttackRate: Byte = QS300ElementParameter.AEG_ATTACK_RATE.default
    var aegDecayRate1: Byte = QS300ElementParameter.AEG_DECAY_RATE_1.default
    var aegDecayRate2: Byte = QS300ElementParameter.AEG_DECAY_RATE_2.default
    var aegReleaseRate: Byte = QS300ElementParameter.AEG_RELEASE_RATE.default
    var aegDecayLvl1: Byte = QS300ElementParameter.AEG_DECAY_LVL_1.default
    var aegDecayLvl2: Byte = QS300ElementParameter.AEG_DECAY_LVL_2.default
    var addressOffsetHi: Byte = QS300ElementParameter.ADDRESS_OFFSET_HI.default
    var addressOffsetLo: Byte = QS300ElementParameter.ADDRESS_OFFSET_LOW.default
    var resonanceSens: Byte = QS300ElementParameter.RESONANCE_SENS.default

    fun setWaveValue(waveValue: Int) {
        waveLo = (waveValue and 0x7f).toByte()
        waveHi = ((waveValue shr 7) and 0x7f).toByte()
    }

    override fun toString(): String {
        return "QS300Element(elementNumber=$elementNumber, waveHi=$waveHi, waveLo=$waveLo, noteLo=$noteLo, noteHi=$noteHi, velLo=$velLo, velHi=$velHi, filterEGVelCurve=$filterEGVelCurve, lfoWave=$lfoWave, lfoPhaseInit=$lfoPhaseInit, lfoSpeed=$lfoSpeed, lfoDelay=$lfoDelay, lfoFadeTime=$lfoFadeTime, lfoPmdDepth=$lfoPmdDepth, lfoCmdDepth=$lfoCmdDepth, lfoAmdDepth=$lfoAmdDepth, noteShift=$noteShift, detune=$detune, pitchScaling=$pitchScaling, pitchScalingCenter=$pitchScalingCenter, pitchEgDepth=$pitchEgDepth, velPegLvlSens=$velPegLvlSens, velPegRateSens=$velPegRateSens, pegRateScaling=$pegRateScaling, pegRateScalingCenter=$pegRateScalingCenter, pegRate1=$pegRate1, pegRate2=$pegRate2, pegRate3=$pegRate3, pegRate4=$pegRate4, pegLvl0=$pegLvl0, pegLvl1=$pegLvl1, pegLvl2=$pegLvl2, pegLvl3=$pegLvl3, pegLvl4=$pegLvl4, filterRes=$filterRes, velSens=$velSens, cutoffFreq=$cutoffFreq, cutoffScalingBreak1=$cutoffScalingBreak1, cutoffScalingBreak2=$cutoffScalingBreak2, cutoffScalingBreak3=$cutoffScalingBreak3, cutoffScalingBreak4=$cutoffScalingBreak4, cutoffScalingOffset1=$cutoffScalingOffset1, cutoffScalingOffset2=$cutoffScalingOffset2, cutoffScalingOffset3=$cutoffScalingOffset3, cutoffScalingOffset4=$cutoffScalingOffset4, velFegLvlSens=$velFegLvlSens, velFegRateSens=$velFegRateSens, fegRateScaling=$fegRateScaling, fegRateScalingCenter=$fegRateScalingCenter, fegRate1=$fegRate1, fegRate2=$fegRate2, fegRate3=$fegRate3, fegRate4=$fegRate4, fegLvl0=$fegLvl0, fegLvl1=$fegLvl1, fegLvl2=$fegLvl2, fegLvl3=$fegLvl3, fegLvl4=$fegLvl4, elementLvl=$elementLvl, lvlScalingBreak1=$lvlScalingBreak1, lvlScalingBreak2=$lvlScalingBreak2, lvlScalingBreak3=$lvlScalingBreak3, lvlScalingBreak4=$lvlScalingBreak4, lvlScalingOffset1=$lvlScalingOffset1, lvlScalingOffset2=$lvlScalingOffset2, lvlScalingOffset3=$lvlScalingOffset3, lvlScalingOffset4=$lvlScalingOffset4, velCurve=$velCurve, pan=$pan, aegRateScaling=$aegRateScaling, aegScalingCenter=$aegScalingCenter, aegKeyOnDelay=$aegKeyOnDelay, aegAttackRate=$aegAttackRate, aegDecayRate1=$aegDecayRate1, aegDecayRate2=$aegDecayRate2, aegReleaseRate=$aegReleaseRate, aegDecayLvl1=$aegDecayLvl1, aegDecayLvl2=$aegDecayLvl2, addressOffsetHi=$addressOffsetHi, addressOffsetLo=$addressOffsetLo, resonanceSens=$resonanceSens)"
    }


}