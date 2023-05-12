package com.example.xgbuddy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import androidx.fragment.app.activityViewModels
import com.example.xgbuddy.R
import com.example.xgbuddy.data.ControlParameter
import com.example.xgbuddy.data.MidiControlChange
import com.example.xgbuddy.data.MidiMessage
import com.example.xgbuddy.data.gm.MidiParameter
import com.example.xgbuddy.data.gm.MidiPart
import com.example.xgbuddy.data.xg.XGControlParameter
import com.example.xgbuddy.ui.custom.ControlViewGroup
import com.example.xgbuddy.ui.custom.SwitchControlView
import com.example.xgbuddy.util.EnumFinder.findBy
import com.example.xgbuddy.util.MidiMessageUtility

class MidiPartEditFragment : ControlBaseFragment() {

    private val midiViewModel: MidiViewModel by activityViewModels()

    private var currentParam: MidiParameter? = null
    private var isNrpnActive = false
    private var isSpinnerUpdating = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val v = layoutInflater.inflate(R.layout.fragment_midi_part_edit, container, false)
        findViews(v)
        initControlGroups()
        etPartVoiceName.apply {
            showSoftInputOnFocus = false
            setOnClickListener { openVoiceSelectionDialog() }
        }
        midiViewModel.channels.observe(viewLifecycleOwner) {
            val channel = midiViewModel.selectedChannel.value
            // TODO: Find a way to change the edit text when the voice is selected.
            etPartVoiceName.setText(it[channel!!].voiceNameRes)
        }
        midiViewModel.selectedChannel.observe(viewLifecycleOwner) {
            updateViews(midiViewModel.channels.value!![it])
        }

        // Todo: clean this up a little.
        spRcvCh.setSelection(
            midiViewModel.channels.value!![midiViewModel.selectedChannel.value!!]
                .receiveChannel.toInt()
        )
        spRcvCh.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (isSpinnerUpdating) {
                    isSpinnerUpdating = false
                } else {
                    midiViewModel.channels.value!![midiViewModel.selectedChannel.value!!].receiveChannel =
                        position.toByte()
                    midiViewModel.channels.value = midiViewModel.channels.value
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        return v
    }

    private fun updateViews(midiPart: MidiPart) {
        etPartVoiceName.setText(midiPart.voiceNameRes)
        isSpinnerUpdating = true
        spRcvCh.setSelection(midiPart.receiveChannel.toInt())
        controlGroups.forEach {
            it.updateViews(midiPart)
        }
    }

    private fun initControlGroups() {
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
        initReceiveSwitches()
    }

    private fun initReceiveSwitches() {
        cvgMidiRcv.apply {
            isInteractive = true
            collapse()
            controlItemIds.forEach {
                addControlView(SwitchControlView(requireContext()).apply {
                    controlParameter = initParameter(it)
                    listener = this@MidiPartEditFragment
                })
            }
        }
        controlGroups.add(cvgMidiRcv)
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
            controlParameter.value
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
                controlParameter.value
            )
        }

        // Then check if control change param
        if (currentParam?.controlChange != null) {
            return MidiMessageUtility.getControlChange(
                midiViewModel.selectedChannel.value!!,
                currentParam!!.controlChange!!.controlNumber,
                controlParameter.value
            )
        }

        // TODO: Verify parameters are changing
        return MidiMessageUtility.getXGParamChange(
            midiViewModel.selectedChannel.value!!,
            currentParam!!,
            controlParameter.value
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

    private fun findViews(v: View) {
        etPartVoiceName = v.findViewById(R.id.etPartVoiceName)
        cvgMidiMain = v.findViewById(R.id.cvgMidiMain)
        cvgMidiPitch = v.findViewById(R.id.cvgMidiPitch)
        cvgMidiEG = v.findViewById(R.id.cvgMidiEG)
        cvgMidiFx = v.findViewById(R.id.cvgMidiFx)
        cvgMidiNote = v.findViewById(R.id.cvgMidiNote)
        midiNoteExtras = v.findViewById(R.id.midiNoteExtras)
        cvgMidiPat = v.findViewById(R.id.cvgMidiPat)
        cvgMidiCat = v.findViewById(R.id.cvgMidiCat)
        cvgMidiBend = v.findViewById(R.id.cvgMidiBend)
        cvgMidiMod = v.findViewById(R.id.cvgMidiMod)
        cvgMidiAc1 = v.findViewById(R.id.cvgMidiAc1)
        cvgMidiAc2 = v.findViewById(R.id.cvgMidiAc2)
        cvgMidiScale = v.findViewById(R.id.cvgMidiScale)
        cvgMidiRcv = v.findViewById(R.id.cvgMidiRcv)
        cvgMidiCh = v.findViewById(R.id.cvgMidiCh)
        spRcvCh = v.findViewById(R.id.spRcvCh)
    }

    private lateinit var etPartVoiceName: EditText
    private lateinit var cvgMidiMain: ControlViewGroup
    private lateinit var cvgMidiPitch: ControlViewGroup
    private lateinit var cvgMidiEG: ControlViewGroup
    private lateinit var cvgMidiFx: ControlViewGroup
    private lateinit var cvgMidiNote: ControlViewGroup
    private lateinit var midiNoteExtras: LinearLayout
    private lateinit var cvgMidiPat: ControlViewGroup
    private lateinit var cvgMidiCat: ControlViewGroup
    private lateinit var cvgMidiBend: ControlViewGroup
    private lateinit var cvgMidiMod: ControlViewGroup
    private lateinit var cvgMidiAc1: ControlViewGroup
    private lateinit var cvgMidiAc2: ControlViewGroup
    private lateinit var cvgMidiScale: ControlViewGroup
    private lateinit var cvgMidiRcv: ControlViewGroup
    private lateinit var cvgMidiCh: ControlViewGroup
    private lateinit var spRcvCh: Spinner
}