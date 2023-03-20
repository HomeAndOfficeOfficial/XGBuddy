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

class SpinnerControlView(context: Context) : ParameterControlView(context), OnItemSelectedListener {

    private var entriesResId: Int = 0
    private val spinner: Spinner

    constructor(context: Context, attributeSet: AttributeSet) : this(context) {
        val typedArray =
            context.obtainStyledAttributes(attributeSet, R.styleable.SpinnerControlView, 0, 0)
        entriesResId = typedArray.getResourceId(R.styleable.SpinnerControlView_pcEntries, 0)
        getIdFromAttr(typedArray)
        setupSpinner()
        typedArray.recycle()
    }

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
            value = position.toByte()
            listener?.onParameterChanged(controlParameter!!)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    override fun updateControlBounds() {}

    override fun updateViews() {
        spinner.setSelection(value.toInt())
    }
}