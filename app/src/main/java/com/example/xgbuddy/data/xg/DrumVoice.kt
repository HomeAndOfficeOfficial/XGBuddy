package com.example.xgbuddy.data.xg

import com.example.xgbuddy.data.MidiData

data class DrumVoice(var name: String) : MidiData() {
    var pitchCoarse: Byte = DrumVoiceParameter.PITCH_COARSE.default
    var pitchFine: Byte = DrumVoiceParameter.PITCH_FINE.default
    var level: Byte = DrumVoiceParameter.LEVEL.default
    var alternateGroup: Byte = DrumVoiceParameter.ALTERNATE_GROUP.default
    var pan: Byte = DrumVoiceParameter.PAN.default
    var reverbSend: Byte = DrumVoiceParameter.REVERB_SEND.default
    var chorusSend: Byte = DrumVoiceParameter.CHORUS_SEND.default
    var variSend: Byte = DrumVoiceParameter.VARI_SEND.default
    var keyAssign: Byte = DrumVoiceParameter.KEY_ASSIGN.default
    var rcvNoteOff: Byte = DrumVoiceParameter.RCV_NOTE_OFF.default
    var rcvNoteOn: Byte = DrumVoiceParameter.RCV_NOTE_ON.default
    var cutoffFreq: Byte = DrumVoiceParameter.CUTOFF_FREQ.default
    var resonance: Byte = DrumVoiceParameter.RESONANCE.default
    var egAttack: Byte = DrumVoiceParameter.EG_ATTACK.default
    var egDecay1: Byte = DrumVoiceParameter.EG_DECAY1.default
    var egDecay2: Byte = DrumVoiceParameter.EG_DECAY2.default
}
