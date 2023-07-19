package com.yamahaw.xgbuddy.ui.custom

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.yamahaw.xgbuddy.R
import com.yamahaw.xgbuddy.data.buddy.ControlParameter

abstract class ParameterControlView :
    ConstraintLayout {

    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet)

    constructor(context: Context) : this(context, null)

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
    var value: Int = -1
        set(value) {
            controlParameter?.value = value
            if (field != value) {
                field = value
                updateViews()
            }
        }
    var label = ""
        set(value) {
            field = value
            tvLabel?.text = value
        }
    var paramId: Int = 0
    var listener: OnParameterChangedListener? = null
    var isRealtimeControl = true
    open var izEnabled = true // I don't know what the accidental override is for isEnabled

    abstract fun updateControlBounds()
    protected abstract fun updateViews()

    protected fun getIdFromAttr(typedArray: TypedArray) {
        paramId = typedArray.getResourceId(R.styleable.ParameterControlView_pcId, 0)
    }

    protected fun initializeCommonViews(view: View) {
        root = view.findViewById(R.id.pcRoot)
        tvLabel = view.findViewById(R.id.tvParamLabel)
        tvValue = view.findViewById(R.id.tvParamValue)
    }

    fun interface OnParameterChangedListener {
        fun onParameterChanged(controlParameter: ControlParameter, isTouching: Boolean)
    }
}