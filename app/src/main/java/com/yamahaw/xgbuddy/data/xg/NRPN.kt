package com.yamahaw.xgbuddy.data.xg

import com.yamahaw.xgbuddy.R

enum class NRPN(val nameRes: Int, val msb: Byte, val lsb: Byte?, val default: Byte) {
    VIBRATO_RATE(R.string.nrpn_vibrato_rate, 0x01, 0x08, 0x40),
    VIBRATO_DEPTH(R.string.nrpn_vibrato_rate, 0x01, 0x09, 0x40),
    VIBRATO_DELAY(R.string.nrpn_vibrato_rate, 0x01, 0x0A, 0x40),
    CUTOFF_FREQ(R.string.nrpn_vibrato_rate, 0x01, 0x20, 0x40),
    RESONANCE(R.string.nrpn_vibrato_rate, 0x01, 0x21, 0x40),
    EG_ATTACK(R.string.nrpn_vibrato_rate, 0x01, 0x63, 0x40),
    EG_DECAY(R.string.nrpn_vibrato_rate, 0x01, 0x64, 0x40),
    EG_RELEASE(R.string.nrpn_vibrato_rate, 0x01, 0x66, 0x40),
    DRUM_CUTOFF_FREQ(R.string.nrpn_vibrato_rate, 0x14, null, 0x40),
    DRUM_RESONANCE(R.string.nrpn_vibrato_rate, 0x15, null, 0x40),
    DRUM_ATTACK(R.string.nrpn_vibrato_rate, 0x16, null, 0x40),
    DRUM_DECAY(R.string.nrpn_vibrato_rate, 0x17, null, 0x40),
    DRUM_PITCH_COARSE(R.string.nrpn_vibrato_rate, 0x18, null, 0x40),
    DRUM_PITCH_FINE(R.string.nrpn_vibrato_rate, 0x19, null, 0x40),
    DRUM_LEVEL(R.string.nrpn_vibrato_rate, 0x1A, null, 0x40),
    DRUM_PAN(R.string.nrpn_vibrato_rate, 0x1C, null, 0x40),
    DRUM_REVERB(R.string.nrpn_vibrato_rate, 0x1D, null, 0x28),
    DRUM_CHORUS(R.string.nrpn_vibrato_rate, 0x1E, null, 0x00),
    DRUM_VARIATION(R.string.nrpn_vibrato_rate, 0x1F, null, 0x00);

    /**
     * Drum lsb values are set based on the note. Will need to do some UI design to figure this out.
     */

    fun getMax(): Byte = 0x7f
    fun getMin(): Byte = 0
}