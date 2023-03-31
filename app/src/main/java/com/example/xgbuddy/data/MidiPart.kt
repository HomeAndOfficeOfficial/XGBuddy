package com.example.xgbuddy.data

data class MidiChannel(val ch: Int)

/**
 *
 * There is a
 *
 * - channel: Int
 *
 * XG data and QS300 data are a little different. Everything below applies to QS300.
 * I don't think there is going to be much overlap between the two unfortunately.
 * The following three fields should probably go in a class called QS300Voice
 * - 00 voiceName [32 - 126]
 * - 0b elementSwitch = 3 [EL_1, EL_2, EL_12]
 * - 0c voiceLevel = 100 [0 - 127]
 *
 * The reset of these fields are specific to a single element. A Qs300 voice contains two elements.
 * The addresses listed apply to element 1. For element 2, add 50 to the address (starts at 8D).
 *
 * - 3d waveNumHi [0 - 127]
 * - 3e waveNumLo [0 - 127]
 * - 3f noteLow = 0
 * - 40 noteHi = 127
 * - 41 velocityLow = 0
 * - 42 velocityHi = 127
 * - 43 filterEgVelCurve [0, 1]
 * - 44 lfoWave = LFO_WAVE_SAW [LFO_WAVE_SAW, LFO_WAVE_TRI, LFO_WAVE_SH]
 * - 45 lfoPhaseInitialize [0, 1]
 * - 46 lfoSpeed = 0 [0 - 63]
 * - 47 lfoDelay = 63 [0 - 127]
 * - 48 lfoFadeTime = 63 [0 - 127]
 * - 49 lfoPMDDepth [0 - 63]
 * - 4a lfoCMDDepth [0 - 15]
 * - 4b lfoAMDDepth [0 - 31]
 * - 4c noteShift = 64 [32 - 96]
 * - 4d detune = 50 [14 - 114]
 * - 4e pitchScaling = 0 [0 - 5] see manual for scale percentages
 * - 4f pitchScalingCenterNote = 63 [0 - 127]
 * - 50 pitchEGDepth = 1 [0 - 3]
 * - 51 velPEGLevelSens = 57 [57 - 71]
 * - 52 velPEGRateSens = 57 [57 - 71]
 * - 53 pEGRateScaling = 57 [57 - 71]
 * - 54 pEGRateScalingCenterNote = 63 [0 - 127]
 * - 55 pEGRate1 = 0 [0 - 63]
 * - 56 pEGRate2 = 0 [0 - 63]
 * - 57 pEGRate3 = 0 [0 - 63]
 * - 58 pEGRate4 = 0 [0 - 63]
 * - 57 pEGLevel0 = 63 [0 - 127]
 * - 5a pEGLevel1 = 63 [0 - 127]
 * - 5b pEGLevel2 = 63 [0 - 127]
 * - 5c pEGLevel3 = 63 [0 - 127]
 * - 5d pEGLevel4 = 63 [0 - 127]
 * - 5e filterResonance = 0 [0 - 63]
 * - 5f velSensitivity = 3 [0 - 7]
 * - 60 cutoffFreq = 63 [0 - 127]
 * - 61 cutoffScalingBreak1 = 63 [0 - 127]
 * - 62 cutoffScalingBreak2 = 63 [0 - 127]
 * - 63 cutoffScalingBreak3 = 63 [0 - 127]
 * - 64 cutoffScalingBreak4 = 63 [0 - 127]
 * - 65 cutoffScalingOffset1 = 63 [0 - 127]
 * - 66 cutoffScalingOffset2 = 63 [0 - 127]
 * - 67 cutoffScalingOffset3 = 63 [0 - 127]
 * - 68 cutoffScalingOffset4 = 63 [0 - 127]
 * - 69 velFEGLevelSens = 57 [57 - 71]
 * - 6a velFEGRateSens = 57 [57 - 71]
 * - 6b fEGRateScaling = 57 [57 - 71]
 * - 6c fEGRateScalingCenterNote = 63 [0 - 127]
 * - 6d fEGRate1 = 0 [0 - 63]
 * - 6e fEGRate2 = 0 [0 - 63]
 * - 6f fEGRate3 = 0 [0 - 63]
 * - 70 fEGRate4 = 0 [0 - 63]
 * - 71 fEGLevel0 = 63 [0 - 127]
 * - 72 fEGLevel1 = 63 [0 - 127]
 * - 73 fEGLevel2 = 63 [0 - 127]
 * - 74 fEGLevel3 = 63 [0 - 127]
 * - 75 fEGLevel4 = 63 [0 - 127]
 * - 76 elementLevel = 100 [0 - 127]
 * - 77 levelScalingBreak1 = 63 [0 - 127]
 * - 78 levelScalingBreak2 = 63 [0 - 127]
 * - 79 levelScalingBreak3 = 63 [0 - 127]
 * - 7a levelScalingBreak4 = 63 [0 - 127]
 * - 7b levelScalingOffset1 = 0 [0 - 127]
 * - 7c levelScalingOffset2 = 0 [0 - 127]
 * - 7d levelScalingOffset3 = 0 [0 - 127]
 * - 7e levelScalingOffset4 = 0 [0 - 127]
 * - 7f velCurve = 2 [0 - 6]
 * - 80 pan = 7 [0 - 15] 15 = scaling
 * - 81 aEGRateScaling = 57 [57 - 71]
 * - 82 aEGScalingCenterNote = 63 [0 - 127]
 * - 83 aEGKeyOnDelay = 0 [0 - 15]
 * - 84 aEGAttackRate = 63 [0 - 127]
 * - 85 aEGDecayRate1 = 63 [0 - 127]
 * - 86 aEGDecayRate2 = 63 [0 - 127]
 * - 87 aEGReleaseRate = 63 [0 - 127]
 * - 88 aEGDecayLevel1 = 63 [0 - 127]
 * - 89 aEGDecayLevel2 = 63 [0 - 127]
 * - 8a addressOffsetHi [0 - 127]
 * - 8b addressOffsetLo [0 - 127
 * - 8c resonanceSens = 57 [57 - 71]
 * Unknowns:


]

 These parameters need to be defined in an enum class.
 Since a Qs voice may not need to make use of every parameter it only makes sense to keep a record
 of the ones that it does use.

 So I need an Enum class called QS300ElementParameter.
 It will contain all of the element specific parame
 */
