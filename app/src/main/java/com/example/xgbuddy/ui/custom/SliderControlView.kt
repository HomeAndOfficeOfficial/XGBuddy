package com.example.xgbuddy.ui.custom

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import com.example.xgbuddy.R

@SuppressLint("ClickableViewAccessibility")
class SliderControlView(context: Context) :
    ParameterControlView(context), OnSeekBarChangeListener {
    constructor(context: Context, attributeSet: AttributeSet) : this(context) {
        val typedArray =
            context.obtainStyledAttributes(attributeSet, R.styleable.SliderControlView, 0, 0)
        getIdFromAttr(typedArray)
        typedArray.recycle()
    }

    private val seekbar: SeekBar

    var shouldReportAllTouchEvents = false

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
        seekbar.min = controlParameter?.min?.toInt() ?: 0
        seekbar.max = controlParameter?.max?.toInt() ?: 127
    }

    override fun updateViews() {
        seekbar.progress = value.toInt()
        tvValue?.text = "$value"
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        if (fromUser && isRealtimeControl) {
            this.value = progress.toByte()
            listener?.onParameterChanged(controlParameter!!, true)
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
        if (shouldReportAllTouchEvents) {
            this.value = value.toInt().toByte()
            listener?.onParameterChanged(controlParameter!!, true)
        }
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        if (!isRealtimeControl || shouldReportAllTouchEvents) {
            this.value = seekbar.progress.toByte()
            listener?.onParameterChanged(controlParameter!!, false)
        }
    }
}