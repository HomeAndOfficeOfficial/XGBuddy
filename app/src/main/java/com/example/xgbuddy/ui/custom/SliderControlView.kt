package com.example.xgbuddy.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.example.xgbuddy.R
import com.google.android.material.slider.Slider

class SliderControlView(context: Context) :
    ParameterControlView(context) {
    constructor(context: Context, attributeSet: AttributeSet) : this(context) {
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.SliderControlView, 0, 0)
        getIdFromAttr(typedArray)
        typedArray.recycle()
    }

    private val slider: Slider

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.slider_control_view, this, false)
        slider = view.findViewById(R.id.cpSpinner)
        initializeCommonViews(view)
    }

    override fun updateControlBounds() {
        TODO("Not yet implemented")
    }

    override fun updateViews() {
        TODO("Not yet implemented")
    }
}