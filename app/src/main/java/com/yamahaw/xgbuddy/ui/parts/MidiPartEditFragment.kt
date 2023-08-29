package com.yamahaw.xgbuddy.ui.parts

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.google.android.material.textfield.TextInputLayout
import com.yamahaw.xgbuddy.R
import com.yamahaw.xgbuddy.data.buddy.ControlParameter
import com.yamahaw.xgbuddy.data.gm.MidiControlChange
import com.yamahaw.xgbuddy.data.comms.MidiMessage
import com.yamahaw.xgbuddy.data.gm.MidiParameter
import com.yamahaw.xgbuddy.data.gm.MidiPart
import com.yamahaw.xgbuddy.databinding.FragmentMidiPartEditBinding
import com.yamahaw.xgbuddy.ui.ControlBaseFragment
import com.yamahaw.xgbuddy.ui.custom.SwitchControlView
import com.yamahaw.xgbuddy.ui.voiceselect.OnVoiceItemSelectedListenerImpl
import com.yamahaw.xgbuddy.ui.voiceselect.VoiceSelectionDialogFragment
import com.yamahaw.xgbuddy.util.EnumFinder.findBy
import com.yamahaw.xgbuddy.util.MidiMessageUtility
import com.yamahaw.xgbuddy.viewmodel.QS300ViewModel

