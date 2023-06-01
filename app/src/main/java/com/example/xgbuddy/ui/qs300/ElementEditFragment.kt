package com.example.xgbuddy.ui.qs300

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.xgbuddy.R
import com.example.xgbuddy.databinding.FragmentElementEditBinding
import com.example.xgbuddy.ui.custom.ParameterControlView

class ElementEditFragment : QS300ElementBaseFragment<FragmentElementEditBinding>(),
    ParameterControlView.OnParameterChangedListener {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentElementEditBinding =
        FragmentElementEditBinding::inflate
    override val elementAttrs: IntArray = R.styleable.ElementEditFragment_MembersInjector
    override val attrIndexElIndex: Int =
        R.styleable.ElementEditFragment_MembersInjector_elementIndex

    override fun setupViews() {
        binding!!.apply {
            tvElementNum.text = buildString {
                append("Element ")
                append(elementIndex + 1)
            }
            initControlGroup(
                cvgLfo,
                isInteractive = true,
                shouldShowColoredHeader = true,
                shouldStartExpanded = true,
                cvgLfo.findViewById(R.id.lfoExtras) // lfoExtras
            )
            initControlGroup(cvgAeg)
            initControlGroup(cvgPitch)
            initControlGroup(cvgPeg)
            initControlGroup(cvgFeg)
            initControlGroup(cvgLvlScale)
            initControlGroup(cvgFilterScale)
            initControlGroup(cvgMisc)
        }
    }
}