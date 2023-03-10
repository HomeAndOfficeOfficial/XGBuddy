package com.example.xgbuddy

import android.animation.ValueAnimator
import android.content.pm.PackageManager
import android.graphics.drawable.AnimatedVectorDrawable
import android.media.midi.MidiDeviceInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ImageView
import androidx.core.view.MenuProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var midiSession: MidiSession

    private var connectedDevices: Set<MidiDeviceInfo> = setOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (packageManager.hasSystemFeature(PackageManager.FEATURE_MIDI)) {
            setContentView(R.layout.activity_main)
            setupOptionsMenu()
        } else {
            displayNoMidiCompatScreen()
        }
    }

    override fun onStart() {
        super.onStart()
        midiSession.apply {
            connectedDeviceList.observe(this@MainActivity) {
                connectedDevices = it
                Log.d(TAG, "Devices = ${it.joinToString(" ")}")

                // Open connection status fragment if no inputs or outputs are open
                if (connectedDevices.isEmpty() || (midiSession.getInputDevices()
                        .isEmpty() && midiSession.getOutputDevices().isEmpty())
                ) {
                    if (supportFragmentManager.findFragmentByTag(ConnectionStatusFragment.TAG) == null) {
                        ConnectionStatusFragment().show(
                            supportFragmentManager,
                            ConnectionStatusFragment.TAG
                        )
                    }
                }
            }
            inputDeviceOpened.observe(this@MainActivity) {
                Log.d(TAG, "InputDeviceOpened = $it")
            }
            outputDeviceOpened.observe(this@MainActivity) {
                Log.d(TAG, "OutputDeviceOpened = $it")
            }
        }
    }

    private fun setupOptionsMenu() {
        addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.main_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.qs_capture -> {
                        if (supportFragmentManager.findFragmentByTag(QS300PresetCaptureFragment.TAG) == null) {
                            QS300PresetCaptureFragment().show(
                                supportFragmentManager,
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

    private fun displayNoMidiCompatScreen() {
        setContentView(R.layout.activity_main_no_midi)
        findViewById<ImageView?>(R.id.leftHand).apply { (drawable as AnimatedVectorDrawable).start() }
        findViewById<ImageView?>(R.id.rightHand).apply { (drawable as AnimatedVectorDrawable).start() }
        val head = findViewById<ImageView>(R.id.head)
        ValueAnimator.ofFloat(-15f, 15f).apply {
            duration = 750
            addUpdateListener {
                head.rotationY = it.animatedValue as Float
            }
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE
            start()
        }
    }

    companion object {
        const val TAG = "MainActivity"
    }
}