package com.yamahaw.xgbuddy

import android.content.Context
import android.media.midi.MidiDevice
import android.media.midi.MidiDeviceInfo
import android.media.midi.MidiInputPort
import android.media.midi.MidiOutputPort
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.yamahaw.xgbuddy.data.*
import javax.inject.Inject
import kotlin.math.min

class MidiSession @Inject constructor(context: Context) {

    private val midiReceiver = MyMidiReceiver(object : MidiReceiverListener() {

    })

    private var midiReceivedListener: OnMidiReceivedListener? = null
    private var inputDevices: MutableMap<String, MidiDevice> = mutableMapOf()
    private var outputDevices: MutableMap<String, MidiDevice> = mutableMapOf()

    val outputDeviceOpened = MutableLiveData(false)
    val inputDeviceOpened = MutableLiveData(false)
    val connectedDeviceList = MutableLiveData(setOf<MidiDeviceInfo>())

    val midiManager = MyMidiManager(context, object : MyMidiManager.MyMidiDeviceCallback {
        override fun onInputDeviceOpened(device: MidiDevice, inputPort: MidiInputPort) {
            midiReceiver.inputPort = inputPort
            inputDeviceOpened.postValue(true)
            inputDevices[device.info.properties.getString(MidiDeviceInfo.PROPERTY_NAME)!!] = device
        }

        override fun onOutputDeviceOpened(device: MidiDevice, outputPort: MidiOutputPort) {
            Log.d(TAG, "Should have an output device")
            outputPort.connect(midiReceiver)
            outputDeviceOpened.postValue(true)
            outputDevices[device.info.properties.getString(MidiDeviceInfo.PROPERTY_NAME)!!] = device
        }

        override fun onConnectionStatusChanged(devices: Set<MidiDeviceInfo>) {
            connectedDeviceList.postValue(devices)
            if (devices.isEmpty()) {
                inputDevices.clear()
                outputDevices.clear()
                outputDeviceOpened.postValue(false)
                inputDeviceOpened.postValue(false)
            }
        }

        override fun onDeviceError(error: DeviceError) {
            Log.e(TAG, error.name)
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

    fun registerForMidiCallbacks(listener: OnMidiReceivedListener) {
        midiReceivedListener = listener
    }

    fun unregisterMidiListener(listener: OnMidiReceivedListener) {
        if (midiReceivedListener == listener) {
            midiReceivedListener = null
            midiReceiver.flush()
        }
    }

    /**TODO: Create separate send method for bulk dump. I think everything else can be send
     *  normally, but bulk dump is going to require some extra effort.
     */

    fun send(midiMessages: List<MidiMessage>) {
        midiMessages.forEach {
            send(it)
        }
    }

    fun sendBulkMessages(bulkMessages: List<MidiMessage>) {
        bulkMessages.forEach {
            sendBulkMessage(it)
        }
    }

    fun sendBulkMessage(bulkMessage: MidiMessage) {
        midiManager.inputPort?.let { inputPort ->
            var bytesSent = 0
            var sendCount = 0
            var timestamp = bulkMessage.timestamp
            while (bytesSent < bulkMessage.msg!!.size) {
                val buffer = ByteArray(min(bulkMessage.msg.size - bytesSent, 64)) {
                    bulkMessage.msg[bytesSent++]
                }
                if (timestamp > 0) {
                    inputPort.send(
                        buffer,
                        0,
                        buffer.size,
                        timestamp
                    )
                    timestamp += 1000000
                } else {
                    inputPort.send(
                        buffer,
                        0,
                        buffer.size
                    )
                }
                sendCount++
            }
        }
    }

    fun send(midiMessage: MidiMessage) {
        if (midiMessage.msg != null) {
            if (midiMessage.timestamp > 0) {
                midiManager.inputPort?.send(
                    midiMessage.msg,
                    0,
                    midiMessage.msg.size,
                    midiMessage.timestamp
                )
            } else {
                midiManager.inputPort?.send(midiMessage.msg, 0, midiMessage.msg!!.size)
            }
        }
    }

    fun interface OnMidiReceivedListener {
        fun onMidiMessageReceived(message: MidiMessage)
    }

    companion object {
        private const val TAG = "MidiSession"
    }

}