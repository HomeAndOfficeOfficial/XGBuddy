package com.example.xgbuddy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.xgbuddy.R
import com.example.xgbuddy.data.ControlParameter
import com.example.xgbuddy.data.xg.DrumVoiceParameter
import com.example.xgbuddy.ui.custom.ControlViewGroup
import com.example.xgbuddy.ui.custom.SwitchControlView
import com.example.xgbuddy.util.EnumFinder.findBy

class DrumParamEditFragment : ControlBaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = layoutInflater.inflate(R.layout.fragment_drum_param_edit, container, false)
        findViews(v)
        initControlGroups()
        return v
    }

    private fun initControlGroups() {
        initControlGroup(
            cvgDrumParam,
            shouldStartExpanded = true,
            shouldReceiveAllTouchCallbacks = true
        )
        initControlGroup(
            cvgDrumParam2,
            shouldStartExpanded = true,
            extraChildren = llDrumParam2Extras
        )
    }

    override fun initParameter(paramId: Int): ControlParameter {
        val param = DrumVoiceParameter::nameRes findBy paramId
        // TOdo: Add XGControlParameter constructor for drum params
    }

    override fun onParameterChanged(controlParameter: ControlParameter, isTouching: Boolean) {
        TODO("Not yet implemented")
    }

    private fun findViews(v: View) {
        cvgDrumParam = v.findViewById(R.id.cvgDrumParam)
        cvgDrumParam2 = v.findViewById(R.id.cvgDrumParam2)
        llDrumParam2Extras = v.findViewById(R.id.drumParam2Extras)
    }

    private lateinit var cvgDrumParam: ControlViewGroup
    private lateinit var cvgDrumParam2: ControlViewGroup
    private lateinit var llDrumParam2Extras: LinearLayout
}