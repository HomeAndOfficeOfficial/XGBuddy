package com.example.xgbuddy

import android.content.Context
import android.media.midi.MidiDevice
import android.media.midi.MidiDeviceInfo
import androidx.lifecycle.MutableLiveData
import com.example.xgbuddy.data.InstrumentMode
import com.example.xgbuddy.data.MidiSetup
import javax.inject.Inject

class MidiSession @Inject constructor(context: Context) {

    private var inputDevices: MutableMap<String, MidiDevice> = mutableMapOf()
    private var outputDevices: MutableMap<String, MidiDevice> = mutableMapOf()

    val outputDeviceOpened = MutableLiveData(false)
    val inputDeviceOpened = MutableLiveData(false)
    val connectedDeviceList = MutableLiveData(setOf<MidiDeviceInfo>())

    val midiManager = MyMidiManager(context, object : MyMidiManager.MyMidiDeviceCallback {
        override fun onInputDeviceOpened(device: MidiDevice) {
            inputDeviceOpened.postValue(true)
            inputDevices[device.info.properties.getString(MidiDeviceInfo.PROPERTY_NAME)!!] = device
        }

        override fun onOutputDeviceOpened(device: MidiDevice) {
            outputDeviceOpened.postValue(true)
            outputDevices[device.info.properties.getString(MidiDeviceInfo.PROPERTY_NAME)!!] = device
        }

        override fun onConnectionStatusChanged(devices: Set<MidiDeviceInfo>) {
            connectedDeviceList.postValue(devices)
            if (devices.isEmpty()) {
                outputDeviceOpened.postValue(false)
                inputDeviceOpened.postValue(false)
            }
        }
    })

    var midiSetup = MidiSetup.getXGDefault()

    fun getInputDevices(): Map<String, MidiDevice> = inputDevices

    fun getOutputDevices(): Map<String, MidiDevice> = outputDevices

    fun openInputDevice(deviceInfo: MidiDeviceInfo) {
        midiManager.openInputDevice(deviceInfo)
    }

    fun removeInputDevice(deviceInfo: MidiDeviceInfo) {
        val name = deviceInfo.properties.getString(MidiDeviceInfo.PROPERTY_NAME)
        inputDevices.remove(name)
    }

    fun openOutputDevice(deviceInfo: MidiDeviceInfo) {
        midiManager.openOutputDevice(deviceInfo)
    }

    fun removeOutputDevice(deviceInfo: MidiDeviceInfo) {
        val name = deviceInfo.properties.getString(MidiDeviceInfo.PROPERTY_NAME)
        outputDevices.remove(name)
    }

    fun createNewSetup(instrumentMode: InstrumentMode) {

        midiSetup = if (instrumentMode == InstrumentMode.XG) {
            MidiSetup.getXGDefault()
        } else {
            MidiSetup.getQSDefault()
        }

        /**
         * Just a note: Instead of sending an entire default setup here, I think I could probably
         * just send a "reset all parameters" command.
         *
         * From a fresh setup, any time a control is changed, that is added to a file (.xbs).
         *
         * Whenever a setup is loaded from a file, send a reset command, then send only the parameters
         * that are required for that setup.
         *
         * I will still need to set initial values for everything in the code. It may take some work
         * to determine what the default values for everything are when you reset every parameter.
         */

        /*
        TODO: Work on data structures. Can't really continue on without having the MIDI data structs
            all planned out.

            Then need to work on encoding and sending MIDI.

            Then will need to figure out how to send setups.
         */
    }

}