package com.example.xgbuddy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.xgbuddy.databinding.FragmentConnectionStatusBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

// Simple dialog that shows up whenever a device is disconnected.
// MainFragment will probably be in control of showing/hiding this
// This may not even need to be its own class, but we'll see. All it does is show a little animation

@AndroidEntryPoint
class ConnectionStatusFragment: Fragment() {

    @Inject
    lateinit var midiSession: MidiSession

    private lateinit var binding: FragmentConnectionStatusBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConnectionStatusBinding.inflate(layoutInflater)

        midiSession.connectedDeviceList.observe(viewLifecycleOwner) { devices ->
            if (devices.isEmpty()) {
                binding.disconnectAlertContainer.visibility = View.VISIBLE
                binding.deviceListContainer.visibility = View.GONE
            } else {
                binding.deviceListContainer.visibility = View.VISIBLE
                binding.disconnectAlertContainer.visibility = View.GONE
            }
        }
        return binding.root
    }
}