package com.example.xgbuddy.ui

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.xgbuddy.MidiSession
import com.example.xgbuddy.data.ControlParameter
import com.example.xgbuddy.ui.custom.ControlViewGroup
import com.example.xgbuddy.ui.custom.ParameterControlView
import com.example.xgbuddy.ui.custom.SliderControlView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
abstract class ControlBaseFragment : Fragment(), ParameterControlView.OnParameterChangedListener {

    @Inject
    lateinit var midiSession: MidiSession

    protected val controlGroups: MutableList<ControlViewGroup> = mutableListOf()

    abstract fun initParameter(paramId: Int): ControlParameter

    protected open fun initControlGroup(
        controlGroup: ControlViewGroup,
        isInteractive: Boolean = true,
        shouldShowColoredHeader: Boolean = true,
        shouldStartExpanded: Boolean = false,
        extraChildren: ViewGroup? = null,
        shouldReceiveAllTouchCallbacks: Boolean = false
    ) {
        controlGroup.apply {
            this.isInteractive = isInteractive
            if (!shouldStartExpanded) {
                collapse()
            }
            controlItemIds.forEach {
                addControlView(SliderControlView(requireContext()).apply {
                    controlParameter = initParameter(it)
                    listener = this@ControlBaseFragment
                    shouldReportAllTouchEvents = shouldReceiveAllTouchCallbacks
                })
            }
        }
        extraChildren?.apply {
            for (i in 0 until childCount) {
                (getChildAt(i) as ParameterControlView).apply {
                    controlParameter = initParameter(paramId)
                    listener = this@ControlBaseFragment
                    controlGroup.mapUngroupedView(this)
                }
            }
        }
        controlGroups.add(controlGroup)
    }

    // I think this was an attempt to fix the slider issue, but I don't think it did.
    // And doing this makes it so views don't update when we resume app. So leave this out for now.
//    override fun onStop() {
//        super.onStop()
//        controlGroups.clear()
//    }

    abstract override fun onParameterChanged(
        controlParameter: ControlParameter,
        isTouching: Boolean
    )
}