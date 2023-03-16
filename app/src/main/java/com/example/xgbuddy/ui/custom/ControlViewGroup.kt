package com.example.xgbuddy.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.example.xgbuddy.R

class ControlViewGroup(context: Context, attributeSet: AttributeSet) :
    LinearLayout(context, attributeSet) {

    private val tvLabel: TextView
    private val controlViewContainer: LinearLayout

    init {
        val v = LayoutInflater.from(context).inflate(R.layout.control_view_group, this, false)
        tvLabel = v.findViewById(R.id.tvGroupLabel)
        controlViewContainer = v.findViewById(R.id.sliderControlContainer)
        val styledAttr =
            context.obtainStyledAttributes(attributeSet, R.styleable.ControlViewGroup, 0, 0)
        tvLabel.text =
            styledAttr.getString(R.styleable.ControlViewGroup_cvgLabel) ?: "Unnamed Group"
        styledAttr.recycle()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (childCount > 2) {
            removeView(controlViewContainer)
            addView(controlViewContainer)
        }
    }

    fun addControlView(view: ParameterControlView) {
        controlViewContainer.addView(view)
    }
}