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

    val controlItemIds: List<Int>

    init {
        val v = LayoutInflater.from(context).inflate(R.layout.control_view_group, this, false)
        tvLabel = v.findViewById(R.id.tvGroupLabel)
        controlViewContainer = v.findViewById(R.id.sliderControlContainer)
        val styledAttr =
            context.obtainStyledAttributes(attributeSet, R.styleable.ControlViewGroup, 0, 0)
        tvLabel.text =
            styledAttr.getString(R.styleable.ControlViewGroup_cvgLabel) ?: "Unnamed Group"
        val itemArrayId = styledAttr.getResourceId(R.styleable.ControlViewGroup_android_entries, 0)
        controlItemIds = getItemIdsFromAttributes(itemArrayId)
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

    private fun getItemIdsFromAttributes(arrayId: Int): List<Int> {
        if (arrayId != 0) {
            val array = resources.obtainTypedArray(arrayId)
            val itemList = mutableListOf<Int>()
            for (i in 0 until array.length()) {
                itemList.add(array.getResourceId(i, 0))
            }
            array.recycle()
            return itemList
        }
        return listOf()

    }
}