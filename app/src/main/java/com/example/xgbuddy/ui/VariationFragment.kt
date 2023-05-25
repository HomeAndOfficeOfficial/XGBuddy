package com.example.xgbuddy.ui

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import com.example.xgbuddy.R
import com.example.xgbuddy.data.ControlParameter
import com.example.xgbuddy.data.xg.*
import com.example.xgbuddy.ui.custom.ControlViewGroup
import com.example.xgbuddy.util.EnumFinder.findBy
import com.google.android.material.switchmaterial.SwitchMaterial

class VariationFragment : EffectEditFragment() {

    override val effectType: Int = VARIATION

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSwitch()
    }

    private fun setupSwitch() {
        swVariConnect.isChecked = midiViewModel.variation.connection == 1.toByte()
        swVariConnect.setOnClickListener {
            val isChecked = (it as SwitchMaterial).isChecked
            midiViewModel.variation.connection = if (isChecked) 1.toByte() else 0.toByte()
            // Todo: Send message
        }
    }

    override fun setupSpinner() {
        spVariPart.apply {
            setOnTouchListener(spinnerTouchListener)
            onItemSelectedListener = this@VariationFragment
            setSelection(Variation.partValues.indexOf(midiViewModel.variation.part))
        }
        spVariType.apply {
            setOnTouchListener(spinnerTouchListener)
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

    override fun initControlGroups() {
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

    override fun getViewModelEffect(): Effect = midiViewModel.variation

    override fun isBigParameter(effectParameterData: EffectParameterData): Boolean =
        effectParameterData.ordinal <= EffectParameterData.VARIATION_PARAM_16.ordinal

    override fun onEffectPresetSelected(index: Int, spinner: Spinner): Boolean {
        return if (spinner == spVariPart) {
            midiViewModel.variation.part = Variation.partValues[index]
            false
        } else {
            midiViewModel.variation.variationType = VariationType.values()[index]
            true
        }
    }

    override fun findViews(root: View) {
        spVariType = root.findViewById(R.id.spVariType)
        spVariPart = root.findViewById(R.id.spVariPart)
        swVariConnect = root.findViewById(R.id.swVariConnect)
        cvgVari = root.findViewById(R.id.cvgVari)
        llVariExtras = root.findViewById(R.id.llVariExtras)
        cvgVariCtrl = root.findViewById(R.id.cvgVariCtrl)
    }

    private lateinit var spVariType: Spinner
    private lateinit var spVariPart: Spinner
    private lateinit var swVariConnect: SwitchMaterial
    private lateinit var cvgVari: ControlViewGroup
    private lateinit var llVariExtras: LinearLayout
    private lateinit var cvgVariCtrl: ControlViewGroup
}