package com.example.xgbuddy.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.xgbuddy.R
import com.example.xgbuddy.data.ControlParameter
import com.example.xgbuddy.data.xg.*
import com.example.xgbuddy.databinding.FragmentVariationBinding
import com.example.xgbuddy.util.EnumFinder.findBy
import com.example.xgbuddy.util.MidiMessageUtility

class VariationFragment : EffectEditFragment<FragmentVariationBinding>() {

    override val effectType: Int = VARIATION
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentVariationBinding =
        FragmentVariationBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToggleGroup()
    }

    private fun setupToggleGroup() {
        binding!!.bgVariConnect.apply {
            val connectionMode = midiViewModel.variation.connection
            changeConnectionControls(connectionMode)
            check(
                if (connectionMode == 0.toByte())
                    R.id.toggleInsert
                else
                    R.id.toggleSystem
            )
            addOnButtonCheckedListener { _, checkedId, isChecked ->
                if (isChecked) {
                    Log.d("VariationFragment", "Check listenere")
                    val connectionValue =
                        if (checkedId == R.id.toggleSystem) 1.toByte() else 0.toByte()
                    changeConnectionControls(connectionValue)
                    midiViewModel.variation.connection = connectionValue
                    midiSession.send(
                        MidiMessageUtility.getEffectParamChange(
                            EffectParameterData.VARIATION_CONNECTION,
                            connectionValue.toInt()
                        )
                    )
                }
            }
        }
    }

    /**
     * A lot of hardcoding going on here. Until I have a better system in place for enabling and
     * disabling controls, I think this is okay for now. Eventually I would like to do this a little
     * more gracefully - like when XG mode, QS300 mode, etc can be disabled in settings, some things
     * will have to be disabled so it's clear what can and can't be done.
     */
    private fun changeConnectionControls(connection: Byte) {
        val isInSystemMode = connection == 1.toByte()
        binding!!.spVariPart.isEnabled = !isInSystemMode
        val controls = controlGroups[0].controlViewMap
        controls.apply {
            get(EffectParameterData.SEND_VARI_TO_CHOR.addrLo.toUByte())?.izEnabled = isInSystemMode
            get(EffectParameterData.SEND_VARI_TO_REV.addrLo.toUByte())?.izEnabled = isInSystemMode
            get(EffectParameterData.VARIATION_RETURN.addrLo.toUByte())?.izEnabled = isInSystemMode
            get(EffectParameterData.VARIATION_PAN.addrLo.toUByte())?.izEnabled = isInSystemMode
        }
    }

    override fun setupSpinner() {
        binding!!.apply {
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

    }

    override fun initControlGroups() {
        binding!!.apply {
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
    }

    override fun initParameter(paramId: Int): ControlParameter {
        val vmVari = midiViewModel.variation
        val effectParamData = EffectParameterData::resId findBy paramId
        if (effectParamData!!.ordinal > EffectParameterData.VARIATION_PARAM_16.ordinal) {
            val value = vmVari.getPropertyValue(effectParamData.reflectedField)
            return ControlParameter(
                effectParamData,
                null,
                value.toInt(),
                effectParamData.default.toInt()
            )
        } else {
            val value = vmVari.getPropertyValue(effectParamData.reflectedBigField)
            val variParam = vmVari.parameterList?.get(effectParamData)
            val defaultValue = vmVari.defaultValues!![effectParamData.paramIndex!!]
            return ControlParameter(
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
        return if (spinner == binding!!.spVariPart) {
            val partValue = Variation.partValues[index]
            midiViewModel.variation.part = partValue
            midiSession.send(
                MidiMessageUtility.getEffectParamChange(
                    EffectParameterData.VARIATION_PART, partValue.toInt()
                )
            )
            false
        } else {
            midiViewModel.variation.variationType = VariationType.values()[index]
            true
        }
    }
}