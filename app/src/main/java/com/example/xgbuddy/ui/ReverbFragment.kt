package com.example.xgbuddy.ui

import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.xgbuddy.data.xg.*
import com.example.xgbuddy.databinding.FragmentReverbBinding
import com.example.xgbuddy.util.EnumFinder.findBy

class ReverbFragment : EffectEditFragment() {

    override val effectType: Int = REVERB
    override val binding: FragmentReverbBinding by lazy {
        FragmentReverbBinding.inflate(layoutInflater)
    }

    override fun initControlGroups() {
        initControlGroup(
            binding.cvgReverb,
            isInteractive = false,
            shouldStartExpanded = true,
            isRealtime = false,
            extraChildren = binding.llReverbExtras
        )
    }

    override fun setupSpinner() {
        binding.spReverbType.apply {
            setOnTouchListener(spinnerTouchListener)
            onItemSelectedListener = this@ReverbFragment
            adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                ReverbType.values().map { getString(it.nameRes) }
            ).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
            setSelection(
                ReverbType.values().indexOf(ReverbType::nameRes findBy midiViewModel.reverb.nameRes)
            )
        }
    }

    override fun onEffectPresetSelected(index: Int, spinner: Spinner): Boolean {
        midiViewModel.reverb.reverbType = ReverbType.values()[index]
        return true
    }

    override fun isBigParameter(effectParameterData: EffectParameterData): Boolean =
        effectParameterData != EffectParameterData.REVERB_RETURN
                && effectParameterData != EffectParameterData.REVERB_PAN

    override fun getViewModelEffect(): Effect = midiViewModel.reverb

}