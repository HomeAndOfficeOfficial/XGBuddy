package com.example.xgbuddy.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import androidx.fragment.app.activityViewModels
import com.example.xgbuddy.R
import com.example.xgbuddy.data.ControlParameter
import com.example.xgbuddy.data.xg.*
import com.example.xgbuddy.ui.custom.ControlViewGroup
import com.example.xgbuddy.util.EnumFinder.findBy

class ChorusFragment : ControlBaseFragment(), AdapterView.OnItemSelectedListener {

    private val midiViewModel: MidiViewModel by activityViewModels()

    private var wasSpinnerTouched = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val v = layoutInflater.inflate(R.layout.fragment_chorus, container, false)
        findViews(v)
        setupSpinner()
        initControlGroups()
        return v
    }

    private fun initControlGroups() {
        initControlGroup(
            cvgChorus,
            isInteractive = false,
            shouldStartExpanded = true,
            isRealtime = false,
            extraChildren = llChorusExtras
        )
        updateViews(midiViewModel.chorus)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupSpinner() {
        spChorusType.apply {
            setOnTouchListener { _, event ->
                if (event?.action == MotionEvent.ACTION_DOWN) {
                    wasSpinnerTouched = true
                }
                false
            }
            onItemSelectedListener = this@ChorusFragment
            adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                ChorusType.values().map { getString(it.nameRes) }
            ).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }

            // This is awful but I just need to get these fx fragments working before I can figure
            // out how to clean them up.
            setSelection(
                ChorusType.values().indexOf(ChorusType::nameRes findBy midiViewModel.chorus.nameRes)
            )
        }
    }

    override fun initParameter(paramId: Int): ControlParameter {
        val vmChorus = midiViewModel.chorus
        val effectParamData = EffectParameterData::resId findBy paramId
        if (
            effectParamData == EffectParameterData.CHORUS_RETURN
            || effectParamData == EffectParameterData.CHORUS_PAN
            || effectParamData == EffectParameterData.SEND_CHOR_TO_REV
        ) {
            val value = vmChorus.getPropertyValue(effectParamData.reflectedField)
            return EffectControlParameter(
                effectParamData,
                null,
                value.toInt(),
                effectParamData.default.toInt()
            )
        } else {
            val value = vmChorus.getPropertyValue(effectParamData!!.reflectedBigField)
            val chorusParam = vmChorus.parameterList?.get(effectParamData)
            val defaultValue = vmChorus.defaultValues!![effectParamData.paramIndex!!]
            return EffectControlParameter(
                effectParamData,
                chorusParam,
                value,
                defaultValue
            )
        }
    }

    override fun onParameterChanged(controlParameter: ControlParameter, isTouching: Boolean) {
        val param = EffectParameterData::addrLo findBy controlParameter.addr.toByte()
        val chorus = midiViewModel.chorus
        if (
            param == EffectParameterData.CHORUS_RETURN
            || param == EffectParameterData.CHORUS_PAN
            || param == EffectParameterData.SEND_CHOR_TO_REV
        ) {
            chorus.setProperty(param.reflectedField, controlParameter.value.toByte())
        } else {
            midiViewModel.chorus.setProperty(
                param!!.reflectedBigField,
                controlParameter.value
            )
        }
        // No need to update viewmodel explicitly I don't think?
        // Todo: Send message
    }

    private fun findViews(v: View) {
        spChorusType = v.findViewById(R.id.spChorusType)
        cvgChorus = v.findViewById(R.id.cvgChorus)
        llChorusExtras = v.findViewById(R.id.llChorusExtras)
    }

    private fun updateViews(chorus: Chorus) {
        controlGroups.forEach {
            it.updateViews(chorus)
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (wasSpinnerTouched) {
            val preset = ChorusType.values()[position]
            val updateChorus = Chorus(preset)
            midiViewModel.chorus = updateChorus
            updateViews(updateChorus)
            // Todo: send message
        }
        wasSpinnerTouched = false
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        wasSpinnerTouched = false
    }

    private lateinit var spChorusType: Spinner
    private lateinit var cvgChorus: ControlViewGroup
    private lateinit var llChorusExtras: LinearLayout
}