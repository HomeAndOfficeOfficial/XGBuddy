package com.example.xgbuddy

import android.content.Context
import android.media.midi.MidiDevice
import android.media.midi.MidiDeviceInfo
import android.media.midi.MidiInputPort
import android.media.midi.MidiOutputPort
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

// This will be the singleton class
// Has a midimanager and a midi session, and methods to send and receive midi data

class MidiSession @Inject constructor(context: Context) {

    val midiManager = MyMidiManager(context, object : MyMidiManager.MyMidiDeviceCallback {
        override fun onInputDeviceOpened(device: MidiDevice) {
            inputDeviceOpened.postValue(true)
        }

        override fun onOutputDeviceOpened(device: MidiDevice) {
            outputDeviceOpened.postValue(true)
        }

        override fun onConnectionStatusChanged(devices: Set<MidiDeviceInfo>) {
            connectedDeviceList.postValue(devices)
            if (devices.isEmpty()) {
                outputDeviceOpened.postValue(false)
                inputDeviceOpened.postValue(false)
            }
        }
    })

    val outputDeviceOpened = MutableLiveData(false)
    val inputDeviceOpened = MutableLiveData(false)
    val connectedDeviceList = MutableLiveData(setOf<MidiDeviceInfo>())

}