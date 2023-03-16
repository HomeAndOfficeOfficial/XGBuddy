package com.example.xgbuddy.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.example.xgbuddy.R
import com.google.android.material.slider.RangeSlider

class RangeSliderControlView(context: Context) :
    ParameterControlView(context) {

    private val rangeSlider: RangeSlider

    init {
        val view =
            LayoutInflater.from(context).inflate(R.layout.range_slider_control_view, this, false)
        rangeSlider = view.findViewById(R.id.cpRangeSlider)
        initializeCommonViews(view)
    }

    override fun updateControlBounds() {
        TODO("Not yet implemented")
    }

    override fun updateViews() {
        TODO("Not yet implemented")
    }
}