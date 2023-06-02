package com.example.xgbuddy.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.xgbuddy.MidiSession
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
open class MidiBaseFragment : Fragment() {
    @Inject
    lateinit var midiSession: MidiSession
    val midiViewModel: MidiViewModel by activityViewModels()
}