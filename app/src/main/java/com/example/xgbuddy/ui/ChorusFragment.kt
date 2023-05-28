package com.example.xgbuddy.ui

import android.view.View
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import com.example.xgbuddy.R
import com.example.xgbuddy.data.xg.*
import com.example.xgbuddy.ui.custom.ControlViewGroup
import com.example.xgbuddy.util.EnumFinder.findBy

class ChorusFragment : EffectEditFragment() {

    override val effectType: Int = CHORUS
    override fun getViewModelEffect(): Effect = midiViewModel.chorus

    override fun initControlGroups() {
        initControlGroup(
            cvgChorus,
            isInteractive = false,
            shouldStartExpanded = true,
            isRealtime = false,
            extraChildren = llChorusExtras
        )
    }

    override fun setupSpinner() {
        spChorusType.apply {
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

    override fun findViews(root: View) {
        spChorusType = root.findViewById(R.id.spChorusType)
        cvgChorus = root.findViewById(R.id.cvgChorus)
        llChorusExtras = root.findViewById(R.id.llChorusExtras)
    }

    override fun onEffectPresetSelected(index: Int, spinner: Spinner): Boolean {
        midiViewModel.chorus.chorusType = ChorusType.values()[index]
        return true
    }

    private lateinit var spChorusType: Spinner
    private lateinit var cvgChorus: ControlViewGroup
    private lateinit var llChorusExtras: LinearLayout
}