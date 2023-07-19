package com.yamahaw.xgbuddy.data.xg.effect

import com.yamahaw.xgbuddy.R
import java.util.*

enum class ChorusType(val nameRes: Int, val msb: Byte, val lsb: Byte, val parameterDefaults: IntArray?) {
    CHORUS1(R.string.vari_chorus_1, 0x41, 0, EffectParameterDefaults.chorus1Defaults),
    CHORUS2(R.string.vari_chorus_2, 0x41, 0x01, EffectParameterDefaults.chorus2Defaults),
    CHORUS3(R.string.vari_chorus_3, 0x41, 0x02, EffectParameterDefaults.chorus3Defaults),
    CHORUS4(R.string.vari_chorus_4, 0x41, 0x08, EffectParameterDefaults.chorus4Defaults),
    CELESTE1(R.string.vari_celeste_1, 0x42, 0, EffectParameterDefaults.celeste1Defaults),
    CELESTE2(R.string.vari_celeste_2, 0x42, 0x01, EffectParameterDefaults.celeste2Defaults),
    CELESTE3(R.string.vari_celeste_3, 0x42, 0x02, EffectParameterDefaults.celeste3Defaults),
    CELESTE4(R.string.vari_celeste_4, 0x42, 0x08, EffectParameterDefaults.celeste4Defaults),
    FLANGER1(R.string.vari_flanger_1, 0x43, 0, EffectParameterDefaults.flanger1Defaults),
    FLANGER2(R.string.vari_flanger_2, 0x43, 0x01, EffectParameterDefaults.flanger2Defaults),
    FLANGER3(R.string.vari_flanger_3, 0x43, 0x08, EffectParameterDefaults.flanger3Defaults);

    fun getParameterList(): EnumMap<EffectParameterData, EffectParameter> =
        VariationParameterLists.chorus
}