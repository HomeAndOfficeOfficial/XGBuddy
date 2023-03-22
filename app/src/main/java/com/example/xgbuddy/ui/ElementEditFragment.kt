package com.example.xgbuddy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.xgbuddy.R
import com.example.xgbuddy.ui.custom.ControlViewGroup
import com.example.xgbuddy.ui.custom.ParameterControlView

class ElementEditFragment : QS300ElementBaseFragment(), ParameterControlView.OnParameterChangedListener {
    override val elementAttrs: IntArray = R.styleable.ElementEditFragment_MembersInjector
    override val attrIndexElIndex: Int = R.styleable.ElementEditFragment_MembersInjector_elementIndex

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val v = layoutInflater.inflate(R.layout.fragment_element_edit, container, false)
        findViews(v)
        // Probably just call this method manually as many times as needed.
        // There won't be that many control groups
        initControlGroup(cvgLfo, clLfoExtras)
        return v
    }

    private fun findViews(v: View) {
        cvgLfo = v.findViewById(R.id.cvgLfo)
        clLfoExtras = v.findViewById(R.id.lfoExtras)
    }

    private lateinit var cvgLfo: ControlViewGroup
    private lateinit var clLfoExtras: LinearLayout
}