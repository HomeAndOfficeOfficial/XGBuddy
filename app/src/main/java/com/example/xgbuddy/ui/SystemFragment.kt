package com.example.xgbuddy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.xgbuddy.MidiSession
import com.example.xgbuddy.R
import com.example.xgbuddy.data.xg.SystemControlParameter
import com.example.xgbuddy.data.xg.SystemParameter
import com.example.xgbuddy.ui.custom.ParameterControlView
import com.example.xgbuddy.ui.custom.SliderControlView
import com.example.xgbuddy.util.MidiMessageUtility
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SystemFragment : Fragment() {

    @Inject
    lateinit var midiSession: MidiSession

    private val midiViewModel: MidiViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val v = layoutInflater.inflate(R.layout.fragment_system, container, false)
        findViews(v)
        setupViewParams()
        initControlListeners()
        return v
    }

    private fun setupViewParams() {
        setupControlView(scvMasterVol, SystemParameter.MASTER_VOLUME, midiViewModel.volume)
        setupControlView(scvMasterTune, SystemParameter.MASTER_TUNE, midiViewModel.tuning)
        setupControlView(scvTranspose, SystemParameter.TRANSPOSE, midiViewModel.transpose)
    }

    private fun setupControlView(view: SliderControlView, param: SystemParameter, value: Int) {
        view.apply {
            controlParameter = SystemControlParameter(param, value)
            shouldReportAllTouchEvents = false
            isRealtimeControl = false
            this.value = value
        }
    }

    private fun initControlListeners() {
        scvMasterVol.listener =
            ParameterControlView.OnParameterChangedListener { controlParameter, _ ->
                val volume = controlParameter.value
                midiViewModel.volume = volume
                midiSession.send(MidiMessageUtility.getMasterVolumeChange(volume))
            }
        scvTranspose.listener =
            ParameterControlView.OnParameterChangedListener { controlParameter, _ ->
                val transpose = controlParameter.value
                midiViewModel.transpose = transpose
                midiSession.send(MidiMessageUtility.getTransposeChange(transpose))
            }
        scvMasterTune.listener =
            ParameterControlView.OnParameterChangedListener { controlParameter, _ ->
                val tuning = controlParameter.value
                midiViewModel.tuning = tuning
                midiSession.send(MidiMessageUtility.getTuningChange(tuning))
            }
        bResetDrum.setOnClickListener(this@SystemFragment::resetDrums)
        bResetParams.setOnClickListener(this@SystemFragment::resetParams)
        bInitSetup.setOnClickListener(this@SystemFragment::resetSetup)
        bPanic.setOnClickListener(this@SystemFragment::sendAllOff)

    }

    private fun resetDrums(v: View) {
        // Need to investigate to see how this works
    }

    private fun resetParams(v: View) {
        // Add a method to MidiPart which sets all its parameters to default values
        // Further investigation necessary to determine logic for drums/qs300
    }

    private fun resetSetup(v: View) {
        // I think I may have a default setup method somewhere already?
    }

    private fun sendAllOff(v: View) {
        // Just need to send a message
    }

    private fun findViews(v: View) {
        scvMasterVol = v.findViewById(R.id.scvMasterVolume)
        scvMasterTune = v.findViewById(R.id.scvMasterTune)
        scvTranspose = v.findViewById(R.id.scvTranspose)
        bPanic = v.findViewById(R.id.bPanic)
        bInitSetup = v.findViewById(R.id.bInitSetup)
        bResetDrum = v.findViewById(R.id.bResetDrum)
        bResetParams = v.findViewById(R.id.bResetParams)
    }

    private lateinit var scvMasterVol: SliderControlView
    private lateinit var scvMasterTune: SliderControlView
    private lateinit var scvTranspose: SliderControlView
    private lateinit var bPanic: Button
    private lateinit var bInitSetup: Button
    private lateinit var bResetDrum: Button
    private lateinit var bResetParams: Button

}