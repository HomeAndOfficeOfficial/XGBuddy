package com.example.xgbuddy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.children
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
        // Probably just call this method manually as many times as needed.
        // There won't be that many control groups
        initControlGroup(
            cvgLfo,
            isInteractive = true,
            shouldShowColoredHeader = true,
            shouldStartExpanded = true,
            clLfoExtras
        )
        initControlGroup(cvgAeg, shouldStartExpanded = false)
        initControlGroup(cvgPitch, shouldStartExpanded = false)
        initControlGroup(cvgPeg, shouldStartExpanded = false)
        initControlGroup(cvgFeg, shouldStartExpanded = false)
        initControlGroup(cvgLvlScale, shouldStartExpanded = false)
        initControlGroup(cvgFilterScale, shouldStartExpanded = false)
        initControlGroup(cvgMisc, shouldStartExpanded = false)
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
}