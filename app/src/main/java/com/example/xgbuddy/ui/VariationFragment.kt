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
import com.example.xgbuddy.data.xg.EffectControlParameter
import com.example.xgbuddy.data.xg.EffectParameterData
import com.example.xgbuddy.data.xg.Variation
import com.example.xgbuddy.data.xg.VariationType
import com.example.xgbuddy.ui.custom.ControlViewGroup
import com.example.xgbuddy.util.EnumFinder.findBy
import com.google.android.material.switchmaterial.SwitchMaterial

class VariationFragment : ControlBaseFragment(), OnItemSelectedListener {

    private val midiViewModel: MidiViewModel by activityViewModels()

    private var wasSpinnerTouched = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val v = layoutInflater.inflate(R.layout.fragment_variation, container, false)
        findViews(v)
        setupSpinners()
        setupSwitch()
        initControlGroups()
        return v
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupSpinners() {
        val touchListener = View.OnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                wasSpinnerTouched = true
            }
            false
        }
        spVariPart.apply {
            setOnTouchListener(touchListener)
            onItemSelectedListener = this@VariationFragment
            setSelection(Variation.partValues.indexOf(midiViewModel.variation.part))
        }
        spVariType.apply {
            setOnTouchListener(touchListener)
            onItemSelectedListener = this@VariationFragment
            adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                VariationType.values().map { getString(it.nameRes) }
            ).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
            setSelection(
                VariationType.values()
                    .indexOf(VariationType::nameRes findBy midiViewModel.variation.nameRes)
            )
        }
    }

    private fun setupSwitch() {
        swVariConnect.isChecked = midiViewModel.variation.connection == 1.toByte()
        swVariConnect.setOnClickListener {
            val isChecked = (it as SwitchMaterial).isChecked
            midiViewModel.variation.connection = if (isChecked) 1.toByte() else 0.toByte()
            // Todo: Send message
        }
    }

    private fun initControlGroups() {
        initControlGroup(
            cvgVari,
            shouldStartExpanded = true,
            isRealtime = false,
            extraChildren = llVariExtras
        )
        initControlGroup(
            cvgVariCtrl,
            isRealtime = false
        )
        updateViews(midiViewModel.variation)
    }

    private fun updateViews(variation: Variation) {
        controlGroups.forEach {
            it.updateViews(variation)
        }
    }

    override fun initParameter(paramId: Int): ControlParameter {
        val vmVari = midiViewModel.variation
        val effectParamData = EffectParameterData::resId findBy paramId
        if (effectParamData!!.ordinal > EffectParameterData.VARIATION_PARAM_16.ordinal) {
            val value = vmVari.getPropertyValue(effectParamData.reflectedField)
            return EffectControlParameter(
                effectParamData,
                null,
                value.toInt(),
                effectParamData.default.toInt()
            )
        } else {
            val value = vmVari.getPropertyValue(effectParamData.reflectedBigField)
            val variParam = vmVari.parameterList?.get(effectParamData)
            val defaultValue = vmVari.defaultValues!![effectParamData.paramIndex!!]
            return EffectControlParameter(
                effectParamData,
                variParam,
                value,
                defaultValue
            )
        }
    }

    override fun onParameterChanged(controlParameter: ControlParameter, isTouching: Boolean) {
        val param = EffectParameterData::addrLo findBy controlParameter.addr.toByte()
        val variation = midiViewModel.variation
        if (param!!.ordinal > EffectParameterData.VARIATION_PARAM_16.ordinal) {
            variation.setProperty(param.reflectedField, controlParameter.value.toByte())
        } else {
            midiViewModel.variation.setProperty(
                param.reflectedBigField,
                controlParameter.value
            )
        }
        // No need to update viewmodel explicitly I don't think?
        // Todo: Send message
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (wasSpinnerTouched) {
            if (view == spVariPart) {
                midiViewModel.variation.part = Variation.partValues[position]
                // Todo: send message
            } else {
                midiViewModel.variation.variationType = VariationType.values()[position]
                updateViews(midiViewModel.variation)
                // TOdo: send message
            }
        }
        wasSpinnerTouched = false
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        wasSpinnerTouched = false
    }

    private fun findViews(v: View) {
        spVariType = v.findViewById(R.id.spVariType)
        spVariPart = v.findViewById(R.id.spVariPart)
        swVariConnect = v.findViewById(R.id.swVariConnect)
        cvgVari = v.findViewById(R.id.cvgVari)
        llVariExtras = v.findViewById(R.id.llVariExtras)
        cvgVariCtrl = v.findViewById(R.id.cvgVariCtrl)
    }

    private lateinit var spVariType: Spinner
    private lateinit var spVariPart: Spinner
    private lateinit var swVariConnect: SwitchMaterial
    private lateinit var cvgVari: ControlViewGroup
    private lateinit var llVariExtras: LinearLayout
    private lateinit var cvgVariCtrl: ControlViewGroup
}