package com.yamahaw.xgbuddy

import com.yamahaw.xgbuddy.data.MidiControlChange
import com.yamahaw.xgbuddy.data.gm.MidiParameter
import com.yamahaw.xgbuddy.data.xg.DrumVoiceParameter
import com.yamahaw.xgbuddy.data.xg.EffectParameterData

open class MidiReceiverListener {
    open fun onEffectParameterChangeReceived(
        channel: Int,
        parameter: EffectParameterData,
        value: Int
    ) {
    }

    open fun onVoiceParameterChangeReceived(channel: Int, parameter: MidiParameter, value: Int) {}
    open fun onDrumParameterChangeReceived(channel: Int, parameter: DrumVoiceParameter) {}
    open fun onControlChangeReceived(channel: Int, cc: MidiControlChange, value: Int) {}
    open fun onProgramChangeReceived(channel: Int, programNumber: Int) {}
    open fun onNoteReceived(channel: Int, message: ByteArray) {}
}