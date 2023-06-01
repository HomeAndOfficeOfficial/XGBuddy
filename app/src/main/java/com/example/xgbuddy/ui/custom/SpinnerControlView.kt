package com.example.xgbuddy.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.xgbuddy.R

class SpinnerControlView : ParameterControlView, OnItemSelectedListener {

    private var entriesResId: Int = 0
    private val spinner: Spinner
    private var isUserUpdating = true

    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {
        val typedArray =
            context.obtainStyledAttributes(attributeSet, R.styleable.SpinnerControlView, 0, 0)
        entriesResId = typedArray.getResourceId(R.styleable.SpinnerControlView_pcEntries, 0)
        getIdFromAttr(typedArray)
        setupSpinner()
        typedArray.recycle()
    }

    constructor(context: Context) : this(context, null)

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.spinner_control_view, this, true)
        initializeCommonViews(view)
        spinner = view.findViewById(R.id.cpSpinner)
    }

    private fun setupSpinner() {
        spinner.apply {
            ArrayAdapter.createFromResource(
                context,
                entriesResId,
                android.R.layout.simple_spinner_item
            ).takeIf { entriesResId > 0 }.also { adapter ->
                adapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                this.adapter = adapter
            }
            onItemSelectedListener = this@SpinnerControlView
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (value.toInt() != position) {
            value = position
            if (isUserUpdating) {
                listener?.onParameterChanged(controlParameter!!, true)
            } else {
                isUserUpdating = true
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    override fun updateControlBounds() {}

    override fun updateViews() {
        isUserUpdating = false
        spinner.setSelection(value)
    }
}