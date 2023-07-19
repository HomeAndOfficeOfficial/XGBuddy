package com.yamahaw.xgbuddy.data.xg.effect

import androidx.annotation.Keep
import com.yamahaw.xgbuddy.util.EnumFinder.findBy

@Keep
class Variation(variationType: VariationType) : Effect(
    variationType.nameRes,
    variationType.msb,
    variationType.lsb,
    variationType.parameterList
) {

    var variationType = variationType
        set(value) {
            field = value
            updateEffectType(value)
            defaultValues = (VariationType::nameRes findBy nameRes)!!.parameterDefaults
            initializeDefaultValues()
        }

    override val baseAddr: Byte = 0x40
    override var defaultValues: IntArray? =
        (VariationType::nameRes findBy nameRes)!!.parameterDefaults

    init {
        initializeDefaultValues()
    }

    var variReturn = EffectParameterData.VARIATION_RETURN.default
    var variPan = EffectParameterData.VARIATION_PAN.default
    var sendReverb = EffectParameterData.SEND_VARI_TO_REV.default
    var sendChorus = EffectParameterData.SEND_VARI_TO_CHOR.default
    var part: Byte = 0x7f // 0...0x0f = Part 1...16, 0x40 = A/D, 0x7f = off
    var connection: Byte = 0
    var modWheelDepth = EffectParameterData.MW_VARI_CTRL_DEPTH.default
    var bendDepth = EffectParameterData.BEND_VARI_CTRL_DEPTH.default
    var catDepth = EffectParameterData.CAT_VARI_CTRL_DEPTH.default
    var ac1Depth = EffectParameterData.AC1_VARI_CTRL_DEPTH.default
    var ac2Depth = EffectParameterData.AC2_VARI_CTRL_DEPTH.default

    companion object {
        val partValues = ByteArray(18) {
            when (it) {
                16 -> 0x40.toByte()
                17 -> 0x7f.toByte()
                else -> it.toByte()
            }
        }
    }
}