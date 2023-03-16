package com.example.xgbuddy.ui

import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.xgbuddy.MidiSession
import com.example.xgbuddy.data.ControlParameter
import com.example.xgbuddy.data.QS300ControlParameter
import com.example.xgbuddy.data.QS300ElementParameter
import com.example.xgbuddy.databinding.FragmentElementEditBinding
import com.example.xgbuddy.ui.custom.ParameterControlView
import com.example.xgbuddy.ui.custom.SliderControlView
import com.example.xgbuddy.util.EnumFinder.findBy
import com.example.xgbuddy.viewmodel.QS300ViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ElementEditFragment : Fragment(), ParameterControlView.OnParameterChangedListener {

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

        // Will need to do this for each control group
        binding.cvgLfo.apply {
            controlItemIds.forEach {
                val param = QS300ElementParameter::descriptionRes findBy it
                val paramData = QS300ControlParameter(param!!, param.default)
                // These should actually get their values from the viewModel's preset.
                addControlView(SliderControlView(requireContext()).apply {
                    controlParameter = paramData
                })
            }
        }

        // Then also need to manually setup the special cases
        /**
         * Consider adding an optional controlItemId attr to ControlView,
         * then it would be possible to iterate through all views in each
         * control group and set them up like above
         */
        binding.cvLfoWave.apply {
            controlParameter = QS300ControlParameter(QS300ElementParameter.LFO_WAVE, 0)
        }
        binding.cvLfoPhaseInit.apply {
            controlParameter = QS300ControlParameter(QS300ElementParameter.LFO_PHASE_INIT, 0)
        }
        viewModel.preset.observe(viewLifecycleOwner) {
            it?.let {
//                val elementLevel = it.voices[0].elements[0].elementLvl
//                binding.pcvTest.label = "$elementLevel"
//                binding.pcvTest.value = elementLevel.toFloat()
            }
        }
//        binding.pcvTest.listener =
//            ParameterControlView.OnParameterChangedListener { controlParameter ->
//                midiSession.send(controlParameter.getParamChangeMessage())
//            }
//        binding.bNextParam.setOnClickListener {
//            viewModel.nextParameter()
//        }
        return binding.root
    }

    override fun onParameterChanged(controlParameter: ControlParameter) {
        Log.d(TAG, "Parameter changed: ${controlParameter.name}: ${controlParameter.value}")
    }

    companion object {
        const val TAG = "ElementEditFragment"
    }
}