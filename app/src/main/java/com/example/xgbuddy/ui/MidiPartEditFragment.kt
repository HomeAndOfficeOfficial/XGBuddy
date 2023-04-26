package com.example.xgbuddy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.xgbuddy.data.ControlParameter
import com.example.xgbuddy.data.MidiMessage
import com.example.xgbuddy.data.MidiParameter
import com.example.xgbuddy.data.MidiPart
import com.example.xgbuddy.data.xg.XGControlParameter
import com.example.xgbuddy.databinding.FragmentMidiPartEditBinding
import com.example.xgbuddy.ui.custom.SwitchControlView
import com.example.xgbuddy.util.EnumFinder.findBy
import com.example.xgbuddy.util.MidiMessageUtility

class MidiPartEditFragment : ControlBaseFragment() {

    private val midiViewModel: MidiViewModel by activityViewModels()
    private val binding: FragmentMidiPartEditBinding by lazy {
        FragmentMidiPartEditBinding.inflate(layoutInflater)
    }

    private var currentParam: MidiParameter? = null

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
            updateViews(it)
        }
        return binding.root
    }

    private fun updateViews(parts: MutableList<MidiPart>) {
        val channel = midiViewModel.selectedChannel.value
        /*
            I think the only view that needs to be updated when the viewModel is
            observed is the voice spinner. Everything else should update itself.

            For now anyway. This may change when I start monitoring incoming messages.
            It will depend on how these fragments are notified of incoming messages.
            This is difficult because since there are so many data points, I'm going against
            the viewModel pattern a bit by sending messages as a direct result of view
            interaction. I should be updating the viewmodel then sending messages based on
            what was updated. But there are way too many things to account for, so that's not
            really feasible.

            Anyway I'll just update the voice field for now.
         */

        binding.etPartVoiceName.setText(parts[channel!!].voiceNameRes)

    }

    private fun initControlGroups() {
        binding.apply {
            // TODO: Add RCV_CHANNEL as extra
            initControlGroup(cvgMidiMain, shouldStartExpanded = true)

            // TODO: Add detune as extra
            initControlGroup(cvgMidiPitch)

            initControlGroup(cvgMidiEG)
            initControlGroup(cvgMidiFx)

            // TODO: Add porta_switch as extra
            initControlGroup(cvgMidiNote)

            initControlGroup(cvgMidiPat)
            initControlGroup(cvgMidiCat)
            initControlGroup(cvgMidiBend)
            initControlGroup(cvgMidiMod)

            // TODO: Add controller number as extra for ac1 and ac2
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

    override fun onParameterChanged(controlParameter: ControlParameter) {
        if (controlParameter.name != currentParam?.name) {
            currentParam = MidiParameter::addrLo findBy controlParameter.addr.toByte()
        }
        midiViewModel.channels.value!![midiViewModel.selectedChannel.value!!].setProperty(
            currentParam!!.reflectedField,
            controlParameter.value
        )
        midiSession.send(getParamChangeMessage(controlParameter))
    }

    private fun getParamChangeMessage(controlParameter: ControlParameter): MidiMessage {

        // First check if nrpn param
//        if (currentParam?.nrpn != null) {

//        }

        // Then check if control change param
        if (currentParam?.controlChange != null) {
            return MidiMessageUtility.getControlChange(
                midiViewModel.selectedChannel.value!!,
                currentParam!!.controlChange!!.controlNumber,
                controlParameter.value
            )
        }

        return MidiMessageUtility.getXGParamChange()
    }
}