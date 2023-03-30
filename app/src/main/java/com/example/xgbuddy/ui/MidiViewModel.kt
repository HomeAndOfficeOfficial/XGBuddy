package com.example.xgbuddy.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.xgbuddy.data.MidiChannel

class MidiViewModel : ViewModel() {
    val channels = MutableLiveData(List(16) { MidiChannel(it) })
    val selectedChannel = MutableLiveData(-1)
}