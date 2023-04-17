package com.example.xgbuddy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.xgbuddy.databinding.FragmentVoiceSelectionDialogBinding

class VoiceSelectionDialogFragment : DialogFragment() {

    private val midiViewModel: MidiViewModel by activityViewModels()
    private val binding: FragmentVoiceSelectionDialogBinding by lazy {
        FragmentVoiceSelectionDialogBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }
}