class MidiPartEditFragment : ControlBaseFragment<FragmentMidiPartEditBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMidiPartEditBinding =
        FragmentMidiPartEditBinding::inflate

    private val qs300ViewModel: QS300ViewModel by activityViewModels()

    private var currentParam: MidiParameter? = null
    private var isNrpnActive = false
    private var wasSpinnerTouched = false

    @SuppressLint("ClickableViewAccessibility")
    override fun setupViews() {
        initControlGroups()
        binding?.apply {
            ibNameEdit.setOnClickListener {
                openNameEditDialog()
            }
            ibVoiceList.setOnClickListener { openVoiceSelectionDialog() }
            etPartVoiceName.apply {
                showSoftInputOnFocus = false
                setOnClickListener { openVoiceSelectionDialog() }
            }
            midiViewModel.channels.observe(viewLifecycleOwner) {
                val channel = midiViewModel.selectedChannel.value
                // TODO: Find a way to change the edit text when the voice is selected.
                if (it[channel!!].voiceName.isEmpty()) {
                    etPartVoiceName.setText(it[channel].voiceNameRes)
                } else {
                    etPartVoiceName.setText(it[channel].voiceName)
                }
            }
            midiViewModel.selectedChannel.observe(viewLifecycleOwner) {
                updateViews(midiViewModel.channels.value!![it])
            }

            // Todo: clean this up a little.
            spRcvCh.setSelection(
                midiViewModel.channels.value!![midiViewModel.selectedChannel.value!!]
                    .receiveChannel.toInt()
            )
            spRcvCh.apply {
                setOnTouchListener { _, event ->
                    if (event?.action == MotionEvent.ACTION_DOWN) {
                        wasSpinnerTouched = true
                    }
                    false
                }
                onItemSelectedListener = object : OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        if (wasSpinnerTouched) {
                            midiViewModel.channels.value!![midiViewModel.selectedChannel.value!!].receiveChannel =
                                position.toByte()
                            midiViewModel.channels.value = midiViewModel.channels.value
                            midiSession.send(
                                MidiMessageUtility.getXGParamChange(
                                    midiViewModel.selectedChannel.value!!,
                                    MidiParameter.RCV_CHANNEL,
                                    position.toByte()
                                )
                            )
                        }
                        wasSpinnerTouched = false
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        wasSpinnerTouched = false
                    }
                }
            }
        }
    }

    private fun updateViews(midiPart: MidiPart) {
        if (midiPart.voiceName.isEmpty()) {
            binding!!.etPartVoiceName.setText(midiPart.voiceNameRes)
        } else {
            binding!!.etPartVoiceName.setText(midiPart.voiceName)
        }

        binding!!.spRcvCh.setSelection(midiPart.receiveChannel.toInt())
        controlGroups.forEach {
            it.updateViews(midiPart)
        }
    }

    private fun initControlGroups() {
        binding?.apply {
            initControlGroup(
                cvgMidiMain,
                shouldStartExpanded = true,
                shouldReceiveAllTouchCallbacks = true
            )
            // TODO: Add detune as extra
            initControlGroup(cvgMidiPitch, shouldReceiveAllTouchCallbacks = true)
            initControlGroup(cvgMidiEG, shouldReceiveAllTouchCallbacks = true)
            initControlGroup(cvgMidiFx)
            initControlGroup(cvgMidiNote, extraChildren = midiNoteExtras)
            initControlGroup(cvgMidiPat)
            initControlGroup(cvgMidiCat)
            initControlGroup(cvgMidiBend)
            initControlGroup(cvgMidiMod)
            initControlGroup(cvgMidiAc1)
            initControlGroup(cvgMidiAc2)
            initControlGroup(cvgMidiScale)
            initControlGroup(cvgMidiCh)
        }
        initReceiveSwitches()
    }

    private fun initReceiveSwitches() {
        binding!!.cvgMidiRcv.apply {
            isInteractive = true
            collapse()
            controlItemIds.forEach {
                addControlView(SwitchControlView(requireContext()).apply {
                    controlParameter = initParameter(it)
                    listener = this@MidiPartEditFragment
                })
            }
        }
        controlGroups.add(binding!!.cvgMidiRcv)
    }

    private fun openNameEditDialog() {
        val currentName = getCurrentVoiceName()
        val layout = layoutInflater.inflate(R.layout.dialog_add_directory, null)
        val etName = layout.findViewById<EditText>(R.id.etDirName).apply {
            setText(currentName)
            hint = "Voice Name"
        }
        layout.findViewById<TextInputLayout>(R.id.tilDirName).apply {
            hint = "Voice Name"
        }
        val title = layout.findViewById<TextView>(R.id.tvAddDirTitle)
        title.setText(R.string.voice_name)
        AlertDialog.Builder(requireContext()).apply {
            setView(layout)
            setNeutralButton("Cancel") { dialog, _ -> dialog.dismiss() }
            setPositiveButton("Save") { _, _ ->
                val name = etName.text.toString()
                if (name.isNotEmpty()) {
                    midiViewModel.channels.value!![midiViewModel.selectedChannel.value!!].voiceName =
                        name
                    midiViewModel.channels.value = midiViewModel.channels.value
                    if (midiViewModel.channels.value!![midiViewModel.selectedChannel.value!!].voiceType == MidiPart.VoiceType.QS300) {
                        qs300ViewModel.preset.value!!.voices[qs300ViewModel.voice.value!!].voiceName =
                            name
                    }
                }
            }
            show()
        }
    }

    private fun getCurrentVoiceName(): String {
        val part = midiViewModel.channels.value!![midiViewModel.selectedChannel.value!!]
        return part.voiceName.ifEmpty { getString(part.voiceNameRes) }
    }

    private fun openVoiceSelectionDialog() {
        val voiceSelectFragment = VoiceSelectionDialogFragment(
            OnVoiceItemSelectedListenerImpl.MidiPartImpl(
                qs300ViewModel,
                midiViewModel,
                midiSession
            )
        ).apply {
            val selectedPart = midiViewModel.channels.value!![midiViewModel.selectedChannel.value!!]
            val voiceType = selectedPart.voiceType
            val voiceCategory =
                when (voiceType) {
                    MidiPart.VoiceType.NORMAL -> VoiceSelectionDialogFragment.CATEGORY_ID_NORMAL
                    MidiPart.VoiceType.DRUM -> VoiceSelectionDialogFragment.CATEGORY_ID_XGDRUM
                    MidiPart.VoiceType.QS300 -> VoiceSelectionDialogFragment.CATEGORY_ID_QS300
                    MidiPart.VoiceType.SFX -> VoiceSelectionDialogFragment.CATEGORY_ID_SFX
                }
            arguments = Bundle().apply {
                putInt(
                    VoiceSelectionDialogFragment.ARG_START_CATEGORY,
                    voiceCategory
                )
                putString(
                    VoiceSelectionDialogFragment.ARG_START_VOICE,
                    if (voiceType == MidiPart.VoiceType.QS300) {
                        qs300ViewModel.preset.value!!.name
                    } else {
                        this@MidiPartEditFragment.requireContext().getString(
                            selectedPart.voiceNameRes
                        )
                    }
                )
            }
        }
        voiceSelectFragment.show(childFragmentManager, VoiceSelectionDialogFragment.TAG)
    }

    override fun initParameter(paramId: Int): ControlParameter {
        val param = MidiParameter::descriptionRes findBy paramId
        return ControlParameter(
            getString(param!!.descriptionRes),
            param,
            midiViewModel.channels.value!![midiViewModel.selectedChannel.value!!].getPropertyValue(
                param.reflectedField
            )
        )
    }

    override fun onParameterChanged(controlParameter: ControlParameter, isTouching: Boolean) {
        if (controlParameter.nameRes != currentParam?.descriptionRes) {
            currentParam = MidiParameter::addrLo findBy controlParameter.addr.toByte()
        }
        midiViewModel.channels.value!![midiViewModel.selectedChannel.value!!].setProperty(
            currentParam!!.reflectedField,
            controlParameter.value.toByte()
        )
        midiViewModel.channels.value = midiViewModel.channels.value
        midiSession.send(getParamChangeMessage(controlParameter, isTouching))
    }

    private fun getParamChangeMessage(
        controlParameter: ControlParameter,
        isTouching: Boolean
    ): MidiMessage {

        // First check if nrpn param
        if (currentParam?.nrpn != null) {
            if (!isNrpnActive) {
                activateNRPN()
            } else {
                if (!isTouching) {
                    isNrpnActive = false
                    deactivateNRPN()
                    return MidiMessage(null, 0)
                }
            }
            return MidiMessageUtility.getControlChange(
                midiViewModel.selectedChannel.value!!,
                MidiControlChange.DATA_ENTRY_MSB.controlNumber, // Todo: <- verify this is right
                controlParameter.value.toByte()
            )
        }

        // Then check if control change param
        if (currentParam?.controlChange != null) {
            // If pan, check for Random (need to send XG Param change)
            if (currentParam != MidiParameter.PAN || controlParameter.value != 0) {
                return MidiMessageUtility.getControlChange(
                    midiViewModel.selectedChannel.value!!,
                    currentParam!!.controlChange!!.controlNumber,
                    controlParameter.value.toByte()
                )
            }
        }

        return MidiMessageUtility.getXGParamChange(
            midiViewModel.selectedChannel.value!!,
            currentParam!!,
            controlParameter.value.toByte()
        )
    }

    private fun activateNRPN() {
        isNrpnActive = true
        midiSession.send(
            MidiMessageUtility.getNRPNSet(
                midiViewModel.selectedChannel.value!!,
                currentParam!!.nrpn!!
            )
        )
    }

    private fun deactivateNRPN() {
        isNrpnActive = false
        midiSession.send(MidiMessageUtility.getNRPNClear(midiViewModel.selectedChannel.value!!))
    }
}