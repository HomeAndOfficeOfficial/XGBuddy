package com.example.xgbuddy.data

import com.example.xgbuddy.R

enum class ChannelModeMessage(val nameRes: Int, val cc: MidiControlChange) {
    ALL_SOUND_OFF(R.string.cmm_all_sound_off, MidiControlChange.ALL_SOUND_OFF),
    RESET_ALL_CTRL(R.string.cmm_reset_ctrl, MidiControlChange.RESET_ALL_CTRL),
    ALL_NOTE_OFF(R.string.cmm_all_note_off, MidiControlChange.ALL_NOTE_OFF),
    OMNI_OFF(R.string.cmm_omni_off, MidiControlChange.OMNI_MODE_ON),
    OMNI_ON(R.string.cmm_omni_on, MidiControlChange.OMNI_MODE_ON),
    MONO(R.string.cmm_mono, MidiControlChange.MONO_MODE),
    POLY(R.string.cmm_poly, MidiControlChange.POLY_MODE)
}