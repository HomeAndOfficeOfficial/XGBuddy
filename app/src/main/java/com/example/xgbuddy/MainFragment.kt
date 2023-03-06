package com.example.xgbuddy

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
                Log.d(TAG, "Devices = ${it.joinToString(" ")}")

                // Open connection status fragment if no inputs or outputs are open
                if (connectedDevices.isEmpty() || (midiSession.getInputDevices()
                        .isEmpty() && midiSession.getOutputDevices().isEmpty())
                ) {
                    if (childFragmentManager.findFragmentByTag(ConnectionStatusFragment.TAG) == null) {
                        ConnectionStatusFragment().show(
                            childFragmentManager,
                            ConnectionStatusFragment.TAG
                        )
                    }
                }
            }
            inputDeviceOpened.observe(viewLifecycleOwner) {
                Log.d(TAG, "InputDeviceOpened = $it")
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