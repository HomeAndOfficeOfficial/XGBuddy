package com.yamahaw.xgbuddy.ui.effects

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.yamahaw.xgbuddy.data.xg.effect.Effect
import com.yamahaw.xgbuddy.data.xg.effect.EffectParameterData
import com.yamahaw.xgbuddy.data.xg.effect.ReverbType
import com.yamahaw.xgbuddy.databinding.FragmentReverbBinding
import com.yamahaw.xgbuddy.util.EnumFinder.findBy

class ReverbFragment : EffectEditFragment<FragmentReverbBinding>() {

    override val effectType: Int = REVERB
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentReverbBinding =
        FragmentReverbBinding::inflate

    override fun initControlGroups() {
        initControlGroup(
            binding!!.cvgReverb,
            isInteractive = false,
            shouldStartExpanded = true,
            isRealtime = false,
            extraChildren = binding!!.llReverbExtras
        )
    }

    override fun setupSpinner() {
        binding!!.spReverbType.apply {
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