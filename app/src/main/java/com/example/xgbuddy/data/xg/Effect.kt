package com.example.xgbuddy.data.xg

import com.example.xgbuddy.util.EnumFinder.findBy

abstract class Effect(
    val nameRes: Int,
    val msb: Byte,
    val lsb: Byte,
    val parameterList: Array<EffectParameter?>?,
) {

    protected abstract fun getEffectDefaults(): IntArray?

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
