package com.yamahaw.xgbuddy

import android.animation.ValueAnimator
import android.content.Intent
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
import androidx.activity.viewModels
import androidx.core.view.MenuProvider
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.yamahaw.xgbuddy.data.gm.MidiPart
import com.yamahaw.xgbuddy.ui.*
import com.yamahaw.xgbuddy.ui.filebrowser.FileBrowserFragment
import com.yamahaw.xgbuddy.viewmodel.QS300ViewModel
import com.google.android.material.navigationrail.NavigationRailView
import com.google.gson.Gson
import com.yamahaw.xgbuddy.service.MidiService
import com.yamahaw.xgbuddy.service.MidiServiceConnection
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var midiSession: MidiSession
    private val midiServiceConnection by lazy {
        MidiServiceConnection(this)
    }
    private val qsNoteDuplicator: QSPartNoteDuplicator by lazy {
        QSPartNoteDuplicator(midiViewModel, midiSession)
    }

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
            bindMidiService()
            setupOptionsMenu()
            setupNavigation()
            midiViewModel.apply {
                selectedChannel.observe(this@MainActivity) {
                    showOrHideMenuItems()
                }
                channels.observe(this@MainActivity) {
                    showOrHideMenuItems()
                }
                setupResetFlag.observe(this@MainActivity) {
                    if (it) {
                        if (supportFragmentManager.findFragmentByTag(PartsFragment.TAG) == null) {
                            navHost.navController.navigate(R.id.partsFragment)
                        }
                        setupResetFlag.value = false
                    }
                }
            }
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
                registerForMidiCallbacks(qsNoteDuplicator)
            }
            qs300ViewModel.presets // Initialize presets now so app doesn't hang up later
        } else {
            displayNoMidiCompatScreen()
        }
    }

    override fun onResume() {
        super.onResume()
        enableFullscreenMode()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(Intent(this, MidiService::class.java))
    }

    private fun bindMidiService() {
        try {
            bindService(
                Intent(this, MidiService::class.java),
                midiServiceConnection,
                BIND_AUTO_CREATE
            )
        } catch (e: RuntimeException) {
            Log.d(TAG, "Couldn't do it: ${e.message}")
            startForegroundService(Intent(this, MidiService::class.java))
        }
    }

    private fun enableFullscreenMode() {
        val windowInsetsController =
            WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        windowInsetsController.hide(WindowInsetsCompat.Type.navigationBars())
    }

    private fun setupOptionsMenu() {
        addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.main_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
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
                        openFileBrowser(FileBrowserFragment.WRITE)
                        true
                    }
                    R.id.open_setup -> {
                        openFileBrowser(FileBrowserFragment.READ)
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

    private fun openFileBrowser(mode: Int) {
        val setupString = if (mode == FileBrowserFragment.WRITE)
            Gson().toJson(midiViewModel.toSetupModel())
        else
            ""
        if (supportFragmentManager.findFragmentByTag(FileBrowserFragment.TAG) == null) {
            FileBrowserFragment.newInstance(
                mode,
                setupString
            ).show(supportFragmentManager, FileBrowserFragment.TAG)
        }
    }

    companion object {
        const val TAG = "MainActivity"
    }
}