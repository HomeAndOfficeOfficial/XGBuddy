package com.example.xgbuddy.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.xgbuddy.data.gm.MidiPart
import com.example.xgbuddy.data.xg.Chorus
import com.example.xgbuddy.data.xg.Effect
import com.example.xgbuddy.data.xg.Reverb
import com.example.xgbuddy.data.xg.Variation

class MidiViewModel : ViewModel() {
    val channels = MutableLiveData(MutableList(16) { MidiPart(it) })
    val selectedChannel = MutableLiveData(0)
    val selectedDrumVoice = MutableLiveData(0)
    val reverb = MutableLiveData(Effect(Reverb.HALL1))
    val chorus = MutableLiveData(Effect(Chorus.CHORUS1))
    val variation = MutableLiveData(Variation.DELAY_LCR)
}