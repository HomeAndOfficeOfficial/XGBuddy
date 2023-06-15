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
import androidx.fragment.app.activityViewModels
import com.example.xgbuddy.data.xg.XGNormalVoice
import com.example.xgbuddy.databinding.FragmentVoiceEditBinding
import com.example.xgbuddy.ui.MidiBaseFragment
import com.example.xgbuddy.util.MidiMessageUtility
import com.example.xgbuddy.viewmodel.QS300ViewModel

class VoiceEditFragment : MidiBaseFragment(), OnSeekBarChangeListener,
    AdapterView.OnItemSelectedListener {

    private val qS300ViewModel: QS300ViewModel by activityViewModels()
    private val binding: FragmentVoiceEditBinding by lazy {
        FragmentVoiceEditBinding.inflate(layoutInflater)
    }
    private var wasSpinnerTouched = false

    @SuppressLint("ClickableViewAccessibility")
    private val spinnerTouchListener = View.OnTouchListener { v, event ->
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
        qS300ViewModel.preset.observe(viewLifecycleOwner) { preset ->
            if (qS300ViewModel.voice.value!! > 0 && preset?.voices!!.size == 1) {
                qS300ViewModel.voice.value = 0
            }
            preset?.voices!![qS300ViewModel.voice.value!!].let {
                binding.cvVoiceLevel.progress = it.voiceLevel.toInt()
                binding.spQsVoice.apply {
                    adapter = ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        preset.voices.map { v -> v.voiceName }
                    ).apply {
                        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    }
                    setSelection(qS300ViewModel.voice.value!!)
                }
                binding.spQsPreset.setSelection(qS300ViewModel.presets.indexOf(preset))
            }
        }
    }

    private fun setupSpinner() {
        val voiceNames = qS300ViewModel.preset.value!!.voices.map { it.voiceName }
        binding.spQsVoice.apply {
            setOnTouchListener(spinnerTouchListener)
            onItemSelectedListener = this@VoiceEditFragment
            if (adapter == null) {
                adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    voiceNames
                ).apply {
                    setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                }
            }
            setSelection(qS300ViewModel.voice.value ?: 0)
        }
        binding.spQsPreset.apply {
            setOnTouchListener(spinnerTouchListener)
            onItemSelectedListener = this@VoiceEditFragment
            adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                qS300ViewModel.presets.map { it.name }
            ).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
            setSelection(qS300ViewModel.presets.indexOf(qS300ViewModel.preset.value!!))
        }
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {}

    override fun onStartTrackingTouch(seekBar: SeekBar?) {}

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        val voiceLevel = seekBar!!.progress
        val voiceIndex = qS300ViewModel.voice.value!!
        val voice = qS300ViewModel.preset.value!!.voices[voiceIndex]
        voice.voiceLevel = voiceLevel.toByte()
        midiSession.send(MidiMessageUtility.getQS300BulkDump(voice, voiceIndex))
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (wasSpinnerTouched) {
            when (parent) {
                binding.spQsVoice -> updateVoice(position)
                binding.spQsPreset -> updatePreset(position)
            }
        }
        wasSpinnerTouched = false
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    private fun updateVoice(voiceIndex: Int) {
        val currentVoiceIndex = qS300ViewModel.voice.value!!
        qS300ViewModel.voice.value = voiceIndex
        updateSelectedMidiPart(currentVoiceIndex, voiceIndex)
        binding.cvVoiceLevel.progress =
            qS300ViewModel.preset.value!!.voices[voiceIndex].voiceLevel.toInt()
    }

    private fun updatePreset(presetIndex: Int) {
        val currentVoiceCount = qS300ViewModel.preset.value!!.voices.size
        var currentVoice = qS300ViewModel.voice.value!!
        val updatePreset = qS300ViewModel.presets[presetIndex]
        val updateVoiceCount = updatePreset.voices.size
        if (currentVoiceCount == 2 && updateVoiceCount == 1) {
            // One of qs voices needs to be unselected - aka midi part for the second qs300 channel
            // needs to be set to default
            // if current voice is 1, set selected voice to selectedVOice - 1
            //
            if (currentVoice == 1) {
                val updateSelectedChannel = midiViewModel.selectedChannel.value!! - 1
                midiViewModel.channels.value!![midiViewModel.selectedChannel.value!!].setXGNormalVoice(XGNormalVoice.GRAND_PNO)
                midiViewModel.selectedChannel.value = updateSelectedChannel
                midiSession.send(
                    MidiMessageUtility.getXGNormalVoiceChange(
                        updateSelectedChannel+1,
                        XGNormalVoice.GRAND_PNO
                    )
                )
                currentVoice = 0
            } else {
                midiViewModel.channels.value!![midiViewModel.selectedChannel.value!! + 1].setXGNormalVoice(
                    XGNormalVoice.GRAND_PNO
                )
                midiSession.send(
                    MidiMessageUtility.getXGNormalVoiceChange(
                        midiViewModel.selectedChannel.value!! + 1,
                        XGNormalVoice.GRAND_PNO
                    )
                )
            }
        }
        val firstChannel =
            if (currentVoice == 1)
                midiViewModel.selectedChannel.value!! - 1
            else
                midiViewModel.selectedChannel.value!!
        qS300ViewModel.preset.value = qS300ViewModel.presets[presetIndex]
        qS300ViewModel.preset.value!!.voices.forEachIndexed { index, voice ->
            midiViewModel.channels.value!![firstChannel + index].changeQS300Voice(voice, index)
            midiSession.sendBulkMessage(MidiMessageUtility.getQS300BulkDump(voice, index))
            midiSession.sendBulkMessage(
                MidiMessageUtility.getQS300VoiceSelection(
                    firstChannel + index,
                    index,
                )
            )
        }
    }

    private fun updateSelectedMidiPart(currentIndex: Int, newIndex: Int) {
        if (newIndex > currentIndex) {
            midiViewModel.selectedChannel.value = midiViewModel.selectedChannel.value!! + 1
        } else if (newIndex < currentIndex) {
            midiViewModel.selectedChannel.value = midiViewModel.selectedChannel.value!! - 1
        }
    }
}