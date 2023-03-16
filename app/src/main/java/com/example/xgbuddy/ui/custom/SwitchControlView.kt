package com.example.xgbuddy.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.example.xgbuddy.R
import com.google.android.material.switchmaterial.SwitchMaterial

class SwitchControlView(context: Context, attributeSet: AttributeSet) :
    ParameterControlView(context, attributeSet) {

    private val switch: SwitchMaterial

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.switch_control_view, this, false)
        switch = view.findViewById(R.id.cpSwitch)
        initializeCommonViews(view)
    }

    override fun updateControlBounds() {
        TODO("Not yet implemented")
    }

    override fun updateViews() {
        TODO("Not yet implemented")
    }
}