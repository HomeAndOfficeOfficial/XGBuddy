package com.example.xgbuddy.ui

import android.view.View
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import com.example.xgbuddy.R
import com.example.xgbuddy.data.xg.*
import com.example.xgbuddy.ui.custom.ControlViewGroup
import com.example.xgbuddy.util.EnumFinder.findBy

class ReverbFragment : EffectEditFragment() {

    override val effectType: Int = REVERB

    override fun initControlGroups() {
        initControlGroup(
            cvgReverb,
            isInteractive = false,
            shouldStartExpanded = true,
            isRealtime = false,
            extraChildren = llReverbExtras
        )
    }

    override fun setupSpinner() {
        spReverbType.apply {
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
        val preset = ReverbType.values()[index]
        val updateReverb = Reverb(preset)
        midiViewModel.reverb = updateReverb
        return true
    }

    override fun findViews(root: View) {
        spReverbType = root.findViewById(R.id.spReverbType)
        cvgReverb = root.findViewById(R.id.cvgReverb)
        llReverbExtras = root.findViewById(R.id.llReverbExtras)
    }

    override fun isBigParameter(effectParameterData: EffectParameterData): Boolean =
        effectParameterData != EffectParameterData.REVERB_RETURN
                && effectParameterData != EffectParameterData.REVERB_PAN

    override fun getViewModelEffect(): Effect = midiViewModel.reverb

    private lateinit var spReverbType: Spinner
    private lateinit var cvgReverb: ControlViewGroup
    private lateinit var llReverbExtras: LinearLayout

}