package com.example.xgbuddy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.xgbuddy.R
import com.example.xgbuddy.data.ControlParameter

class DrumParamEditFragment : ControlBaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = layoutInflater.inflate(R.layout.fragment_drum_param_edit, container, false)

        return v
    }

    override fun initParameter(paramId: Int): ControlParameter {
        TODO("Not yet implemented")
    }

    override fun onParameterChanged(controlParameter: ControlParameter, isTouching: Boolean) {
        TODO("Not yet implemented")
    }
}