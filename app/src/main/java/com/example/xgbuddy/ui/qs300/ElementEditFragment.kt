package com.example.xgbuddy.ui.qs300

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.xgbuddy.R
import com.example.xgbuddy.ui.custom.ControlViewGroup
import com.example.xgbuddy.ui.custom.ParameterControlView

class ElementEditFragment : QS300ElementBaseFragment(),
    ParameterControlView.OnParameterChangedListener {

    override val elementAttrs: IntArray = R.styleable.ElementEditFragment_MembersInjector
    override val attrIndexElIndex: Int =
        R.styleable.ElementEditFragment_MembersInjector_elementIndex

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val v = layoutInflater.inflate(R.layout.fragment_element_edit, container, false)
        findViews(v)
        initControlGroup(
            cvgLfo,
            isInteractive = true,
            shouldShowColoredHeader = true,
            shouldStartExpanded = true,
            clLfoExtras
        )
        initControlGroup(cvgAeg)
        initControlGroup(cvgPitch)
        initControlGroup(cvgPeg)
        initControlGroup(cvgFeg)
        initControlGroup(cvgLvlScale)
        initControlGroup(cvgFilterScale)
        initControlGroup(cvgMisc)
        return v
    }

    private fun findViews(v: View) {
        cvgLfo = v.findViewById(R.id.cvgLfo)
        clLfoExtras = v.findViewById(R.id.lfoExtras)
        cvgMisc = v.findViewById(R.id.cvgMisc)
        cvgAeg = v.findViewById(R.id.cvgAeg)
        cvgPitch = v.findViewById(R.id.cvgPitch)
        cvgPeg = v.findViewById(R.id.cvgPeg)
        cvgFeg = v.findViewById(R.id.cvgFeg)
        cvgLvlScale = v.findViewById(R.id.cvgLvlScale)
        cvgFilterScale = v.findViewById(R.id.cvgFilterScale)
        tvElementNum = v.findViewById<TextView?>(R.id.tvElementNum).apply {
            text = buildString {
                append("Element ")
                append(elementIndex + 1)
            }
        }
    }

    private lateinit var cvgLfo: ControlViewGroup
    private lateinit var cvgMisc: ControlViewGroup
    private lateinit var cvgAeg: ControlViewGroup
    private lateinit var cvgPitch: ControlViewGroup
    private lateinit var cvgPeg: ControlViewGroup
    private lateinit var cvgFeg: ControlViewGroup
    private lateinit var cvgLvlScale: ControlViewGroup
    private lateinit var cvgFilterScale: ControlViewGroup
    private lateinit var clLfoExtras: LinearLayout
    private lateinit var tvElementNum: TextView
}