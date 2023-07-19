package com.yamahaw.xgbuddy.data.xg.drum

import androidx.annotation.Keep
import kotlin.reflect.KMutableProperty

@Keep
data class DrumVoice(var name: String,
                     var pitchCoarse: Byte,
                     var pitchFine: Byte,
                     var level: Byte,
                     var alternateGroup: Byte,
                     var pan: Byte,
                     var reverbSend: Byte,
                     var chorusSend: Byte,
                     var variSend: Byte,
                     var keyAssign: Byte,
                     var rcvNoteOff: Byte,
                     var rcvNoteOn: Byte,
                     var cutoffFreq: Byte,
                     var resonance: Byte,
                     var egAttack: Byte,
                     var egDecay1: Byte,
                     var egDecay2: Byte) {

    fun setProperty(property: KMutableProperty<Byte>?, value: Byte) {
        property?.setter!!.call(this, value)
    }

    fun getPropertyValue(property: KMutableProperty<Byte>?): Byte =
        property?.getter!!.call(this)
}
