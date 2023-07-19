package com.yamahaw.xgbuddy.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.yamahaw.xgbuddy.midi.MidiSession
import com.yamahaw.xgbuddy.viewmodel.MidiViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
open class MidiBaseFragment : Fragment() {
    @Inject
    lateinit var midiSession: MidiSession
    val midiViewModel: MidiViewModel by activityViewModels()
}