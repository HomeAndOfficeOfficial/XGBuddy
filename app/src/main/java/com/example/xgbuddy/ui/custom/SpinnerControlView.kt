package com.example.xgbuddy.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Spinner
import com.example.xgbuddy.R

class SpinnerControlView(context: Context, attributeSet: AttributeSet): ParameterControlView(context, attributeSet) {

    private val spinner: Spinner

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.spinner_control_view, this, false)
        initializeCommonViews(view)
        spinner = view.findViewById(R.id.cpSpinner)
    }

    override fun updateControlBounds() {
        TODO("Not yet implemented")
    }

    override fun updateViews() {
        TODO("Not yet implemented")
    }
}