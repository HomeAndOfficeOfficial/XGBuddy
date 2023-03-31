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
}
