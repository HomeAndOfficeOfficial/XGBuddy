package com.example.xgbuddy

import android.media.midi.MidiDevice
import android.media.midi.MidiDeviceInfo
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {

    @Inject
    lateinit var midiSession: MidiSession

    private var connectedDevices: Set<MidiDeviceInfo> = setOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        midiSession.apply {
            connectedDeviceList.observe(viewLifecycleOwner) {
                connectedDevices = it
                // When connected device list changes, display a dialog
                // If empty, display disconnect dialog
                // Else display dialog that shows devices with option to set as input/output
                // Have indicators for output/input connections. Maybe like one little connected
                // indicator icon for each connection. Clicking opens up the connection dialog again
                // showing all the connected devices and whether they are set to in/out
                // That should probably be part of this fragment's layout.
                Log.d(TAG, "Devices = ${it.joinToString(" ")}, opening input if not empty")
                if (connectedDevices.isNotEmpty()) {
                    midiSession.midiManager.openInputDevice(connectedDevices.first())
                }
            }
            inputDeviceOpened.observe(viewLifecycleOwner) {
                Log.d(TAG, "InputDeviceOpened = $it, opening output if true")
                if (it) {
                    midiSession.midiManager.openOutputDevice(connectedDevices.first())
                }
            }
            outputDeviceOpened.observe(viewLifecycleOwner) {
                Log.d(TAG, "OutputDeviceOpened = $it")
            }
        }
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    companion object {
        private const val TAG = "MainFragment"
    }

}