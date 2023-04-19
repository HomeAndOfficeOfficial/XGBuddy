package com.example.xgbuddy.data

import com.example.xgbuddy.data.xg.XGNormalVoice

data class MidiPart(val ch: Int): MidiData() {
    /**
     * Will probably need a field to specify whether this is a drum part, a qs300 voice, or a
     * regular instrument voice. May not be necessary to distinguish between GM, TG300B, etc at this
     * level.
     */

    /**
     * TODO: Add enums for effect types. Might need separate ones for Reverb, Chorus, and Variation.
     *  Maybe look into that after all the base midi stuff is in place? Maybe just add it all now
     *  so it's fully understood.
     */

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

    init {
        if (ch == 9) {
            // Drum channel by default. If this isn't a drum channel, these fields will be changed
            // anyway.
            elementReserve = 0
            bankMsb = 127
            partMode = 2
        }
    }

    fun setXGNormalVoice(voice: XGNormalVoice) {
        programNumber = voice.program
        bankMsb = 0
        bankLsb = voice.bank
        // Set element reserve?
    }

    /**
     * TODO: Better solution for this may be have a utility class that contains a bunch of possible
     *  midi message calls. Each method takes in a MidiPart as a param or whatever it needs to take.
     *  That way these data classes don't have any knowledge of other classes/constants/etc. For
     *  now just leave these message methods where they are since I'm still figuring it all out.
     */

    fun getProgramChangeMessage(): MidiMessage {
        val statusByte = (MidiConstants.STATUS_PROGRAM_CHANGE and ch).toByte()
        return MidiMessage(byteArrayOf(statusByte, programNumber), 0)
    }

    fun changeXGVoice(xgVoice: XGNormalVoice) {
        programNumber = xgVoice.program
        bankMsb = 0
        bankLsb = xgVoice.bank
        // TODO: Set name too
    }
}
