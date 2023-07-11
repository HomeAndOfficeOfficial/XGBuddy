package com.example.xgbuddy.ui.custom

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import com.example.xgbuddy.R

@SuppressLint("ClickableViewAccessibility")
class SliderControlView :
    ParameterControlView, OnSeekBarChangeListener {

    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {
        val typedArray =
            context.obtainStyledAttributes(attributeSet, R.styleable.SliderControlView, 0, 0)
        getIdFromAttr(typedArray)
        typedArray.recycle()
    }

    constructor(context: Context) : this(context, null)

    private val seekbar: SeekBar

    var shouldReportAllTouchEvents = false

    override var izEnabled: Boolean = true
        get() = super.izEnabled
        set(value) {
            field = value
            seekbar.isEnabled = value
        }

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.slider_control_view, this, true)
        seekbar = view.findViewById<SeekBar?>(R.id.cpSeekbar).apply {
            setOnSeekBarChangeListener(this@SliderControlView)
            setOnTouchListener { v, _ ->
                v.parent.requestDisallowInterceptTouchEvent(true)
                false
            }
        }
        initializeCommonViews(view)
    }

    override fun updateControlBounds() {
        seekbar.min = controlParameter?.min ?: 0
        seekbar.max = controlParameter?.max ?: 127
    }

    override fun updateViews() {
        seekbar.progress = value
        updateValueText(value)
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        if (fromUser) {
            updateValueText(progress)
            if (isRealtimeControl) {
                this.value = progress
                listener?.onParameterChanged(controlParameter!!, true)
            }
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
        if (shouldReportAllTouchEvents) {
            this.value = value
            listener?.onParameterChanged(controlParameter!!, true)
        }
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        if (!isRealtimeControl || shouldReportAllTouchEvents) {
            this.value = seekbar.progress
            listener?.onParameterChanged(controlParameter!!, false)
        }
    }

    private fun updateValueText(value: Int) {
        tvValue?.text = controlParameter?.formatter!!.format(value)
    }
}