package com.example.xgbuddy

import android.media.midi.MidiDeviceInfo
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupOptionsMenu()
    }

    private fun setupOptionsMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.main_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.qs_capture -> {
                        if (childFragmentManager.findFragmentByTag(QS300PresetCaptureFragment.TAG) == null) {
                            QS300PresetCaptureFragment().show(
                                childFragmentManager,
                                QS300PresetCaptureFragment.TAG
                            )
                        }
                        true
                    }
                    else -> false
                }
            }
        })
    }

    companion object {
        private const val TAG = "MainFragment"
    }

}