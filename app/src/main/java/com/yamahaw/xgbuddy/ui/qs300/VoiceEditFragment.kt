package com.yamahaw.xgbuddy.ui.qs300

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.yamahaw.xgbuddy.R
import com.yamahaw.xgbuddy.databinding.FragmentVoiceEditBinding
import com.yamahaw.xgbuddy.ui.MidiBaseFragment
import com.yamahaw.xgbuddy.ui.voiceselect.VoiceSelectionDialogFragment
import com.yamahaw.xgbuddy.ui.voiceselect.OnVoiceItemSelectedListenerImpl
import com.yamahaw.xgbuddy.util.MidiMessageUtility
import com.yamahaw.xgbuddy.viewmodel.QS300ViewModel

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
        binding.apply {
            cvVoiceLevel.setOnSeekBarChangeListener(this@VoiceEditFragment)
            ibNameEdit.setOnClickListener { openPresetEditDialog() }
            ibQSVoiceList.setOnClickListener { openPresetSelectionDialog() }
            ibSave.setOnClickListener { openPresetSaveDialog() }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.etQSPreset.apply {
            showSoftInputOnFocus = false
            setOnClickListener { openPresetSelectionDialog() }
        }
        setupSpinner()
    }

    private fun initObservers() {
        qS300ViewModel.preset.observe(viewLifecycleOwner) { preset ->
            if (qS300ViewModel.voice.value!! > 0 && preset?.voices!!.size == 1) {
                qS300ViewModel.voice.value = 0
            }
            preset?.voices!![qS300ViewModel.voice.value!!].let {
                binding.cvVoiceLevel.progress = it.voiceLevel.toInt()
                binding.spQsVoice.apply {
                    adapter = SimpleVoiceNameAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        preset.voices.map { v -> v.voiceName }.toMutableList()
                    ).apply {
                        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    }
                    setSelection(qS300ViewModel.voice.value!!)
                }
            }
            binding.etQSPreset.setText(preset.name)
        }
    }

    private fun setupSpinner() {
        binding.spQsVoice.apply {
            setOnTouchListener(spinnerTouchListener)
            onItemSelectedListener = this@VoiceEditFragment
        }
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {}

    override fun onStartTrackingTouch(seekBar: SeekBar?) {}

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        val voiceLevel = seekBar!!.progress
        val voiceIndex = qS300ViewModel.voice.value!!
        val voice = qS300ViewModel.preset.value!!.voices[voiceIndex]
        voice.voiceLevel = voiceLevel.toByte()
        midiSession.send(
            MidiMessageUtility.getQS300BulkDump(
                voice,
                voiceIndex,
                midiViewModel.selectedChannel.value!!
            )
        )
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (wasSpinnerTouched) {
            updateVoice(position)
        }
        wasSpinnerTouched = false
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    private fun openPresetEditDialog() {
        val currentPresetName = qS300ViewModel.preset.value!!.name
        val currentVoiceName =
            qS300ViewModel.preset.value!!.voices[qS300ViewModel.voice.value!!].voiceName
        val layout = layoutInflater.inflate(R.layout.dialog_qs300_name, null)
        val etPreset = layout.findViewById<TextInputEditText>(R.id.etPresetName).apply {
            setText(currentPresetName)
        }
        val etVoice = layout.findViewById<TextInputEditText>(R.id.etQsVoiceName).apply {
            setText(currentVoiceName)
        }
        AlertDialog.Builder(requireContext()).apply {
            setView(layout)
            setNeutralButton("Cancel") { dialog, _ -> dialog.dismiss() }
            setPositiveButton("Save") { _, _ ->
                val presetName = etPreset.text.toString().ifEmpty { currentPresetName }
                val voiceName = etVoice.text.toString().ifEmpty { currentVoiceName }
                updateViewModelNames(presetName, voiceName)
                updateNameViews(presetName, voiceName)
            }
            show()
        }
    }

    private fun updateViewModelNames(presetName: String, voiceName: String) {
        qS300ViewModel.preset.value!!.name = presetName
        qS300ViewModel.preset.value!!.voices[qS300ViewModel.voice.value!!].voiceName =
            voiceName
        midiViewModel.channels.value!![midiViewModel.selectedChannel.value!!].voiceName =
            voiceName
    }

    private fun updateNameViews(presetName: String, voiceName: String) {
        binding.apply {
            etQSPreset.setText(presetName)
            (spQsVoice.adapter as SimpleVoiceNameAdapter).updateVoice(
                voiceName,
                qS300ViewModel.voice.value!!
            )
        }
    }

    private fun openPresetSelectionDialog() {
        val voiceSelectFragment = VoiceSelectionDialogFragment(
            OnVoiceItemSelectedListenerImpl.VoiceEditImpl(
                qS300ViewModel,
                midiViewModel,
                midiSession
            )
        ).apply {
            arguments = Bundle().apply {
                putInt(
                    VoiceSelectionDialogFragment.ARG_START_CATEGORY,
                    VoiceSelectionDialogFragment.CATEGORY_ID_QS300
                )
                putString(
                    VoiceSelectionDialogFragment.ARG_START_VOICE,
                    qS300ViewModel.preset.value!!.name
                )
                putBoolean(VoiceSelectionDialogFragment.ARG_QS_EXCLUSIVE, true)
            }
        }
        voiceSelectFragment.show(childFragmentManager, VoiceSelectionDialogFragment.TAG)
    }

    private fun openPresetSaveDialog() {
        val presetName = qS300ViewModel.preset.value!!.name
        val layout = layoutInflater.inflate(R.layout.dialog_add_directory, null)
        val etName = layout.findViewById<TextInputEditText>(R.id.etDirName).apply {
            hint = "Preset Name"
            setText(presetName)
        }
        layout.findViewById<TextInputLayout>(R.id.tilDirName).apply {
            hint = "Preset Name"
        }
        layout.findViewById<TextView>(R.id.tvAddDirTitle).apply {
            text = "Save QS300 Preset"
        }
        AlertDialog.Builder(requireContext()).apply {
            setNeutralButton("Cancel") { d, _, -> d.dismiss() }
            setPositiveButton("Save") { _, _ ->
                val presetName = etName.text.toString().ifEmpty { presetName }
                savePreset(presetName)
            }
        }
    }

    private fun savePreset(presetName: String) {
        qS300ViewModel.preset.value!!.name = presetName
        qS300ViewModel.addCurrentToUserPresets()
    }

    private fun updateVoice(voiceIndex: Int) {
        val currentVoiceIndex = qS300ViewModel.voice.value!!
        qS300ViewModel.voice.value = voiceIndex
        updateSelectedMidiPart(currentVoiceIndex, voiceIndex)
        binding.cvVoiceLevel.progress =
            qS300ViewModel.preset.value!!.voices[voiceIndex].voiceLevel.toInt()
    }

    private fun updateSelectedMidiPart(currentIndex: Int, newIndex: Int) {
        if (newIndex > currentIndex) {
            midiViewModel.selectedChannel.value = midiViewModel.selectedChannel.value!! + 1
        } else if (newIndex < currentIndex) {
            midiViewModel.selectedChannel.value = midiViewModel.selectedChannel.value!! - 1
        }
    }

    class SimpleVoiceNameAdapter(
        context: Context,
        resource: Int,
        private val dataSet: MutableList<String>
    ) : ArrayAdapter<String>(context, resource, dataSet) {
        fun updateVoice(voiceName: String, index: Int) {
            dataSet[index] = voiceName
            notifyDataSetChanged()
        }
    }
}