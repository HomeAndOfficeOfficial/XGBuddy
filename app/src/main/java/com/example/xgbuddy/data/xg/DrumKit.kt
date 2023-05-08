package com.example.xgbuddy.data.xg

import com.example.xgbuddy.data.gm.MidiPart

/**
 * Some notes on how I imagine the drum edit fragment will work
 *
 * I think it will be similar to the parts fragment. On the left side will be
 * buttons in a grid for all the drum voices. Clicking will send a note_on
 * for that voice. (This feature should be toggleable. Probably disabled by default)
 *
 * The voice parameters will be displayed on the right, similar to
 * PartEditFragment.
 *
 * At the top there will be a spinner for selecting the drum kit. The SFX options
 * should probably be listed in there as well, and they can be controlled in the same
 * manner. I'll have to verify how parameters work on sfx voices though.
 */

data class DrumKit(val partNum: Int, var name: String, val drumVoices: List<DrumVoice>) :
    MidiPart(partNum) {
    init {
        keyOnAssign = 2
        partMode = 2
    }
}
