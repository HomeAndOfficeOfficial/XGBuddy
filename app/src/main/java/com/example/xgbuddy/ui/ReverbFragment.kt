package com.example.xgbuddy.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
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

    private var wasSpinnerTouched = false

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

    @SuppressLint("ClickableViewAccessibility")
    private fun setupSpinner() {
        spReverbType.apply {
            setOnTouchListener { _, event ->
                if (event?.action == MotionEvent.ACTION_DOWN) {
                    wasSpinnerTouched = true
                }
                false
            }
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

    private fun updateViews(reverb: Reverb) {
        controlGroups.forEach {
            it.updateViews(reverb)
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
        val param = EffectParameterData::addrLo findBy controlParameter.addr.toByte()
        midiViewModel.reverb.value!!.setProperty(param!!.reflectedField, controlParameter.value.toByte())
        // No need to update viewmodel explicitly I don't think?
        // Todo: Send message
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (wasSpinnerTouched) {
            val preset = ReverbType.values()[position]
            val updateReverb = Reverb(preset)
            midiViewModel.reverb.value = updateReverb
            updateViews(updateReverb)
            // Todo: send message
        }
        wasSpinnerTouched = false
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        wasSpinnerTouched = false
    }

    private fun findViews(v: View) {
        spReverbType = v.findViewById(R.id.spReverbType)
        cvgReverb = v.findViewById(R.id.cvgReverb)
        llReverbExtras = v.findViewById(R.id.llReverbExtras)
    }

    private lateinit var spReverbType: Spinner
    private lateinit var cvgReverb: ControlViewGroup
    private lateinit var llReverbExtras: LinearLayout

}