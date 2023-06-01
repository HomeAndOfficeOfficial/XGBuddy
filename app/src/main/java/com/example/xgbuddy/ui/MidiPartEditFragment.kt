package com.example.xgbuddy.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import androidx.fragment.app.activityViewModels
import com.example.xgbuddy.data.ControlParameter
import com.example.xgbuddy.data.MidiControlChange
import com.example.xgbuddy.data.MidiMessage
import com.example.xgbuddy.data.gm.MidiParameter
import com.example.xgbuddy.data.gm.MidiPart
import com.example.xgbuddy.data.xg.XGControlParameter
import com.example.xgbuddy.databinding.FragmentMidiPartEditBinding
import com.example.xgbuddy.ui.custom.SwitchControlView
import com.example.xgbuddy.util.EnumFinder.findBy
import com.example.xgbuddy.util.MidiMessageUtility

class MidiPartEditFragment : ControlBaseFragment() {

    override val binding: FragmentMidiPartEditBinding by lazy {
        FragmentMidiPartEditBinding.inflate(layoutInflater)
    }

    private val midiViewModel: MidiViewModel by activityViewModels()

    private var currentParam: MidiParameter? = null
    private var isNrpnActive = false
    private var wasSpinnerTouched = false

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initControlGroups()
        binding.etPartVoiceName.apply {
            showSoftInputOnFocus = false
            setOnClickListener { openVoiceSelectionDialog() }
        }
        midiViewModel.channels.observe(viewLifecycleOwner) {
            val channel = midiViewModel.selectedChannel.value
            // TODO: Find a way to change the edit text when the voice is selected.
            binding.etPartVoiceName.setText(it[channel!!].voiceNameRes)
        }
        midiViewModel.selectedChannel.observe(viewLifecycleOwner) {
            updateViews(midiViewModel.channels.value!![it])
        }

        // Todo: clean this up a little.
        binding.spRcvCh.setSelection(
            midiViewModel.channels.value!![midiViewModel.selectedChannel.value!!]
                .receiveChannel.toInt()
        )
        binding.spRcvCh.apply {
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

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun updateViews(midiPart: MidiPart) {
        binding.etPartVoiceName.setText(midiPart.voiceNameRes)
        binding.spRcvCh.setSelection(midiPart.receiveChannel.toInt())
        controlGroups.forEach {
            it.updateViews(midiPart)
        }
    }

    private fun initControlGroups() {
        binding.apply {
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
        binding.cvgMidiRcv.apply {
            isInteractive = true
            collapse()
            controlItemIds.forEach {
                addControlView(SwitchControlView(requireContext()).apply {
                    controlParameter = initParameter(it)
                    listener = this@MidiPartEditFragment
                })
            }
        }
        controlGroups.add(binding.cvgMidiRcv)
    }

    private fun openVoiceSelectionDialog() {
        val voiceSelectFragment = VoiceSelectionDialogFragment().apply {
            arguments = Bundle().apply {
                putInt(
                    VoiceSelectionDialogFragment.ARG_START_CATEGORY,
                    VoiceSelectionDialogFragment.CATEGORY_ID_NORMAL
                )
            }
        }
        voiceSelectFragment.show(childFragmentManager, VoiceSelectionDialogFragment.TAG)
    }

    override fun initParameter(paramId: Int): ControlParameter {
        val param = MidiParameter::descriptionRes findBy paramId
        return XGControlParameter(
            param!!,
            midiViewModel.channels.value!![midiViewModel.selectedChannel.value!!].getPropertyValue(
                param.reflectedField
            )
        )
    }

    override fun onParameterChanged(controlParameter: ControlParameter, isTouching: Boolean) {
        if (controlParameter.name != currentParam?.name) {
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
            return MidiMessageUtility.getControlChange(
                midiViewModel.selectedChannel.value!!,
                currentParam!!.controlChange!!.controlNumber,
                controlParameter.value.toByte()
            )
        }

        // TODO: Verify parameters are changing
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