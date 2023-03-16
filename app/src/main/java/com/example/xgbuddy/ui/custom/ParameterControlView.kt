package com.example.xgbuddy.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.xgbuddy.R
import com.example.xgbuddy.data.ControlParameter
import com.google.android.material.slider.Slider

class ParameterControlView(context: Context, attributeSet: AttributeSet) :
    ConstraintLayout(context, attributeSet) {

    private val slider: Slider
    private val tvLabel: TextView
    private val tvValue: TextView

    var controlParameter: ControlParameter? = null
        set(value) {
            field = value
            updateControlBounds()
        }
    var value = 0f
        set(value) {
            field = value
            updateViews()
        }
    var label = ""
        set(value) {
            field = value
            tvLabel.text = value
        }
    var listener: OnParameterChangedListener? = null

    init {
        val v = LayoutInflater.from(context).inflate(R.layout.parameter_control_view, this, false)
        tvLabel = v.findViewById(R.id.tvParamLabel)
        tvValue = v.findViewById(R.id.tvParamValue)
        slider = v.findViewById(R.id.cpSlider)
    }

    private fun updateControlBounds() {
        slider.apply {
            valueFrom = controlParameter!!.min.toFloat()
            valueTo = controlParameter!!.max.toFloat()
            value = controlParameter!!.default.toFloat()
        }
    }

    private fun updateViews() {
        slider.value = value
        tvValue.text = "$value"
    }

    fun interface OnParameterChangedListener {
        fun onParameterChanged(controlParameter: ControlParameter)
    }
}