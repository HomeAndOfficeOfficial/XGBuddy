package com.example.xgbuddy.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.xgbuddy.data.xg.*
import com.example.xgbuddy.databinding.FragmentChorusBinding
import com.example.xgbuddy.util.EnumFinder.findBy

class ChorusFragment : EffectEditFragment<FragmentChorusBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentChorusBinding =
        FragmentChorusBinding::inflate
    override val effectType: Int = CHORUS
    override fun getViewModelEffect(): Effect = midiViewModel.chorus

    override fun initControlGroups() {
        initControlGroup(
            binding!!.cvgChorus,
            isInteractive = false,
            shouldStartExpanded = true,
            isRealtime = false,
            extraChildren = (binding as FragmentChorusBinding).llChorusExtras
        )
    }

    override fun setupSpinner() {
        binding!!.spChorusType.apply {
            setOnTouchListener(spinnerTouchListener)
            onItemSelectedListener = this@ChorusFragment
            adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                ChorusType.values().map { getString(it.nameRes) }
            ).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
            setSelection(
                ChorusType.values().indexOf(ChorusType::nameRes findBy midiViewModel.chorus.nameRes)
            )
        }
    }

    override fun isBigParameter(effectParameterData: EffectParameterData): Boolean =
        effectParameterData != EffectParameterData.CHORUS_RETURN
                && effectParameterData != EffectParameterData.CHORUS_PAN
                && effectParameterData != EffectParameterData.SEND_CHOR_TO_REV

    override fun onEffectPresetSelected(index: Int, spinner: Spinner): Boolean {
        midiViewModel.chorus.chorusType = ChorusType.values()[index]
        return true
    }
}