package com.yamahaw.xgbuddy.midi

import android.content.Context
import android.media.midi.MidiDevice
import android.media.midi.MidiDeviceInfo
import android.media.midi.MidiInputPort
import android.media.midi.MidiManager
import android.media.midi.MidiManager.DeviceCallback
import android.media.midi.MidiOutputPort
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.yamahaw.xgbuddy.data.comms.DeviceError

private const val NOT_OPEN = -1

@Suppress("DEPRECATION") // To accommodate API < 33
class MyMidiManager(context: Context, private val callback: MyMidiDeviceCallback) :
    DeviceCallback() {
    private val midiManager: MidiManager =
        context.getSystemService(Context.MIDI_SERVICE) as MidiManager
    private lateinit var devices: Set<MidiDeviceInfo>

    var inputDevice: MidiDeviceInfo? = null
    var outputDevice: MidiDeviceInfo? = null

    var outputPortNumber: Int = NOT_OPEN
    var inputPortNumber: Int = NOT_OPEN

    var inputPort: MidiInputPort? = null

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            midiManager.registerDeviceCallback(
                MidiManager.TRANSPORT_MIDI_BYTE_STREAM,
                {
                    Log.d(TAG, "Connection status change, should be calling callback")
                    devices =
                        midiManager.getDevicesForTransport(MidiManager.TRANSPORT_MIDI_BYTE_STREAM)
                    callback.onConnectionStatusChanged(devices)
                }, this
            )
            initDeviceList()
        } else {
            initDeviceList()
            midiManager.registerDeviceCallback(this, Handler(Looper.getMainLooper()))
        }
    }

    private fun initDeviceList() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            devices = midiManager.getDevicesForTransport(MidiManager.TRANSPORT_MIDI_BYTE_STREAM)
            if (devices.isNotEmpty()) {
                callback.onConnectionStatusChanged(devices)
            }
        } else {
            devices = midiManager.devices.toSet()
            callback.onConnectionStatusChanged(devices)
        }
    }

    override fun onDeviceAdded(device: MidiDeviceInfo?) {
        super.onDeviceAdded(device)
        initDeviceList()
    }

    override fun onDeviceRemoved(device: MidiDeviceInfo?) {
        super.onDeviceRemoved(device)
        initDeviceList()
    }

    fun openInputDevice(deviceInfo: MidiDeviceInfo) {
        midiManager.openDevice(
            deviceInfo,
            { device ->
                inputDevice = deviceInfo
                openInputPort(device)
            },
            Handler(Looper.getMainLooper())
        )
    }

    fun openOutputDevice(deviceInfo: MidiDeviceInfo) {
        midiManager.openDevice(
            deviceInfo,
            { device ->
                openOutputPort(device)
//                if (openOutputPort(device)) {
//                    callback.onOutputDeviceOpened(device)
//                } else {
//                    callback.onDeviceError(DeviceError.NO_OUTPUT_PORT)
//                }
            },
            Handler(Looper.getMainLooper())
        )
    }

    private fun openOutputPort(device: MidiDevice) {
        val portInfo = device.info.ports
        portInfo.forEach { port ->
            Log.d(TAG, "Port: ${port.portNumber}, type: ${port.type}")
            if (port.type == MidiDeviceInfo.PortInfo.TYPE_OUTPUT) {
                val outputPort = device.openOutputPort(port.portNumber)
                if (outputPort != null) {
                    outputPortNumber = port.portNumber
                    callback.onOutputDeviceOpened(device, outputPort)
                } else {
                    outputPortNumber = NOT_OPEN
                    callback.onDeviceError(DeviceError.OUTPUT_PORT_FAILED)
                }
            }
        }
    }

    private fun openInputPort(device: MidiDevice) {
        val portInfo = device.info.ports
        portInfo.forEach { port ->
            Log.d(TAG, "Port: ${port.portNumber}, type: ${port.type}")
            if (port.type == MidiDeviceInfo.PortInfo.TYPE_INPUT) {
                inputPort = device.openInputPort(port.portNumber)
                if (inputPort != null) {
                    inputPortNumber = port.portNumber
                    callback.onInputDeviceOpened(device, inputPort!!)
                    // TODO: Figure out best implementation for connecting ports (factor in potential
                    //  for multiple devices)
//                    if (outputPortNumber != NOT_OPEN) {
//                        device.connectPorts(inputPort, outputPortNumber)
//                    }
                } else {
                    callback.onDeviceError(DeviceError.INPUT_PORT_FAILED)
                }
            }
        }
    }

    interface MyMidiDeviceCallback {
        /**
         * Might change the parameter that's passed to these functions
         */
        fun onInputDeviceOpened(device: MidiDevice, inputPort: MidiInputPort)
        fun onOutputDeviceOpened(device: MidiDevice, outputPort: MidiOutputPort)
        fun onConnectionStatusChanged(devices: Set<MidiDeviceInfo>)
        fun onDeviceError(error: DeviceError)
    }

    companion object {
        private const val TAG = "MyMidiManager"
    }
}