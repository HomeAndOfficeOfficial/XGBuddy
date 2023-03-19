package com.example.xgbuddy.ui.custom

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.xgbuddy.R
import com.example.xgbuddy.data.ControlParameter

abstract class ParameterControlView(context: Context) :
    ConstraintLayout(context) {

    // TODO: Add callbacks to all interface types
    // TODO: Figure out a way to pass values to spinner views.
    //  Wave select spinner will will need its own customized implementation since it has a hi/lo value

    constructor(context: Context, attributeSet: AttributeSet) : this(context)

    private var root: ConstraintLayout? = null
    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (root == null) {
            super.addView(child, index, params)
        } else {
            root?.addView(child, index, params)
        }
    }

    private var tvLabel: TextView? = null
    protected var tvValue: TextView? = null

    var controlParameter: ControlParameter? = null
        set(value) {
            field = value
            label = value?.name ?: "Unnamed param"
            updateControlBounds()
        }
    var value: Byte = 0
        set(value) {
            field = value
            updateViews()
        }
    var label = ""
        set(value) {
            field = value
            tvLabel?.text = value
        }
    var paramId: Int = 0
    var listener: OnParameterChangedListener? = null

    /**
     * Trying to figure out ways to set these views up:
     * 1. Manually
     *      Everything is laid out in xml. Would most likely have classes extend this view,
     *      Each subclass would be for a different type of control. Switch, slider,
     *      range slider, spinner. each one would funnel changes through the OnParameterChanged
     *      method.
     *      This would require a lot of setup and initialization code.
     * 2. Populate views programatically by group.
     *      In the enum classes for parameters, add methods that would return a list of
     *      parameters that would be grouped together.
     *      Lay out control groups in xml, fragments could define callback logic and populate
     *      the control groups.
     *
     *      Control groups could maybe have a label and you could click on it to expand/collapse
     *      Maybe for certain special cases, like parameters that have named values or
     *      parameters that can be represented by switches or other things, they could be added
     *      as children in xml, separate from the others.
     *
     * 3. Expanding on that idea, control groups could be defined in xml arrays. These arrays
     *      could be passed in to the xml declarations, then the fragment that inflates the view
     *      could read that array and create views to populate each control group.
     *
     *      That way, I wouldn't have to create methods in the enums that just group together
     *      members and I also wouldn't have to manually set up each group.
     *
     *     The XML arrays could contain the resource ids of the enum parameters that are grouped
     *     The fragments setting up the views would know which enum class to call since I don't
     *     think one fragment would be pulling parameters from different levels (voice vs element)
     *
     *
     *     So to implement this:
     *     1. Convert "description" field to descriptionRes and create resource strings for
     *     each parameter's description.
     *     2. Create xml arrays grouping together parameters.
     *     3. Create a viewgroup that extends linearlayout. Give it a "label" attribute.
     *          The first child will be a textview of the label.
     *          Pass additional special case "ParameterControlViews" as children. These can be
     *          laid out in whatever manner desired.
     *          The remaining children would be slider control views, create...
     *
     *

     *
     */

    protected abstract fun updateControlBounds()
    protected abstract fun updateViews()

    protected fun getIdFromAttr(typedArray: TypedArray) {
        paramId = typedArray.getResourceId(R.styleable.ParameterControlView_pcId, 0)
    }

    protected fun initializeCommonViews(view: View) {
        root = view.findViewById(R.id.pcRoot)
        tvLabel = view.findViewById(R.id.tvParamLabel)
        tvValue = view.findViewById(R.id.tvParamValue)
    }
//        slider.apply {
//            valueFrom = controlParameter!!.min.toFloat()
//            valueTo = controlParameter!!.max.toFloat()
//            value = controlParameter!!.default.toFloat()
//        }


//    private fun updateViews() {
//        slider.value = value
//        tvValue.text = "$value"
//    }

    fun interface OnParameterChangedListener {
        fun onParameterChanged(controlParameter: ControlParameter)
    }
}