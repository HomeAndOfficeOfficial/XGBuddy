package com.example.xgbuddy.ui.qs300

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
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
class VoiceEditFragment : Fragment(), OnSeekBarChangeListener, AdapterView.OnItemSelectedListener {

    @Inject
    lateinit var midiSession: MidiSession

    private val viewModel: QS300ViewModel by activityViewModels()
    private val binding: FragmentVoiceEditBinding by lazy {
        FragmentVoiceEditBinding.inflate(layoutInflater)
    }
    private var isSeekbarUpdating = true
    private var wasSpinnerTouched = false

    @SuppressLint("ClickableViewAccessibility")
    protected val spinnerTouchListener = View.OnTouchListener { v, event ->
        if (event.action == MotionEvent.ACTION_DOWN) {
            wasSpinnerTouched = true
        }
        false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initObservers()
        binding.cvVoiceLevel.setOnSeekBarChangeListener(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSpinner()
    }

    private fun initObservers() {
//        viewModel.voice.observe(viewLifecycleOwner) {}
        viewModel.preset.observe(viewLifecycleOwner) { preset ->
            preset?.voices!![viewModel.voice.value!!].let {
                isSeekbarUpdating = true
                binding.cvVoiceLevel.progress = it.voiceLevel.toInt()
                binding.spQsVoice.setSelection(viewModel.voice.value!!)
            }
        }
    }

    private fun setupSpinner() {
        val voiceNames = viewModel.preset.value!!.voices.map { it.voiceName }
        binding.spQsVoice.apply {
            setOnTouchListener(spinnerTouchListener)
            setSelection(viewModel.voice.value ?: 0)
            onItemSelectedListener = this@VoiceEditFragment
            adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                voiceNames
            ).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
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

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (wasSpinnerTouched) {
            viewModel.voice.value = position
            binding.cvVoiceLevel.progress =
                viewModel.preset.value!!.voices[position].voiceLevel.toInt()
        }
        wasSpinnerTouched = false
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}
}