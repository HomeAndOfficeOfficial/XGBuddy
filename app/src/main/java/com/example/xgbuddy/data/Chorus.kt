package com.example.xgbuddy.data

import com.example.xgbuddy.R

enum class Chorus(val nameRes: Int, val msb: Byte, val lsb: Byte) {
    CHORUS1(R.string.vari_chorus_1, 0x41, 0),
    CHORUS2(R.string.vari_chorus_2, 0x41, 0x01),
    CHORUS3(R.string.vari_chorus_3, 0x41, 0x02),
    CHORUS4(R.string.vari_chorus_4, 0x41, 0x08),
    CELESTE1(R.string.vari_celeste_1, 0x42, 0),
    CELESTE2(R.string.vari_celeste_2, 0x42, 0x01),
    CELESTE3(R.string.vari_celeste_3, 0x42, 0x02),
    CELESTE4(R.string.vari_celeste_4, 0x42, 0x08),
    FLANGER1(R.string.vari_flanger_1, 0x43, 0),
    FLANGER2(R.string.vari_flanger_2, 0x43, 0x01),
    FLANGER3(R.string.vari_flanger_3, 0x43, 0x08),
}