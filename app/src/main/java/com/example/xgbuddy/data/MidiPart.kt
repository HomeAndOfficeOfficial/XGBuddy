package com.example.xgbuddy.data

data class MidiPart(val ch: Int) {
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

    private var elementReserve: Byte = MidiParameter.BANK_LSB.default
    private var bankMsb: Byte = MidiParameter.BANK_LSB.default
    private var bankLsb: Byte = MidiParameter.BANK_LSB.default
    private var programNumber: Byte = MidiParameter.BANK_LSB.default
    private var receiveChannel: Byte = MidiParameter.BANK_LSB.default
    private var polyMode: Byte = MidiParameter.BANK_LSB.default
    private var keyOnAssign: Byte = MidiParameter.BANK_LSB.default
    private var partMode: Byte = MidiParameter.BANK_LSB.default
    private var noteShift: Byte = MidiParameter.BANK_LSB.default
    private var volume: Byte = MidiParameter.BANK_LSB.default
    private var velSensDepth: Byte = MidiParameter.BANK_LSB.default
    private var velSenseOffset: Byte = MidiParameter.BANK_LSB.default
    private var pan: Byte = MidiParameter.BANK_LSB.default
    private var noteLimitLo: Byte = MidiParameter.BANK_LSB.default
    private var noteLimitHi: Byte = MidiParameter.BANK_LSB.default
    private var dryLevel: Byte = MidiParameter.BANK_LSB.default
    private var chorusSend: Byte = MidiParameter.BANK_LSB.default
    private var reverbSend: Byte = MidiParameter.BANK_LSB.default
    private var variationSend: Byte = MidiParameter.BANK_LSB.default
    private var vibratoRate: Byte = MidiParameter.BANK_LSB.default
    private var vibratoDelay: Byte = MidiParameter.BANK_LSB.default
    private var cutoffFreq: Byte = MidiParameter.BANK_LSB.default
    private var resonance: Byte = MidiParameter.BANK_LSB.default
    private var egAttackTime: Byte = MidiParameter.BANK_LSB.default
    private var egDecayTime: Byte = MidiParameter.BANK_LSB.default
    private var mwPitchControl: Byte = MidiParameter.BANK_LSB.default
    private var mwAmplControl: Byte = MidiParameter.BANK_LSB.default
    private var mwFilterControl: Byte = MidiParameter.BANK_LSB.default
    private var mwLfoPmodDepth: Byte = MidiParameter.BANK_LSB.default
    private var msLfoFmodDepth: Byte = MidiParameter.BANK_LSB.default
    private var mwLfoAmodDepth: Byte = MidiParameter.BANK_LSB.default
    private var bendPitchContrl: Byte = MidiParameter.BANK_LSB.default
    private var bendFilterContrl: Byte = MidiParameter.BANK_LSB.default
    private var bendAmplContrl: Byte = MidiParameter.BANK_LSB.default
    private var bendLfoPmodDepth: Byte = MidiParameter.BANK_LSB.default
    private var bendLfoFmodDepth: Byte = MidiParameter.BANK_LSB.default
    private var bendLfoAmodDepth: Byte = MidiParameter.BANK_LSB.default
    private var rcvPitchBend: Byte = MidiParameter.BANK_LSB.default
    private var rcvChAfterTouch: Byte = MidiParameter.BANK_LSB.default
    private var rcvProgramChange: Byte = MidiParameter.BANK_LSB.default
    private var rcvControlChange: Byte = MidiParameter.BANK_LSB.default
    private var rcvPolyAfterTouch: Byte = MidiParameter.BANK_LSB.default
    private var rcvNoteMessage: Byte = MidiParameter.BANK_LSB.default
    private var rcvRpn: Byte = MidiParameter.BANK_LSB.default
    private var rcvNrpn: Byte = MidiParameter.BANK_LSB.default
    private var rcvMod: Byte = MidiParameter.BANK_LSB.default
    private var rcvVolume: Byte = MidiParameter.BANK_LSB.default
    private var rcvPan: Byte = MidiParameter.BANK_LSB.default
    private var rcvExpression: Byte = MidiParameter.BANK_LSB.default
    private var rcvHold: Byte = MidiParameter.BANK_LSB.default
    private var rcvPorta: Byte = MidiParameter.BANK_LSB.default
    private var rcvSust: Byte = MidiParameter.BANK_LSB.default
    private var rcvSoftPedal: Byte = MidiParameter.BANK_LSB.default
    private var rcvBankSelect: Byte = MidiParameter.BANK_LSB.default
    private var scaleTuneC: Byte = MidiParameter.BANK_LSB.default
    private var scaleTuneCS: Byte = MidiParameter.BANK_LSB.default
    private var scaleTuneD: Byte = MidiParameter.BANK_LSB.default
    private var scaleTuneDS: Byte = MidiParameter.BANK_LSB.default
    private var scaleTuneE: Byte = MidiParameter.BANK_LSB.default
    private var scaleTuneF: Byte = MidiParameter.BANK_LSB.default
    private var scaleTuneFS: Byte = MidiParameter.BANK_LSB.default
    private var scaleTuneG: Byte = MidiParameter.BANK_LSB.default
    private var scaleTuneGS: Byte = MidiParameter.BANK_LSB.default
    private var scaleTuneA: Byte = MidiParameter.BANK_LSB.default
    private var scaleTuneAS: Byte = MidiParameter.BANK_LSB.default
    private var scaleTuneB: Byte = MidiParameter.BANK_LSB.default
    private var catPitchControl: Byte = MidiParameter.BANK_LSB.default
    private var catFilterControl: Byte = MidiParameter.BANK_LSB.default
    private var catAmplControl: Byte = MidiParameter.BANK_LSB.default
    private var catLfoPmodDepth: Byte = MidiParameter.BANK_LSB.default
    private var catLfoFmodDepth: Byte = MidiParameter.BANK_LSB.default
    private var catLfoAmodDepth: Byte = MidiParameter.BANK_LSB.default
    private var : Byte = MidiParameter.BANK_LSB.default
    private var : Byte = MidiParameter.BANK_LSB.default
    private var : Byte = MidiParameter.BANK_LSB.default
    private var : Byte = MidiParameter.BANK_LSB.default
    private var : Byte = MidiParameter.BANK_LSB.default
    private var : Byte = MidiParameter.BANK_LSB.default
    private var : Byte = MidiParameter.BANK_LSB.default
    private var : Byte = MidiParameter.BANK_LSB.default
    private var : Byte = MidiParameter.BANK_LSB.default
    private var : Byte = MidiParameter.BANK_LSB.default
    private var : Byte = MidiParameter.BANK_LSB.default
    private var : Byte = MidiParameter.BANK_LSB.default
    private var : Byte = MidiParameter.BANK_LSB.default
    private var : Byte = MidiParameter.BANK_LSB.default
    private var : Byte = MidiParameter.BANK_LSB.default
    private var : Byte = MidiParameter.BANK_LSB.default
    private var : Byte = MidiParameter.BANK_LSB.default
    private var : Byte = MidiParameter.BANK_LSB.default
    private var : Byte = MidiParameter.BANK_LSB.default
    private var : Byte = MidiParameter.BANK_LSB.default
    private var : Byte = MidiParameter.BANK_LSB.default
    private var : Byte = MidiParameter.BANK_LSB.default
    private var : Byte = MidiParameter.BANK_LSB.default
    private var : Byte = MidiParameter.BANK_LSB.default
    private var : Byte = MidiParameter.BANK_LSB.default
    private var : Byte = MidiParameter.BANK_LSB.default
    private var : Byte = MidiParameter.BANK_LSB.default
    private var : Byte = MidiParameter.BANK_LSB.default
    private var : Byte = MidiParameter.BANK_LSB.default
    private var : Byte = MidiParameter.BANK_LSB.default
    private var : Byte = MidiParameter.BANK_LSB.default
}
