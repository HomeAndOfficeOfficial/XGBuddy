package com.example.xgbuddy.ui.custom

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.xgbuddy.R
import com.example.xgbuddy.data.gm.MidiPart

class PartsRowItem(context: Context) :
    LinearLayout(context) {

    constructor(context: Context, attributeSet: AttributeSet) : this(context)

    private var rowNumber = 0

    private var root: LinearLayout? = null
    private val tvChannelNumber: TextView
    private val tvName: TextView
    private var tvReceiving: TextView
    private var tvLevel: TextView
    private var tvPan: TextView
    private var tvReverb: TextView
    private var tvChorus: TextView

    var isRowSelected = false
        set(value) {
            field = value
            updateColor()
        }

    var listener: OnPartsRowTouchListener? = null


    init {
        val v = LayoutInflater.from(context).inflate(R.layout.parts_row_item, this, true)
        root = v.findViewById<LinearLayout?>(R.id.rowContainer).apply {
            setOnClickListener { listener?.onPartsRowTouched(rowNumber) }
        }
        tvChannelNumber = v.findViewById(R.id.tvPartNum)
        tvName = v.findViewById(R.id.tvPartName)
        tvReceiving = v.findViewById(R.id.tvPartReceiving)
        tvLevel = v.findViewById(R.id.tvPartLevel)
        tvPan = v.findViewById(R.id.tvPartPan)
        tvReverb = v.findViewById(R.id.tvPartReverb)
        tvChorus = v.findViewById(R.id.tvPartChorus)
    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (root == null) {
            super.addView(child, index, params)
        } else {
            root?.addView(child, index, params)
        }
    }

    fun setChannelInfo(channel: MidiPart, name: String) {
        rowNumber = channel.ch
        tvChannelNumber.text = "${channel.ch}"
        tvName.text = name
        tvReceiving.text = "${channel.receiveChannel}"
        tvLevel.text = "${channel.volume}"
        tvPan.text = "${channel.pan}"
        tvReverb.text = "${channel.reverbSend}"
        tvChorus.text = "${channel.chorusSend}"

        updateColor()
    }

    private fun updateColor() {
        if (isRowSelected) {
            setBackgroundColor(context.getColor(COLOR_SELECTED))
        } else {
            if (rowNumber % 2 == 0) {
                setBackgroundColor(Color.TRANSPARENT)
            } else {
                setBackgroundColor(context.getColor(COLOR_ODD))
            }
        }
    }

    companion object {
        // TODO: Replace these with theme colors
        private const val COLOR_SELECTED = R.color.green_900
        private const val COLOR_ODD = R.color.container_dark
    }

    fun interface OnPartsRowTouchListener {
        fun onPartsRowTouched(partNumber: Int)
    }
}