package com.example.xgbuddy.data.gm

import com.example.xgbuddy.R
import com.example.xgbuddy.data.MidiConstants
import com.example.xgbuddy.data.MidiData
import com.example.xgbuddy.data.qs300.QS300Voice
import com.example.xgbuddy.data.xg.DrumVoice
import com.example.xgbuddy.data.xg.SFXNormalVoice
import com.example.xgbuddy.data.xg.XGDrumKit
import com.example.xgbuddy.data.xg.XGNormalVoice
import com.example.xgbuddy.util.DrumKitVoiceUtil

open class MidiPart(val ch: Int) : MidiData() {
    var voiceType: VoiceType = VoiceType.NORMAL
    var voiceNameRes: Int = XGNormalVoice.GRAND_PNO.nameRes
    var elementReserve: Byte = MidiParameter.ELEMENT_RESERVE.default
    var bankMsb: Byte = MidiParameter.BANK_MSB.default
    var bankLsb: Byte = MidiParameter.BANK_LSB.default
    var programNumber: Byte = MidiParameter.PROG_NUMBER.default
    var receiveChannel: Byte = ch.toByte()
    var polyMode: Byte = MidiParameter.POLY_MODE.default
    var keyOnAssign: Byte = MidiParameter.KEY_ON_ASSIGN.default
    var partMode: Byte = MidiParameter.PART_MODE.default
    var noteShift: Byte = MidiParameter.NOTE_SHIFT.default
    var volume: Byte = MidiParameter.VOLUME.default
    var velSensDepth: Byte = MidiParameter.VEL_SENS_DEPTH.default
    var velSenseOffset: Byte = MidiParameter.VEL_SENS_OFFSET.default
    var pan: Byte = MidiParameter.PAN.default
    var noteLimitLo: Byte = MidiParameter.NOTE_LIMIT_LOW.default
    var noteLimitHi: Byte = MidiParameter.NOTE_LIMIT_HIGH.default
    var dryLevel: Byte = MidiParameter.DRY_LEVEL.default
    var chorusSend: Byte = MidiParameter.CHORUS_SEND.default
    var reverbSend: Byte = MidiParameter.REVERB_SEND.default
    var variationSend: Byte = MidiParameter.VARI_SEND.default
    var vibratoRate: Byte = MidiParameter.VIBRATO_RATE.default
    var vibratoDepth: Byte = MidiParameter.VIBRATO_DEPTH.default
    var vibratoDelay: Byte = MidiParameter.VIBRATO_DELAY.default
    var cutoffFreq: Byte = MidiParameter.CUTOFF_FREQ.default
    var resonance: Byte = MidiParameter.RESONANCE.default
    var egAttackTime: Byte = MidiParameter.EG_ATTACK_TIME.default
    var egDecayTime: Byte = MidiParameter.EG_DECAY_TIME.default
    var egReleaseTime: Byte = MidiParameter.EG_RELEASE_TIME.default
    var mwPitchControl: Byte = MidiParameter.MW_PITCH_CTRL.default
    var mwAmplControl: Byte = MidiParameter.MW_AMP_CTRL.default
    var mwFilterControl: Byte = MidiParameter.MW_FILTER_CTRL.default
    var mwLfoPmodDepth: Byte = MidiParameter.MW_LFO_PMOD_DEPTH.default
    var mwLfoFmodDepth: Byte = MidiParameter.MW_LFO_FMOD_DEPTH.default
    var mwLfoAmodDepth: Byte = MidiParameter.MW_LFO_AMOD_DEPTH.default
    var bendPitchContrl: Byte = MidiParameter.BEND_PITCH_CTRL.default
    var bendFilterContrl: Byte = MidiParameter.BEND_FILTER_CTRL.default
    var bendAmplContrl: Byte = MidiParameter.BEND_AMP_CTRL.default
    var bendLfoPmodDepth: Byte = MidiParameter.BEND_LFO_PMOD_DEPTH.default
    var bendLfoFmodDepth: Byte = MidiParameter.BEND_LFO_FMOD_DEPTH.default
    var bendLfoAmodDepth: Byte = MidiParameter.BEND_LFO_AMOD_DEPTH.default
    var rcvPitchBend: Byte = MidiParameter.RCV_PITCH_BEND.default
    var rcvChAfterTouch: Byte = MidiParameter.RCV_CH_AFTER_TOUCH.default
    var rcvProgramChange: Byte = MidiParameter.RCV_PROGRAM_CHANGE.default
    var rcvControlChange: Byte = MidiParameter.RCV_CONTROL_CHANGE.default
    var rcvPolyAfterTouch: Byte = MidiParameter.RCV_POLY_AFTER_TOUCH.default
    var rcvNoteMessage: Byte = MidiParameter.RCV_NOTE_MESSAGE.default
    var rcvRpn: Byte = MidiParameter.RCV_RPN.default
    var rcvNrpn: Byte = MidiParameter.RCV_NRPN.default
    var rcvMod: Byte = MidiParameter.RCV_MOD.default
    var rcvVolume: Byte = MidiParameter.RCV_VOLUME.default
    var rcvPan: Byte = MidiParameter.RCV_PAN.default
    var rcvExpression: Byte = MidiParameter.RCV_EXPRESSION.default
    var rcvHold: Byte = MidiParameter.RCV_HOLD1.default
    var rcvPorta: Byte = MidiParameter.RCV_PORTA.default
    var rcvSust: Byte = MidiParameter.RCV_SUST.default
    var rcvSoftPedal: Byte = MidiParameter.RCV_SOFT_PEDAL.default
    var rcvBankSelect: Byte = MidiParameter.RCV_BANK_SELECT.default
    var scaleTuneC: Byte = MidiParameter.SCALE_TUNE_C.default
    var scaleTuneCS: Byte = MidiParameter.SCALE_TUNE_CS.default
    var scaleTuneD: Byte = MidiParameter.SCALE_TUNE_D.default
    var scaleTuneDS: Byte = MidiParameter.SCALE_TUNE_DS.default
    var scaleTuneE: Byte = MidiParameter.SCALE_TUNE_E.default
    var scaleTuneF: Byte = MidiParameter.SCALE_TUNE_F.default
    var scaleTuneFS: Byte = MidiParameter.SCALE_TUNE_FS.default
    var scaleTuneG: Byte = MidiParameter.SCALE_TUNE_G.default
    var scaleTuneGS: Byte = MidiParameter.SCALE_TUNE_GS.default
    var scaleTuneA: Byte = MidiParameter.SCALE_TUNE_A.default
    var scaleTuneAS: Byte = MidiParameter.SCALE_TUNE_AS.default
    var scaleTuneB: Byte = MidiParameter.SCALE_TUNE_B.default
    var catPitchControl: Byte = MidiParameter.CAT_PITCH_CTRL.default
    var catFilterControl: Byte = MidiParameter.CAT_FILTER_CTRL.default
    var catAmplControl: Byte = MidiParameter.CAT_AMP_CTRL.default
    var catLfoPmodDepth: Byte = MidiParameter.CAT_LFO_PMOD_DEPTH.default
    var catLfoFmodDepth: Byte = MidiParameter.CAT_LFO_FMOD_DEPTH.default
    var catLfoAmodDepth: Byte = MidiParameter.CAT_LFO_AMOD_DEPTH.default
    var patPitchControl: Byte = MidiParameter.PAT_PITCH_CTRL.default
    var patFilterControl: Byte = MidiParameter.PAT_FILTER_CTRL.default
    var patAmplControl: Byte = MidiParameter.PAT_AMP_CTRL.default
    var patLfoPmodDepth: Byte = MidiParameter.PAT_LFO_PMOD_DEPTH.default
    var patLfoFmodDepth: Byte = MidiParameter.PAT_LFO_FMOD_DEPTH.default
    var patLfoAmodDepth: Byte = MidiParameter.PAT_LFO_AMOD_DEPTH.default
    var ac1CtrlNumber: Byte = MidiParameter.AC1_CTRL_NUMBER.default
    var ac1PitchCtrl: Byte = MidiParameter.AC1_PITCH_CTRL.default
    var ac1FilterCtrl: Byte = MidiParameter.AC1_FILTER_CTRL.default
    var ac1AmpCtrl: Byte = MidiParameter.AC1_AMP_CTRL.default
    var ac1LfoPmodDepth: Byte = MidiParameter.AC1_LFO_PMOD_DEPTH.default
    var ac1LfoFmodDepth: Byte = MidiParameter.AC1_LFO_FMOD_DEPTH.default
    var ac1LfoAmodDepth: Byte = MidiParameter.AC1_LFO_AMOD_DEPTH.default
    var ac2CtrlNumber: Byte = MidiParameter.AC2_CTRL_NUMBER.default
    var ac2PitchCtrl: Byte = MidiParameter.AC2_PITCH_CTRL.default
    var ac2FilterCtrl: Byte = MidiParameter.AC2_FILTER_CTRL.default
    var ac2AmpCtrl: Byte = MidiParameter.AC2_AMP_CTRL.default
    var ac2LfoPmodDepth: Byte = MidiParameter.AC2_LFO_PMOD_DEPTH.default
    var ac2LfoFmodDepth: Byte = MidiParameter.AC2_LFO_FMOD_DEPTH.default
    var ac2LfoAmodDepth: Byte = MidiParameter.AC2_LFO_AMOD_DEPTH.default
    var portaSwitch: Byte = MidiParameter.PORTA_SWITCH.default
    var portaTime: Byte = MidiParameter.PORTA_TIME.default
    var pitchEgInitLvl: Byte = MidiParameter.PITCH_EG_INIT_LVL.default
    var pitchEgAttackTime: Byte = MidiParameter.PITCH_EG_ATTACK_TIME.default
    var pitchEgRelLvl: Byte = MidiParameter.PITCH_EG_REL_LVL.default
    var pitchEgRelTime: Byte = MidiParameter.PITCH_EG_REL_TIME.default
    var velocityLimitLo: Byte = MidiParameter.VEL_LIMIT_LOW.default
    var velocityLimitHi: Byte = MidiParameter.VEL_LIMIT_HIGH.default

