package com.yamahaw.xgbuddy.data.xg.effect

import com.yamahaw.xgbuddy.R
import java.util.*

enum class VariationType(
    val nameRes: Int,
    val msb: Byte,
    val lsb: Byte,
    val parameterList: EnumMap<EffectParameterData, EffectParameter>?,
    val parameterDefaults: IntArray?
) {
    NO_EFFECT(R.string.vari_no_effect, 0, 0, null, null),
    HALL1(R.string.vari_hall_1, 0x01, 0,
        VariationParameterLists.basicReverbVari,
        EffectParameterDefaults.hall1Defaults
    ),
    HALL2(R.string.vari_hall_2, 0x01, 0x01,
        VariationParameterLists.basicReverbVari,
        EffectParameterDefaults.hall2Defaults
    ),
    ROOM1(R.string.vari_room_1, 0x02, 0,
        VariationParameterLists.basicReverbVari,
        EffectParameterDefaults.room1Defaults
    ),
    ROOM2(R.string.vari_room_2, 0x02, 0x01,
        VariationParameterLists.basicReverbVari,
        EffectParameterDefaults.room2Defaults
    ),
    ROOM3(R.string.vari_room_3, 0x02, 0x02,
        VariationParameterLists.basicReverbVari,
        EffectParameterDefaults.room3Defaults
    ),
    STAGE1(R.string.vari_stage_1, 0x03, 0,
        VariationParameterLists.basicReverbVari,
        EffectParameterDefaults.stage1Defaults
    ),
    STAGE2(R.string.vari_stage_2, 0x03, 0x01,
        VariationParameterLists.basicReverbVari,
        EffectParameterDefaults.stage2Defaults
    ),
    PLATE(R.string.vari_plate, 0x04, 0,
        VariationParameterLists.basicReverbVari,
        EffectParameterDefaults.plateDefaults
    ),
    DELAY_LCR(R.string.vari_delay_lcr, 0x05, 0,
        VariationParameterLists.delayLCR,
        EffectParameterDefaults.delayLCRDefaults
    ),
    DELAY_LR(R.string.vari_delay_lr, 0x06, 0,
        VariationParameterLists.delayLR,
        EffectParameterDefaults.delayLRDefaults
    ),
    ECHO(R.string.vari_echo, 0x07, 0,
        VariationParameterLists.echo,
        EffectParameterDefaults.echoDefaults
    ),
    X_DELAY(R.string.vari_x_delay, 0x08, 0,
        VariationParameterLists.xDelay,
        EffectParameterDefaults.xDelayDefaults
    ),
    EARLY_REF1(R.string.vari_early_ref_1, 0x09, 0,
        VariationParameterLists.earlyRef,
        EffectParameterDefaults.earlyRef1Defaults
    ),
    EARLY_REF2(R.string.vari_early_ref_2, 0x09, 0x01,
        VariationParameterLists.earlyRef,
        EffectParameterDefaults.earlyRef2Defaults
    ),
    GATE_REVERB(R.string.vari_gate_reverb, 0x0A, 0,
        VariationParameterLists.gateReverb,
        EffectParameterDefaults.gateReverbDefaults
    ),
    REVERSE_GATE(R.string.vari_rev_gate, 0x0B, 0,
        VariationParameterLists.gateReverb,
        EffectParameterDefaults.revGateDefaults
    ),
    KARAOKE1(R.string.vari_karaoke_1, 0x14, 0,
        VariationParameterLists.karaoke,
        EffectParameterDefaults.karaoke1Defaults
    ),
    KARAOKE2(R.string.vari_karaoke_2, 0x14, 0x01,
        VariationParameterLists.karaoke,
        EffectParameterDefaults.karaoke2Defaults
    ),
    KARAOKE3(R.string.vari_karaoke_3, 0x14, 0x02,
        VariationParameterLists.karaoke,
        EffectParameterDefaults.karaoke3Defaults
    ),
    CHORUS1(R.string.vari_chorus_1, 0x41, 0,
        VariationParameterLists.chorusVari,
        EffectParameterDefaults.chorus1Defaults
    ),
    CHORUS2(R.string.vari_chorus_2, 0x41, 0x01,
        VariationParameterLists.chorusVari,
        EffectParameterDefaults.chorus2Defaults
    ),
    CHORUS3(R.string.vari_chorus_3, 0x41, 0x02,
        VariationParameterLists.chorusVari,
        EffectParameterDefaults.chorus3Defaults
    ),
    CHORUS4(R.string.vari_chorus_4, 0x41, 0x08,
        VariationParameterLists.chorusVari,
        EffectParameterDefaults.chorus4Defaults
    ),
    CELESTE1(R.string.vari_celeste_1, 0x42, 0,
        VariationParameterLists.chorusVari,
        EffectParameterDefaults.celeste1Defaults
    ),
    CELESTE2(R.string.vari_celeste_2, 0x42, 0x01,
        VariationParameterLists.chorusVari,
        EffectParameterDefaults.celeste2Defaults
    ),
    CELESTE3(R.string.vari_celeste_3, 0x42, 0x02,
        VariationParameterLists.chorusVari,
        EffectParameterDefaults.celeste3Defaults
    ),
    CELESTE4(R.string.vari_celeste_4, 0x42, 0x08,
        VariationParameterLists.chorusVari,
        EffectParameterDefaults.celeste4Defaults
    ),
    FLANGER1(R.string.vari_flanger_1, 0x43, 0,
        VariationParameterLists.flangerVari,
        EffectParameterDefaults.flanger1Defaults
    ),
    FLANGER2(R.string.vari_flanger_2, 0x43, 0x01,
        VariationParameterLists.flangerVari,
        EffectParameterDefaults.flanger2Defaults
    ),
    FLANGER3(R.string.vari_flanger_3, 0x43, 0x08,
        VariationParameterLists.flangerVari,
        EffectParameterDefaults.flanger3Defaults
    ),
    SYMPHONIC(R.string.vari_symphonic, 0x44, 0,
        VariationParameterLists.symphonic,
        EffectParameterDefaults.symphonicDefaults
    ),
    ROTARY_SPEAKER(R.string.vari_rot_speaker, 0x45, 0,
        VariationParameterLists.rotarySpk,
        EffectParameterDefaults.rotarySpkDefaults
    ),
    TREMOLO(R.string.vari_tremolo, 0x46, 0,
        VariationParameterLists.tremolo,
        EffectParameterDefaults.tremoloDefaults
    ),
    AUTO_PAN(R.string.vari_auto_pan, 0x47, 0,
        VariationParameterLists.autoPan,
        EffectParameterDefaults.autoPanDefaults
    ),
    PHASER1(R.string.vari_phaser_1, 0x48, 0,
        VariationParameterLists.phaser,
        EffectParameterDefaults.phaser1Defaults
    ),
    PHASER2(R.string.vari_phaser_2, 0x48, 0x08,
        VariationParameterLists.phaser,
        EffectParameterDefaults.phaser2Defaults
    ),
    DISTORTION(R.string.vari_distortion, 0x49, 0,
        VariationParameterLists.distortion,
        EffectParameterDefaults.distortionDefaults
    ),
    OVERDRIVE(R.string.vari_overdrive, 0x4A, 0,
        VariationParameterLists.distortion,
        EffectParameterDefaults.overdriveDefaults
    ),
    AMP_SIMULATOR(R.string.vari_amp_sim, 0x4B, 0,
        VariationParameterLists.ampSim,
        EffectParameterDefaults.ampSimDefaults
    ),
    THREE_EQ(R.string.vari_3_band_eq, 0x4C, 0,
        VariationParameterLists.threeBandEq,
        EffectParameterDefaults.threeBandEQDefaults
    ),
    TWO_EQ(R.string.vari_2_band_eq, 0x4D, 0,
        VariationParameterLists.twoBandEq,
        EffectParameterDefaults.twoBandEQDefaults
    ),
    AUTO_WAH(R.string.vari_auto_wah, 0x4E, 0,
        VariationParameterLists.autoWah,
        EffectParameterDefaults.autoWahDefaults
    ),
    PITCH_CHANGE(R.string.vari_pitch_change, 0x50, 0,
        VariationParameterLists.pitchChange,
        EffectParameterDefaults.pitchChangeDefaults
    ),
    THRU(R.string.vari_thru, 0x40, 0, null, null);
}
