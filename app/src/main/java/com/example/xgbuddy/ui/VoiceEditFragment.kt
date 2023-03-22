package com.example.xgbuddy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.xgbuddy.R
import com.example.xgbuddy.data.QS300Element
import com.example.xgbuddy.ui.custom.SliderControlView
import com.example.xgbuddy.util.MidiStoredDataUtility
import com.example.xgbuddy.viewmodel.QS300ViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class VoiceEditFragment : Fragment() {

    @Inject
    lateinit var dataUtility: MidiStoredDataUtility

    private val viewModel: QS300ViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO: This should be handled in the top level fragment (Probably QS300 Session?)
        // The overall structure is still uncertain. Working from the bottom up.
        viewModel.preset.value = dataUtility.getQS300Presets()[1]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = layoutInflater.inflate(R.layout.fragment_voice_edit, container, false)
        findViews(v)
        initObservers()
        return v
    }

    private fun initObservers() {
        viewModel.preset.observe(viewLifecycleOwner) { preset ->
            // For now just work with single voice
            preset.voices[0].let {
                etVoiceName.setText(it.voiceName)
                cvVoiceLevel.value = it.voiceLevel
                setElementContainerVisibilities(llElementEditContainer, it.elements)
                setElementContainerVisibilities(llPrimaryControlContainer, it.elements)
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

    private fun findViews(v: View) {
        llPrimaryControlContainer = v.findViewById(R.id.llPrimaryControlContainer)
        llElementEditContainer = v.findViewById(R.id.llElementEditContainer)
        etVoiceName = v.findViewById(R.id.etVoiceName)
        cvVoiceLevel = v.findViewById(R.id.cvVoiceLevel)
    }

    private lateinit var llPrimaryControlContainer: LinearLayout
    private lateinit var etVoiceName: EditText
    private lateinit var cvVoiceLevel: SliderControlView
    private lateinit var llElementEditContainer: LinearLayout
}