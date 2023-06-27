package com.example.xgbuddy

import android.animation.ValueAnimator
import android.content.pm.PackageManager
import android.graphics.drawable.AnimatedVectorDrawable
import android.media.midi.MidiDeviceInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.core.view.MenuProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.xgbuddy.data.gm.MidiPart
import com.example.xgbuddy.ui.ConnectionStatusFragment
import com.example.xgbuddy.ui.FileBrowserFragment
import com.example.xgbuddy.ui.MidiViewModel
import com.example.xgbuddy.ui.QS300PresetCaptureFragment
import com.example.xgbuddy.viewmodel.QS300ViewModel
import com.google.android.material.navigationrail.NavigationRailView
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * I'm thinking it may be possible to have QS300 and XG side by side in this fragment. I'm thinking
 * about how the Parts fragment will be laid out.
 *
 * 1. Parts
 *
 *      I think the fragment could be split into two parts. On the left, is a list of midi channels 1-16
 *      and on the right will be the controls for the selected channel. The channels could contain
 *      either an XG voice or a QS300 voice.
 *
 *      The controls panel on the right will contain all the controls related to an XG channel since
 *      these can be applied whether the channel is dedicated to a QS300 voice or a regular XG voice.
 *
 *      When a channel is selected, if the channel contains a QS300 voice, a new tab will be added to the
 *      tab group - this will be for the VoiceEditFragment.
 *
 *      For QS300 voices, I'm not quite sure how to handle the stored user voices and navigating between
 *      them in the app. I think I'll save that for later.
 *
 * 2. Effects
 *
 *      Another tab will be the variation tab for all the effects controls. Will need to do some
 *      more research on how the data is organized for these to figure out the best way to organize
 *      the controls.
 *
 *      Chorus and reverb controls may be located here as well.
 *
 * 3. Midi Settings
 *
 *      I think here there will be a list of things where you can enable/disable things like
 *      pitch wheel, mod wheel, etc. Refer to the Mountain Utilities software for all the different
 *      options
 *
 * 4. Input/Output meters?
 *
 *      This is not crucial but may be a nice addition at some point.
 *
 * 5. Keyboard
 *
 *      Have a virtual keyboard for easy testing of sounds?
 *
 * That's probably all I need for tabs.
 *
 * In addition to the tab layout, this fragment should perhaps display the name of the setup in the
 * title bar, along with save/open/home icons. There should also be additional option menu items to
 * open connection status fragment (to enable/disable inputs/outputs), and settings (tbd), and all
 * note off.
 *
 * Going forward:
 *
 *      I can probably get the main layout in place and stubs for the fragments that will make up
 *      the session. I think to really begin, I will need to have the remaining data structures in
 *      place. QS300 stuff is fully defined i think, so I should try to define XG stuff next, then
 *      try to fit those into a General Midi model. The data structures will probably inform how the
 *      views are implemented so I think that will be necessary before doing any actual work on
 *      this.
 *
 *      To start, I'll add the basic layouts.
 */

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var midiSession: MidiSession

    private val navHost: NavHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
    }
    private val midiViewModel: MidiViewModel by viewModels()
    private val qs300ViewModel: QS300ViewModel by viewModels()
    private var connectedDevices: Set<MidiDeviceInfo> = setOf()

    private lateinit var navRail: NavigationRailView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (packageManager.hasSystemFeature(PackageManager.FEATURE_MIDI)) {
            setContentView(R.layout.activity_main)
            setupOptionsMenu()
            setupNavigation()
            midiViewModel.selectedChannel.observe(this) {
                showOrHideMenuItems()
            }
            midiViewModel.channels.observe(this) {
                showOrHideMenuItems()
            }
            qs300ViewModel.presets // Initialize presets now so app doesn't hang up later
        } else {
            displayNoMidiCompatScreen()
        }
    }


    override fun onStart() {
        super.onStart()
        midiSession.apply {
            connectedDeviceList.observe(this@MainActivity) {
                connectedDevices = it
                if (connectedDevices.isEmpty() || (midiSession.getInputDevices()
                        .isEmpty() && midiSession.getOutputDevices().isEmpty())
                ) {
                    showConnectionStatusDialog()
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
                    R.id.connect_status -> {
                        showConnectionStatusDialog()
                        true
                    }
                    R.id.save_setup -> {
                        Log.d(
                            TAG, "QS Map: ${
                                midiViewModel.qsPartMap.entries.joinToString(", ") {
                                    "{${it.key}: ${it.value.name}}"
                                }
                            }"
                        )
                        if (supportFragmentManager.findFragmentByTag(FileBrowserFragment.TAG) == null) {
                            FileBrowserFragment.newInstance(
                                FileBrowserFragment.WRITE,
                                Gson().toJson(midiViewModel.toSetupModel())
                            ).show(supportFragmentManager, FileBrowserFragment.TAG)
                        }
                        true
                    }
                    else -> false
                }
            }
        })
    }

    private fun setupNavigation() {
        val navController = navHost.navController
        navRail = findViewById(R.id.navRail)
        NavigationUI.setupWithNavController(navRail, navController)
    }

    private fun showOrHideMenuItems() {
        val selectedPart = midiViewModel.channels.value!![midiViewModel.selectedChannel.value!!]
        navRail.menu.findItem(R.id.voiceEditFragment).isVisible =
            selectedPart.voiceType == MidiPart.VoiceType.QS300
        navRail.menu.findItem(R.id.drumEditFragment).isVisible =
            selectedPart.voiceType == MidiPart.VoiceType.DRUM
    }

    private fun showConnectionStatusDialog() {
        if (supportFragmentManager.findFragmentByTag(ConnectionStatusFragment.TAG) == null) {
            ConnectionStatusFragment().show(
                supportFragmentManager,
                ConnectionStatusFragment.TAG
            )
        }
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
        const val ARG_RESUMED = "argResumed"
    }
}