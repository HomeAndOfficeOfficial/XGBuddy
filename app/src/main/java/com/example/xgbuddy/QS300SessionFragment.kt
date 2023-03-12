package com.example.xgbuddy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.xgbuddy.data.QS300Preset
import com.example.xgbuddy.databinding.FragmentQS300SessionBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class QS300SessionFragment : Fragment() {

    @Inject
    lateinit var midiSession: MidiSession

    private lateinit var binding: FragmentQS300SessionBinding
    private var presets = mutableListOf<QS300Preset>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        buildPresetList()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQS300SessionBinding.inflate(layoutInflater)
        addPresetsToSpinner()
        return binding.root
    }

    private fun buildPresetList() {

    }

    private fun addPresetsToSpinner() {

    }
}