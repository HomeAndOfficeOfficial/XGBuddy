package com.example.xgbuddy.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.example.xgbuddy.R
import com.google.android.material.slider.RangeSlider

class RangeSliderControlView : ParameterControlView {

    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {
        val typedArray =
            context.obtainStyledAttributes(attributeSet, R.styleable.RangeSliderControlView, 0, 0)
        getIdFromAttr(typedArray)
        typedArray.recycle()
    }

    constructor(context: Context) : this(context, null)

    private val rangeSlider: RangeSlider

    init {
        val view =
            LayoutInflater.from(context).inflate(R.layout.range_slider_control_view, this, true)
        rangeSlider = view.findViewById(R.id.cpRangeSlider)
        initializeCommonViews(view)
    }

    override fun updateControlBounds() {

    }

    override fun updateViews() {

    }
}