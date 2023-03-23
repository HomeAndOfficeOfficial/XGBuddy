package com.example.xgbuddy.ui.custom

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import com.example.xgbuddy.R
import com.example.xgbuddy.data.QS300Element
import com.example.xgbuddy.data.QS300ElementParameter
import com.example.xgbuddy.util.EnumFinder.findBy

class ControlViewGroup(context: Context, attributeSet: AttributeSet) :
    LinearLayout(context, attributeSet) {

    private var root: LinearLayout? = null
    private val bLabel: Button
    private val controlViewContainer: LinearLayout

    val controlViewMap: MutableMap<UByte, ParameterControlView> = mutableMapOf()
    val controlItemIds: List<Int>

    var isInteractive: Boolean = true
        set(value) {
            field = value
            if (!value) {
                updateHeaderButtonBehavior(value)
            }
        }
    var headerColor: Int = 0
        set(value) {
            field = value
            bLabel.backgroundTintList = ColorStateList.valueOf(value)
        }

    init {
        val v = LayoutInflater.from(context).inflate(R.layout.control_view_group, this, true)
        root = v.findViewById(R.id.cvgRoot)
        bLabel = v.findViewById(R.id.bLabel)
        updateHeaderButtonBehavior(isInteractive)
        controlViewContainer = v.findViewById(R.id.sliderControlContainer)
        val styledAttr =
            context.obtainStyledAttributes(attributeSet, R.styleable.ControlViewGroup, 0, 0)
        bLabel.text =
            styledAttr.getString(R.styleable.ControlViewGroup_cvgLabel) ?: "Unnamed Group"
        val itemArrayId = styledAttr.getResourceId(R.styleable.ControlViewGroup_android_entries, 0)
        controlItemIds = getItemIdsFromAttributes(itemArrayId)
        styledAttr.recycle()
    }

    private fun toggleControlVisibility() {
        val isControlVisible = controlViewContainer.visibility == View.VISIBLE
        val iconRes =
            if (isControlVisible) R.drawable.baseline_keyboard_arrow_down_24 else R.drawable.baseline_keyboard_arrow_up_24
        bLabel.setCompoundDrawablesWithIntrinsicBounds(0, 0, iconRes, 0)
        controlViewContainer.visibility = if (isControlVisible) View.GONE else View.VISIBLE
    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (root == null) {
            super.addView(child, index, params)
        } else {
            if (child?.id == R.id.bLabel) {
                root?.addView(child, index, params)
            } else {
                controlViewContainer.addView(child, 0, params)
            }
        }
    }

    override fun removeView(view: View?) {
        if (root == null) {
            super.removeView(view)
        } else {
            root?.removeView(view)
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
            val param = QS300ElementParameter::baseAddress findBy it
            Log.d(TAG, "Map key: $it, param: ${param?.name}")
            controlViewMap[it]?.value =
                if (param != null) qS300Element.getPropertyValue(param.reflectedField) else 0
        }
    }

    fun collapse() {
        if (controlViewContainer.visibility == VISIBLE) {
            toggleControlVisibility()
        }
    }

    fun expand() {
        if (controlViewContainer.visibility == GONE) {
            toggleControlVisibility()
        }
    }

    private fun updateHeaderButtonBehavior(isInteractive: Boolean) {
        bLabel.apply {
            if (isInteractive) {
                setOnClickListener {
                    toggleControlVisibility()
                }
                setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.baseline_keyboard_arrow_up_24,
                    0
                )
            } else {
                visibility = View.GONE
            }
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

    companion object {
        const val TAG = "ControlViewGroup"
    }
}