    var drumVoices: List<DrumVoice>? = null
    var qs300VoiceNumber = 0

    init {
        if (ch == 9) {
            // Drum channel by default. If this isn't a drum channel, these fields will be changed
            // anyway.
            elementReserve = 0
            bankMsb = 127
            partMode = 2
            drumVoices = DrumKitVoiceUtil.standardKit1
        }
    }

    fun setXGNormalVoice(voice: XGNormalVoice) {
        voiceType = VoiceType.NORMAL
        programNumber = voice.program
        bankMsb = 0
        bankLsb = voice.bank
        voiceNameRes = voice.nameRes
        drumVoices = null
        partMode = 0
        // TODO: Set element reserve and part mode for this and other
    }

    fun setDrumKit(drumKit: XGDrumKit) {
        voiceType = VoiceType.DRUM
        programNumber = drumKit.programNumber
        bankMsb = 127
        bankLsb = 0
        voiceNameRes = drumKit.nameRes
        keyOnAssign = 2
        partMode = 2
        drumVoices = drumKit.drumVoices
    }

    fun changeXGVoice(xgVoice: XGNormalVoice) {
        voiceType = VoiceType.NORMAL
        programNumber = xgVoice.program
        bankMsb = MidiConstants.XG_NORMAL_VOICE_MSB
        bankLsb = xgVoice.bank
        voiceNameRes = xgVoice.nameRes
        // TODO: add element reserve here and to normal voice and sfx voice enums, and part mode
    }

