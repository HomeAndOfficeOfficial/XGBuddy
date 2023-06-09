package com.example.xgbuddy

import com.example.xgbuddy.data.MidiControlChange
import com.example.xgbuddy.data.gm.MidiParameter
import com.example.xgbuddy.data.xg.DrumVoiceParameter
import com.example.xgbuddy.data.xg.EffectParameterData

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
}