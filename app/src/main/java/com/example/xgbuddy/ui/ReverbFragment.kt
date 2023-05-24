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
        updateViews(midiViewModel.reverb)
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

            // This is awful but I just need to get these fx fragments working before I can figure
            // out how to clean them up.
            setSelection(
                ReverbType.values().indexOf(ReverbType::nameRes findBy midiViewModel.reverb.nameRes)
            )
        }
    }

    private fun updateViews(reverb: Reverb) {
        controlGroups.forEach {
            it.updateViews(reverb)
        }
    }

    override fun initParameter(paramId: Int): ControlParameter? {
        val vmReverb = midiViewModel.reverb
        val effectParamData = EffectParameterData::resId findBy paramId
        if (effectParamData == EffectParameterData.REVERB_RETURN || effectParamData == EffectParameterData.REVERB_PAN) {
            val value = vmReverb.getPropertyValue(effectParamData.reflectedField)
            return EffectControlParameter(
                effectParamData,
                null,
                value.toInt(),
                effectParamData.default
            )
        } else {
            val value = vmReverb.getPropertyValue(effectParamData!!.reflectedBigField)
            val reverbParam = vmReverb.parameterList?.get(effectParamData) ?: return null
            val defaultValue = vmReverb.defaultValues!![effectParamData.paramIndex!!]
            return EffectControlParameter(
                effectParamData,
                reverbParam,
                value,
                defaultValue.toByte()
            )
        }
    }

    override fun onParameterChanged(controlParameter: ControlParameter, isTouching: Boolean) {
        val param = EffectParameterData::addrLo findBy controlParameter.addr.toByte()
        val reverb = midiViewModel.reverb
        if (param == EffectParameterData.REVERB_RETURN || param == EffectParameterData.REVERB_PAN) {
            reverb.setProperty(param.reflectedField, controlParameter.value.toByte())
        } else {
            midiViewModel.reverb.setProperty(
                param!!.reflectedBigField,
                controlParameter.value
            )
        }
        // No need to update viewmodel explicitly I don't think?
        // Todo: Send message
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (wasSpinnerTouched) {
            val preset = ReverbType.values()[position]
            val updateReverb = Reverb(preset)
            midiViewModel.reverb = updateReverb
            // Actually instead of updating view, we have to repopulate everything
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