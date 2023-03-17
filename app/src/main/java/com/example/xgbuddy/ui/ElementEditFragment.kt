package com.example.xgbuddy.ui

import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.xgbuddy.MidiSession
import com.example.xgbuddy.R
import com.example.xgbuddy.data.ControlParameter
import com.example.xgbuddy.data.QS300ControlParameter
import com.example.xgbuddy.data.QS300ElementParameter
import com.example.xgbuddy.data.QS300Preset
import com.example.xgbuddy.ui.custom.ControlViewGroup
import com.example.xgbuddy.ui.custom.ParameterControlView
import com.example.xgbuddy.ui.custom.SliderControlView
import com.example.xgbuddy.util.EnumFinder.findBy
import com.example.xgbuddy.util.MidiStoredDataUtility
import com.example.xgbuddy.viewmodel.QS300ViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ElementEditFragment : Fragment(), ParameterControlView.OnParameterChangedListener {

    // TODO: Add "MidiBaseFragment" to avoid having to inject midiSession every time

    @Inject
    lateinit var midiSession: MidiSession

    @Inject
    lateinit var dataUtility: MidiStoredDataUtility

    private lateinit var presets: List<QS300Preset>

    //TODO: Hook up receiver listener to update control view

    private val viewModel: QS300ViewModel by activityViewModels()
//    private lateinit var binding: FragmentElementEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presets = dataUtility.getQS300Presets()
        viewModel.preset.value = presets[1]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        binding = FragmentElementEditBinding.inflate(layoutInflater)
        // Will need to do this for each control group'
        val v = layoutInflater.inflate(R.layout.fragment_element_edit, container, false)
        findViews(v)
        cvgLfo.apply {
            controlItemIds.forEach {
                addControlView(SliderControlView(requireContext()).apply {
                    controlParameter = initElementParam(it)
                    listener = this@ElementEditFragment
                })
            }
        }

        // Then also need to manually setup the special cases
        // Will have to assess what refactoring will be needed once I add all the different groups
        // Might become a big mess.
        clLfoExtras.apply {
            for (i in 0 until childCount) {
                (getChildAt(i) as ParameterControlView).apply {
                    controlParameter = initElementParam(paramId)
                    listener = this@ElementEditFragment
                    cvgLfo.mapUngroupedView(this)
                }

            }
        }
        viewModel.preset.observe(viewLifecycleOwner) {
            it?.let {
                // Will need to keep track of what voice and what element is currently displayed.
                // Maybe if preset changes, just switch to zero by default?
                cvgLfo.updateViews(it.voices[0].elements[0])
            }
        }
        return v
    }

    private fun initElementParam(descriptionRes: Int): QS300ControlParameter {
        val param = QS300ElementParameter::descriptionRes findBy descriptionRes
        Log.d(TAG, "Element Param: ${param?.name}, addr: ${param?.baseAddress}")
        return QS300ControlParameter(
            param!!,
            viewModel.preset.value!!.voices[0].elements[0].getPropertyValue(param.reflectedField)
        )
    }

    override fun onParameterChanged(controlParameter: ControlParameter) {
        Log.d(TAG, "Parameter changed: ${controlParameter.name}: ${controlParameter.value}")
    }

    companion object {
        const val TAG = "ElementEditFragment"
    }

    private fun findViews(v: View) {
        cvgLfo = v.findViewById(R.id.cvgLfo)
        clLfoExtras = v.findViewById(R.id.lfoExtras)
    }

    private lateinit var cvgLfo: ControlViewGroup
    private lateinit var clLfoExtras: ConstraintLayout
}