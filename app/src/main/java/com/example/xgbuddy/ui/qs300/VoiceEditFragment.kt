package com.example.xgbuddy.ui.qs300

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.xgbuddy.MidiSession
import com.example.xgbuddy.databinding.FragmentVoiceEditBinding
import com.example.xgbuddy.util.MidiMessageUtility
import com.example.xgbuddy.viewmodel.QS300ViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class VoiceEditFragment : Fragment(), OnSeekBarChangeListener {

    @Inject
    lateinit var midiSession: MidiSession

    private val viewModel: QS300ViewModel by activityViewModels()
    private val binding: FragmentVoiceEditBinding by lazy {
        FragmentVoiceEditBinding.inflate(layoutInflater)
    }
    private var isSeekbarUpdating = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initObservers()
        binding.cvVoiceLevel.setOnSeekBarChangeListener(this)
        return binding.root
    }

    private fun initObservers() {
//        viewModel.voice.observe(viewLifecycleOwner) {}
        viewModel.preset.observe(viewLifecycleOwner) { preset ->
            preset?.voices!![viewModel.voice.value!!].let {
                binding.tvVoiceName.text = it.voiceName
                isSeekbarUpdating = true
                binding.cvVoiceLevel.progress = it.voiceLevel.toInt()
            }
        }
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {}

    override fun onStartTrackingTouch(seekBar: SeekBar?) {}

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        val voiceLevel = seekBar!!.progress
        val voiceIndex = viewModel.voice.value!!
        val voice = viewModel.preset.value!!.voices[voiceIndex]
        voice.voiceLevel = voiceLevel.toByte()
        midiSession.send(MidiMessageUtility.getQS300BulkDump(voice, voiceIndex))
    }
}