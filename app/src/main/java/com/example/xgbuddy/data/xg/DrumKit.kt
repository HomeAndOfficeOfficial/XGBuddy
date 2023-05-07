package com.example.xgbuddy.data.xg

import com.example.xgbuddy.data.gm.MidiPart

data class DrumKit(val partNum: Int, var name: String, val drumVoices: List<DrumVoice>): MidiPart(partNum) {
    init {
        keyOnAssign = 2
        partMode = 2
    }
}
