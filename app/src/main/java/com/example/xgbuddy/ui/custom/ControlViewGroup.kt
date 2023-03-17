package com.example.xgbuddy.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.xgbuddy.R
import com.example.xgbuddy.data.QS300Element
import com.example.xgbuddy.data.QS300ElementParameter
import com.example.xgbuddy.util.EnumFinder.findBy

class ControlViewGroup(context: Context, attributeSet: AttributeSet) :
    LinearLayout(context, attributeSet) {

    private var root: LinearLayout? = null
    private val tvLabel: TextView
    private val controlViewContainer: LinearLayout


    val controlViewMap: MutableMap<UByte, ParameterControlView> = mutableMapOf()
    val controlItemIds: List<Int>

    init {

        val v = LayoutInflater.from(context).inflate(R.layout.control_view_group, this, true)
        root = v.findViewById(R.id.cvgRoot)
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

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (root == null) {
            super.addView(child, index, params)
        } else {
            root?.addView(child, index, params)
        }
    }

    fun addControlView(view: ParameterControlView) {
        controlViewMap[view.controlParameter!!.addr] = view
        controlViewContainer.addView(view)
    }

    fun mapUngroupedView(view: ParameterControlView) {
        controlViewMap[view.controlParameter!!.addr] = view
    }

    // There will probably be multiple overloads of this to update views for whatever this group contains
    fun updateViews(qS300Element: QS300Element) {
        controlViewMap.keys.forEach {
            // TODO: Move this 0x50 to a constant
            val baseAddr = it - (qS300Element.elementNumber * 0x50).toUByte()
            val param = QS300ElementParameter::getBaseAddress findBy baseAddr
            controlViewMap[it]?.value = qS300Element.getPropertyValue(param!!.reflectedField)
        }
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