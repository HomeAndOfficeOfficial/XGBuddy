package com.yamahaw.xgbuddy.data.xg

import com.yamahaw.xgbuddy.data.MidiData
import java.util.*

abstract class Effect(
    var nameRes: Int,
    var msb: Byte,
    var lsb: Byte,
    var parameterList: EnumMap<EffectParameterData, EffectParameter>?,
) : MidiData() {

    abstract var defaultValues: IntArray?
    abstract val baseAddr: Byte
    var param1: Int = 0
    var param2: Int = 0
    var param3: Int = 0
    var param4: Int = 0
    var param5: Int = 0
    var param6: Int = 0
    var param7: Int = 0
    var param8: Int = 0
    var param9: Int = 0
    var param10: Int = 0
    var param11: Int = 0
    var param12: Int = 0
    var param13: Int = 0
    var param14: Int = 0
    var param15: Int = 0
    var param16: Int = 0

    protected fun initializeDefaultValues() {
        defaultValues?.let {
            param1 = it[0]
            param2 = it[1]
            param3 = it[2]
            param4 = it[3]
            param5 = it[4]
            param6 = it[5]
            param7 = it[6]
            param8 = it[7]
            param9 = it[8]
            param10 = it[9]
            param11 = it[10]
            param12 = it[11]
            param13 = it[12]
            param14 = it[13]
            param15 = it[14]
            param16 = it[15]
        }
    }

    protected fun updateEffectType(effectType: ReverbType) {
        nameRes = effectType.nameRes
        msb = effectType.msb
        lsb = effectType.lsb
        parameterList = effectType.parameterList
    }

    protected fun updateEffectType(effectType: ChorusType) {
        nameRes = effectType.nameRes
        msb = effectType.msb
        lsb = effectType.lsb
        parameterList = effectType.getParameterList()
    }

    protected fun updateEffectType(effectType: VariationType) {
        nameRes = effectType.nameRes
        msb = effectType.msb
        lsb = effectType.lsb
        parameterList = effectType.parameterList
    }

}
