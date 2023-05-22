package com.example.xgbuddy.data.xg

import com.example.xgbuddy.util.EnumFinder.findBy

data class Effect(
    val nameRes: Int,
    val msb: Byte,
    val lsb: Byte,
    val parameterList: Array<EffectParameter?>?,
    val effectType: EffectType
) {

    constructor(reverb: Reverb) : this(
        reverb.nameRes,
        reverb.msb,
        reverb.lsb,
        reverb.parameterList,
        EffectType.REVERB
    )

    constructor(chorus: Chorus) : this(
        chorus.nameRes,
        chorus.msb,
        chorus.lsb,
        chorus.getParameterList(),
        EffectType.CHORUS
    )

    constructor(variation: Variation) : this(
        variation.nameRes,
        variation.msb,
        variation.lsb,
        variation.parameterList,
        EffectType.VARIATION
    )

    var parameterValues: IntArray? = getEffectDefaults()

    private fun getEffectDefaults(): IntArray? =
        when (effectType) {
            EffectType.REVERB -> (Reverb::nameRes findBy nameRes)!!.parameterDefaults
            EffectType.CHORUS -> (Chorus::nameRes findBy nameRes)!!.parameterDefaults
            EffectType.VARIATION -> (Variation::nameRes findBy nameRes)!!.parameterDefaults
        }

    enum class EffectType { REVERB, CHORUS, VARIATION }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Effect

        if (nameRes != other.nameRes) return false
        if (msb != other.msb) return false
        if (lsb != other.lsb) return false
        if (!parameterList.contentEquals(other.parameterList)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = nameRes
        result = 31 * result + msb
        result = 31 * result + lsb
        result = 31 * result + parameterList.contentHashCode()
        return result
    }
}
