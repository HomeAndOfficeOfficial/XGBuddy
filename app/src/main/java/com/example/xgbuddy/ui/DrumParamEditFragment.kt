package com.example.xgbuddy.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.xgbuddy.R
import com.example.xgbuddy.data.ControlParameter
import com.example.xgbuddy.data.MidiConstants
import com.example.xgbuddy.data.MidiControlChange
import com.example.xgbuddy.data.MidiMessage
import com.example.xgbuddy.data.xg.DrumControlParameter
import com.example.xgbuddy.data.xg.DrumVoice
import com.example.xgbuddy.data.xg.DrumVoiceParameter
import com.example.xgbuddy.databinding.FragmentDrumParamEditBinding
import com.example.xgbuddy.util.EnumFinder.findBy
import com.example.xgbuddy.util.MidiMessageUtility

class DrumParamEditFragment : ControlBaseFragment<FragmentDrumParamEditBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDrumParamEditBinding =
        FragmentDrumParamEditBinding::inflate

    private var currentParam: DrumVoiceParameter? = null
    private var isNrpnActive = false

    override fun setupViews() {
        initControlGroups()
        midiViewModel.selectedDrumVoice.observe(viewLifecycleOwner) {
            updateViews(
                midiViewModel.channels.value!![midiViewModel.selectedChannel.value!!]
                    .drumVoices!![it]
            )
        }
        midiViewModel.channels.observe(viewLifecycleOwner) {
            updateViews(
                it[midiViewModel.selectedChannel.value!!]
                    .drumVoices!![midiViewModel.selectedDrumVoice.value!!]
            )
        }
    }

    private fun initControlGroups() {
        initControlGroup(
            binding!!.cvgDrumParam,
            shouldStartExpanded = true,
            shouldReceiveAllTouchCallbacks = true
        )
        initControlGroup(
            binding!!.cvgDrumParam2,
            shouldStartExpanded = true,
            extraChildren = binding!!.cvgDrumParam2.findViewById(R.id.drumParam2Extras) // llDrumParam2Extras
        )
    }

    private fun updateViews(drumVoice: DrumVoice) {
        controlGroups.forEach {
            it.updateViews(drumVoice)
        }
    }

    override fun initParameter(paramId: Int): ControlParameter {
        val param = DrumVoiceParameter::nameRes findBy paramId
        val value =
            midiViewModel.channels.value!![midiViewModel.selectedChannel.value!!].drumVoices!![midiViewModel.selectedDrumVoice.value!!].getPropertyValue(
                param!!.reflectedField
            )
        return DrumControlParameter(param, value)
    }

    override fun onParameterChanged(controlParameter: ControlParameter, isTouching: Boolean) {
        if (controlParameter.name != currentParam?.name) {
            currentParam = DrumVoiceParameter::getAddrLo findBy controlParameter.addr.toByte()
        }
        val drumVoice = midiViewModel.channels.value!![midiViewModel.selectedChannel.value!!]
            .drumVoices!![midiViewModel.selectedDrumVoice.value!!]
        drumVoice.setProperty(
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
                activateNRPN(midiViewModel.selectedDrumVoice.value!! + MidiConstants.XG_INITIAL_DRUM_NOTE)
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

        return MidiMessageUtility.getDrumParamChange(
            currentParam!!,
            0,
            midiViewModel.selectedDrumVoice.value!! + MidiConstants.XG_INITIAL_DRUM_NOTE,
            controlParameter.value.toByte()
        )
    }

    private fun activateNRPN(drumNote: Int) {
        isNrpnActive = true
        midiSession.send(
            MidiMessageUtility.getNRPNSet(
                midiViewModel.selectedChannel.value!!,
                currentParam!!.nrpn!!,
                drumNote.toByte()
            )
        )
    }

    private fun deactivateNRPN() {
        isNrpnActive = false
        midiSession.send(MidiMessageUtility.getNRPNClear(midiViewModel.selectedChannel.value!!))
    }
}