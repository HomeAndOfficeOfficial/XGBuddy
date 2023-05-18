package com.example.xgbuddy.ui.custom

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import com.example.xgbuddy.R
import com.google.android.material.slider.Slider

@SuppressLint("ClickableViewAccessibility")
class SliderControlView(context: Context) :
    ParameterControlView(context), Slider.OnChangeListener, Slider.OnSliderTouchListener {
    constructor(context: Context, attributeSet: AttributeSet) : this(context) {
        val typedArray =
            context.obtainStyledAttributes(attributeSet, R.styleable.SliderControlView, 0, 0)
        getIdFromAttr(typedArray)
        typedArray.recycle()
    }

    private val slider: Slider

    var shouldReportAllTouchEvents = false

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.slider_control_view, this, true)
        slider = view.findViewById<Slider?>(R.id.cpSlider).apply {
            addOnChangeListener(this@SliderControlView)
            addOnSliderTouchListener(this@SliderControlView)
            setOnTouchListener { v, _ ->
                v.parent.requestDisallowInterceptTouchEvent(true)
                false
            }
        }
        initializeCommonViews(view)
    }

    override fun updateControlBounds() {
        slider.valueFrom = controlParameter?.min?.toFloat() ?: 0f
        slider.valueTo = controlParameter?.max?.toFloat() ?: 127f
    }

    override fun updateViews() {
        if (value.toFloat() in slider.valueFrom..slider.valueTo)
            slider.value = value.toFloat()
        tvValue?.text = "$value"
    }

    @SuppressLint("RestrictedApi")
    override fun onValueChange(slider: Slider, value: Float, fromUser: Boolean) {
        if (fromUser && isRealtimeControl) {
            this.value = value.toInt().toByte()
            listener?.onParameterChanged(controlParameter!!, true)
        }
    }

    @SuppressLint("RestrictedApi")
    override fun onStartTrackingTouch(slider: Slider) {
        if (shouldReportAllTouchEvents) {
            this.value = value.toInt().toByte()
            listener?.onParameterChanged(controlParameter!!, true)
        }
    }

    @SuppressLint("RestrictedApi")
    override fun onStopTrackingTouch(slider: Slider) {
        if (!isRealtimeControl || shouldReportAllTouchEvents) {
            this.value = slider.value.toInt().toByte()
            listener?.onParameterChanged(controlParameter!!, false)
        }
    }
}