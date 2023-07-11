package com.yamahaw.xgbuddy.ui.qs300

import android.view.LayoutInflater
import android.view.ViewGroup
import com.yamahaw.xgbuddy.R
import com.yamahaw.xgbuddy.databinding.FragmentElementEditBinding
import com.yamahaw.xgbuddy.ui.custom.ParameterControlView

class ElementEditFragment : QS300ElementBaseFragment<FragmentElementEditBinding>(),
    ParameterControlView.OnParameterChangedListener {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentElementEditBinding =
        FragmentElementEditBinding::inflate
    override val elementAttrs: IntArray = R.styleable.ElementEditFragment_MembersInjector
    override val attrIndexElIndex: Int =
        R.styleable.ElementEditFragment_MembersInjector_elementIndex

    override fun setupViews() {
        binding!!.apply {
            initControlGroup(
                cvgElementMain,
                isInteractive = true,
                shouldShowColoredHeader = true,
                shouldStartExpanded = true
            )
            initControlGroup(
                cvgLfo,
                isInteractive = true,
                shouldShowColoredHeader = true,
                extraChildren = cvgLfo.findViewById(R.id.lfoExtras) // lfoExtras
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