package com.example.xgbuddy

import android.content.Context
import android.media.midi.MidiDevice
import android.media.midi.MidiDeviceInfo
import android.media.midi.MidiDeviceStatus
import android.media.midi.MidiInputPort
import android.media.midi.MidiManager
import android.media.midi.MidiManager.DeviceCallback
import android.media.midi.MidiManager.OnDeviceOpenedListener
import android.media.midi.MidiOutputPort
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.xgbuddy.data.DeviceError
import java.util.concurrent.Executor

/**
 * For now, it's unclear to me what I'm supposed to do with the device callbacks because they don't
 * seem to work when I plug/unplug the UM-1. The registered executor does something, so I guess I
 * could use that to check if there is a device connected, if it has input/output ports, then use
 * info to deduce whether or not a device has been attached or detached.
 *
 * I can create a DeviceStatusCallback that I could pass in to this class from the main fragment.
 * That can respond to attach/detach events, displaying a popup if we get disconnected.
 *
 * From there, probably choose between a QS300 or XG mode. That will send the appropriate command
 * to the output port.
 *
 * I guess once I get the initial setup figured out, I'll need to figure out sending midi data.
 * Probably will need a class or set of utility functions to encode and send commands.
 *
 * I will need some basic in/out indicators.
 *
 * Once I can send data, need to start mapping out all the xg-specific things i need to send, and
 * start defining those.
 *
 * Then I'll have to get into the QS300 voices, and see what all I am able to control.
 */

private const val NOT_OPEN = -1

@Suppress("DEPRECATION") // To accommodate API < 33
class MyMidiManager(context: Context, private val callback: MyMidiDeviceCallback) :
    DeviceCallback() {
    private val midiManager: MidiManager =
        context.getSystemService(Context.MIDI_SERVICE) as MidiManager
    private var devices: Set<MidiDeviceInfo>

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
            devices = midiManager.getDevicesForTransport(MidiManager.TRANSPORT_MIDI_BYTE_STREAM)
            if (devices.isNotEmpty()) {
                callback.onConnectionStatusChanged(devices)
            }
        } else {
            devices = midiManager.devices.toSet()
            midiManager.registerDeviceCallback(this, Handler(Looper.getMainLooper()))
        }
    }

    override fun onDeviceStatusChanged(status: MidiDeviceStatus?) {
        super.onDeviceStatusChanged(status)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            devices = midiManager.devices.toSet()
            callback.onConnectionStatusChanged(devices)
        }
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