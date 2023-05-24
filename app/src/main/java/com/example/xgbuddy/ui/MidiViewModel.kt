package com.example.xgbuddy.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.xgbuddy.data.gm.MidiPart
import com.example.xgbuddy.data.xg.*

class MidiViewModel : ViewModel() {
    val channels = MutableLiveData(MutableList(16) { MidiPart(it) })
    val selectedChannel = MutableLiveData(0)
    val selectedDrumVoice = MutableLiveData(0)
    var reverb = Reverb(ReverbType.HALL1)
    val chorus = MutableLiveData(Chorus(ChorusType.CHORUS1))
    val variation = MutableLiveData(Variation(VariationType.DELAY_LCR))
}