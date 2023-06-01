package com.example.xgbuddy.ui.qs300

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.xgbuddy.R
import com.example.xgbuddy.data.qs300.QS300Element
import com.example.xgbuddy.databinding.FragmentVoiceEditBinding
import com.example.xgbuddy.viewmodel.QS300ViewModel

class VoiceEditFragment : Fragment() {

    private val viewModel: QS300ViewModel by activityViewModels()
    private val binding: FragmentVoiceEditBinding by lazy {
        FragmentVoiceEditBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = layoutInflater.inflate(R.layout.fragment_voice_edit, container, false)
        initObservers()
        return v
    }

    private fun initObservers() {
        viewModel.preset.observe(viewLifecycleOwner) { preset ->
            preset?.voices!![viewModel.voice].let {
                binding.etVoiceName.setText(it.voiceName)
                // TODO: Add VoiceCommon enum so param id can be added to layout element
//                cvVoiceLevel.value = it.voiceLevel
                setElementContainerVisibilities(binding.llElementEditContainer, it.elements)
                setElementContainerVisibilities(binding.llPrimaryControlContainer, it.elements)
            }
        }
    }

    private fun setElementContainerVisibilities(
        container: ViewGroup,
        elements: List<QS300Element>
    ) {
        container.children.forEachIndexed { index, view ->
            view.visibility = if (index + 1 > elements.size) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }
    }
}