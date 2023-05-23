package com.example.xgbuddy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import androidx.fragment.app.activityViewModels
import com.example.xgbuddy.R
import com.example.xgbuddy.data.ControlParameter
import com.example.xgbuddy.data.xg.*
import com.example.xgbuddy.ui.custom.ControlViewGroup
import com.example.xgbuddy.util.EnumFinder.findBy

class ReverbFragment : ControlBaseFragment(), OnItemSelectedListener {

    private val midiViewModel: MidiViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val v = layoutInflater.inflate(R.layout.fragment_reverb, container, false)
        findViews(v)
        setupSpinner()
        initControlGroup(
            cvgReverb,
            isInteractive = false,
            shouldStartExpanded = true,
            isRealtime = false,
            extraChildren = llReverbExtras
        )
        return v
    }

    private fun setupSpinner() {
        spReverbType.apply {
            onItemSelectedListener = this@ReverbFragment
            val reverbNames: List<String> = ReverbType.values().map { it.name }
            adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                reverbNames
            ).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
        }
    }

    override fun initParameter(paramId: Int): ControlParameter {
        val vmReverb = midiViewModel.reverb.value!!
        val effectParamData = EffectParameterData::resId findBy paramId
        val value = vmReverb.getPropertyValue(effectParamData!!.reflectedField)
        val reverbParam = vmReverb.parameterList?.get(effectParamData)!!
        val defaultValue = vmReverb.defaultValues!![effectParamData.paramIndex!!]
        return EffectControlParameter(effectParamData, reverbParam, value, defaultValue.toByte())
    }

    override fun onParameterChanged(controlParameter: ControlParameter, isTouching: Boolean) {
        TODO("Not yet implemented")
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val preset = ReverbType.values()[position]
        midiViewModel.reverb.value = Reverb(preset)
        // Todo: send message, update views
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    private fun findViews(v: View) {
        spReverbType = v.findViewById(R.id.spReverbType)
        cvgReverb = v.findViewById(R.id.cvgReverb)
        llReverbExtras = v.findViewById(R.id.llReverbExtras)
    }

    private lateinit var spReverbType: Spinner
    private lateinit var cvgReverb: ControlViewGroup
    private lateinit var llReverbExtras: LinearLayout

}