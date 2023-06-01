package com.example.xgbuddy.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.xgbuddy.data.MidiSetup
import com.example.xgbuddy.data.gm.MidiPart
import com.example.xgbuddy.data.xg.*

class MidiViewModel : ViewModel() {
    val channels = MutableLiveData(MutableList(16) { MidiPart(it) })
    val selectedChannel = MutableLiveData(0)
    val selectedDrumVoice = MutableLiveData(0)
    var reverb = Reverb(ReverbType.HALL1)
    var chorus = Chorus(ChorusType.CHORUS1)
    var variation = Variation(VariationType.DELAY_LCR)
    var tuning = SystemParameter.MASTER_TUNE.default
    var volume = SystemParameter.MASTER_VOLUME.default
    var transpose = SystemParameter.TRANSPOSE.default

    fun resetToDefaultSetup() {
        val defaultSetup = MidiSetup.getXGDefault()
        channels.value = defaultSetup.parts.toMutableList()
        reverb = Reverb(ReverbType.HALL1)
        chorus = Chorus(ChorusType.CHORUS1)
        variation = Variation(VariationType.DELAY_LCR)
        tuning = SystemParameter.MASTER_TUNE.default
        volume = SystemParameter.MASTER_VOLUME.default
        transpose = SystemParameter.TRANSPOSE.default
    }
}