package com.example.xgbuddy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.xgbuddy.MidiSession
import com.example.xgbuddy.data.ControlParameter
import com.example.xgbuddy.databinding.FragmentElementEditBinding
import com.example.xgbuddy.ui.custom.ParameterControlView
import com.example.xgbuddy.viewmodel.QS300ViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ElementEditFragment() : Fragment() {

    @Inject
    lateinit var midiSession: MidiSession

    //TODO: Hook up receiver listener to update control view

    private val viewModel: QS300ViewModel by activityViewModels()
    private lateinit var binding: FragmentElementEditBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentElementEditBinding.inflate(layoutInflater)
        viewModel.preset.observe(viewLifecycleOwner) {
            it?.let {
                val elementLevel = it.voices[0].elements[0].elementLvl
                binding.pcvTest.label = "$elementLevel"
                binding.pcvTest.value = elementLevel.toFloat()
            }
        }
        binding.pcvTest.listener =
            ParameterControlView.OnParameterChangedListener { controlParameter ->
                midiSession.send(controlParameter.getParamChangeMessage())
            }
        binding.bNextParam.setOnClickListener {
            viewModel.nextParameter()
        }
        return binding.root
    }
}