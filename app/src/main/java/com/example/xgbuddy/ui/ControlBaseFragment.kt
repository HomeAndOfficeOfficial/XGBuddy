package com.example.xgbuddy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.xgbuddy.MidiSession
import com.example.xgbuddy.data.ControlParameter
import com.example.xgbuddy.ui.custom.ControlViewGroup
import com.example.xgbuddy.ui.custom.ParameterControlView
import com.example.xgbuddy.ui.custom.SliderControlView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
abstract class ControlBaseFragment<VB : ViewBinding?> : Fragment(),
    ParameterControlView.OnParameterChangedListener {

    @Inject
    lateinit var midiSession: MidiSession

    protected var binding: VB? = null
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB

    protected val controlGroups: MutableList<ControlViewGroup> = mutableListOf()

    abstract fun setupViews()
    abstract fun initParameter(paramId: Int): ControlParameter?

    protected fun initBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = bindingInflater.invoke(inflater, container, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = bindingInflater.invoke(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    protected open fun initControlGroup(
        controlGroup: ControlViewGroup,
        isInteractive: Boolean = true,
        shouldShowColoredHeader: Boolean = true,
        shouldStartExpanded: Boolean = false,
        extraChildren: ViewGroup? = null,
        shouldReceiveAllTouchCallbacks: Boolean = false,
        isRealtime: Boolean = true
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
                    isRealtimeControl = isRealtime
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

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    protected fun clearControlGroups() {
        controlGroups.forEach {
            it.removeAllViews()
        }
        controlGroups.clear()
    }

    abstract override fun onParameterChanged(
        controlParameter: ControlParameter,
        isTouching: Boolean
    )
}