package com.example.xgbuddy.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.example.xgbuddy.R
import com.google.android.material.switchmaterial.SwitchMaterial

class SwitchControlView : ParameterControlView {

    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {
        val typedArray =
            context.obtainStyledAttributes(attributeSet, R.styleable.SwitchControlView, 0, 0)
        getIdFromAttr(typedArray)
        typedArray.recycle()
    }

    constructor(context: Context) : this(context, null)

    private val switch: SwitchMaterial

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.switch_control_view, this, true)
        switch = view.findViewById<SwitchMaterial?>(R.id.cpSwitch).apply {
            setOnClickListener {
                val updatedValue = if ((it as SwitchMaterial).isChecked) 1 else 0
                if (value != updatedValue) {
                    value = updatedValue
                    listener?.onParameterChanged(controlParameter!!, true)
                }
            }
            initializeCommonViews(view)
        }
    }

    override fun updateControlBounds() {}

    override fun updateViews() {
        switch.isChecked = controlParameter?.value == 1
    }
}