    fun changeSFXVoice(sfxNormalVoice: SFXNormalVoice) {
        voiceType = VoiceType.NORMAL
        programNumber = sfxNormalVoice.program
        bankMsb = MidiConstants.XG_SFX_VOICE_MSB
        bankLsb = MidiConstants.XG_SFX_VOICE_LSB
        voiceNameRes = sfxNormalVoice.nameRes
    }

    fun changeQS300Voice(qS300Voice: QS300Voice, voiceNumber: Int) {
        voiceType = VoiceType.QS300
        voiceNameRes = R.string.qs300_voice_label
        qs300VoiceNumber = voiceNumber
        // Do we need to change program number/bank if switching to qs300 voice?
        // What to set voice name to?
        // There might be a way to pass an argument to the "getString" method when updating the
        // label in the part list. Might be a good place to pass in the voice name or index
        // Need to do more research into how the voices and stuff actually work.
    }

    override fun toString(): String {
        return "MidiPart(ch=$ch, voiceNameRes=$voiceNameRes, elementReserve=$elementReserve, bankMsb=$bankMsb, bankLsb=$bankLsb, programNumber=$programNumber, receiveChannel=$receiveChannel)"
    }

    enum class VoiceType {
        NORMAL,
        DRUM,
        SFX,
        QS300
    }
}
