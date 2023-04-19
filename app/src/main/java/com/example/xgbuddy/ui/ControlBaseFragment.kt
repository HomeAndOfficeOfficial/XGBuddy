package com.example.xgbuddy.ui

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.xgbuddy.MidiSession
import com.example.xgbuddy.data.ControlParameter
import com.example.xgbuddy.ui.custom.ControlViewGroup
import com.example.xgbuddy.ui.custom.ParameterControlView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
abstract class ControlBaseFragment: Fragment(), ParameterControlView.OnParameterChangedListener {

    @Inject
    lateinit var midiSession: MidiSession

    protected val controlGroups: MutableList<ControlViewGroup> = mutableListOf()

    protected open fun initControlGroup(
        controlGroup: ControlViewGroup,
        isInteractive: Boolean = true,
        shouldShowColoredHeader: Boolean = true,
        shouldStartExpanded: Boolean = true,
        extraChildren: ViewGroup? = null
    ) {
        controlGroup.apply {
            this.isInteractive = isInteractive
            if (!shouldStartExpanded) {
                collapse()
            }
        }
        controlGroups.add(controlGroup)
    }

    override fun onStop() {
        super.onStop()
        controlGroups.clear()
    }

   abstract override fun onParameterChanged(controlParameter: ControlParameter)
}