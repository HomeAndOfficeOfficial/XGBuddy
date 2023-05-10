package com.example.xgbuddy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.activityViewModels
import com.example.xgbuddy.R
import com.example.xgbuddy.data.ControlParameter
import com.example.xgbuddy.data.MidiConstants
import com.example.xgbuddy.data.MidiControlChange
import com.example.xgbuddy.data.MidiMessage
import com.example.xgbuddy.data.xg.DrumControlParameter
import com.example.xgbuddy.data.xg.DrumVoice
import com.example.xgbuddy.data.xg.DrumVoiceParameter
import com.example.xgbuddy.ui.custom.ControlViewGroup
import com.example.xgbuddy.util.EnumFinder.findBy
import com.example.xgbuddy.util.MidiMessageUtility

class DrumParamEditFragment : ControlBaseFragment() {

    private val viewModel: MidiViewModel by activityViewModels()

    private var currentParam: DrumVoiceParameter? = null
    private var isNrpnActive = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = layoutInflater.inflate(R.layout.fragment_drum_param_edit, container, false)
        findViews(v)
        initControlGroups()
        viewModel.selectedDrumVoice.observe(viewLifecycleOwner) {
            updateViews(
                viewModel.channels.value!![viewModel.selectedChannel.value!!]
                    .drumVoices!![it]
            )
        }
        return v
    }

    private fun initControlGroups() {
        initControlGroup(
            cvgDrumParam,
            shouldStartExpanded = true,
            shouldReceiveAllTouchCallbacks = true
        )
        initControlGroup(
            cvgDrumParam2,
            shouldStartExpanded = true,
            extraChildren = llDrumParam2Extras
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
            viewModel.channels.value!![viewModel.selectedChannel.value!!].drumVoices!![viewModel.selectedDrumVoice.value!!].getPropertyValue(
                param!!.reflectedField
            )
        return DrumControlParameter(param, value)
    }

    override fun onParameterChanged(controlParameter: ControlParameter, isTouching: Boolean) {
        if (controlParameter.name != currentParam?.name) {
            currentParam = DrumVoiceParameter::getAddrLo findBy controlParameter.addr.toByte()
        }
        val drumVoice = viewModel.channels.value!![viewModel.selectedChannel.value!!]
            .drumVoices!![viewModel.selectedDrumVoice.value!!]
        drumVoice.setProperty(
            currentParam!!.reflectedField,
            controlParameter.value
        )
        viewModel.channels.value = viewModel.channels.value
        midiSession.send(getParamChangeMessage(controlParameter, isTouching))
    }

    private fun getParamChangeMessage(
        controlParameter: ControlParameter,
        isTouching: Boolean
    ): MidiMessage {

        // First check if nrpn param
        if (currentParam?.nrpn != null) {
            if (!isNrpnActive) {
                activateNRPN(viewModel.selectedDrumVoice.value!! + MidiConstants.XG_INITIAL_DRUM_NOTE)
            } else {
                if (!isTouching) {
                    isNrpnActive = false
                    deactivateNRPN()
                    return MidiMessage(null, 0)
                }
            }
            return MidiMessageUtility.getControlChange(
                viewModel.selectedChannel.value!!,
                MidiControlChange.DATA_ENTRY_MSB.controlNumber, // Todo: <- verify this is right
                controlParameter.value
            )
        }

        // TODO: Send required params to param change
        return MidiMessageUtility.getXGParamChange()
    }

    private fun activateNRPN(drumNote: Int) {
        isNrpnActive = true
        midiSession.send(
            MidiMessageUtility.getNRPNSet(
                viewModel.selectedChannel.value!!,
                currentParam!!.nrpn!!,
                drumNote.toByte()
            )
        )
    }

    private fun deactivateNRPN() {
        isNrpnActive = false
        midiSession.send(MidiMessageUtility.getNRPNClear(viewModel.selectedChannel.value!!))
    }

    private fun findViews(v: View) {
        cvgDrumParam = v.findViewById(R.id.cvgDrumParam)
        cvgDrumParam2 = v.findViewById(R.id.cvgDrumParam2)
        llDrumParam2Extras = v.findViewById(R.id.drumParam2Extras)
    }

    private lateinit var cvgDrumParam: ControlViewGroup
    private lateinit var cvgDrumParam2: ControlViewGroup
    private lateinit var llDrumParam2Extras: LinearLayout
}