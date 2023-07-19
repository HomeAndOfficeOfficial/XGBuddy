package com.yamahaw.xgbuddy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.yamahaw.xgbuddy.midi.MidiSession
import com.yamahaw.xgbuddy.data.buddy.ControlParameter
import com.yamahaw.xgbuddy.data.xg.SystemParameter
import com.yamahaw.xgbuddy.databinding.FragmentSystemBinding
import com.yamahaw.xgbuddy.ui.custom.ParameterControlView
import com.yamahaw.xgbuddy.ui.custom.SliderControlView
import com.yamahaw.xgbuddy.util.MidiMessageUtility
import com.yamahaw.xgbuddy.viewmodel.MidiViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SystemFragment : Fragment() {

    @Inject
    lateinit var midiSession: MidiSession

    private val midiViewModel: MidiViewModel by activityViewModels()
    private val binding: FragmentSystemBinding by lazy {
        FragmentSystemBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setupViewParams()
        initControlListeners()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.apply {
            scvMasterVolume.forceSeekbarUpdate()
            scvTranspose.forceSeekbarUpdate()
            scvMasterTune.forceSeekbarUpdate()
        }
    }

    private fun setupViewParams() {
        binding.apply {
            setupControlView(scvMasterVolume, SystemParameter.MASTER_VOLUME, midiViewModel.volume)
            setupControlView(scvMasterTune, SystemParameter.MASTER_TUNE, midiViewModel.tuning)
            setupControlView(scvTranspose, SystemParameter.TRANSPOSE, midiViewModel.transpose)
        }
    }

    private fun setupControlView(view: SliderControlView, param: SystemParameter, value: Int) {
        view.apply {
            controlParameter = ControlParameter(getString(param.nameRes), param, value)
            shouldReportAllTouchEvents = false
            isRealtimeControl = false
            this.value = value
        }
    }

    private fun initControlListeners() {
        binding.apply {
            scvMasterVolume.listener =
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
            bResetControls.setOnClickListener(this@SystemFragment::resetSystemControls)
        }
    }

    private fun resetDrums(v: View) {
        // Need to investigate to see how this works
    }

    private fun resetParams(v: View) {
        // Add a method to MidiPart which sets all its parameters to default values
        // Further investigation necessary to determine logic for drums/qs300
    }

    private fun resetSetup(v: View) {
        midiViewModel.resetToDefaultSetup()
        midiSession.send(MidiMessageUtility.getXGSystemOn())
    }

    private fun sendAllOff(v: View) {
        midiSession.send(MidiMessageUtility.getAllOff())
    }

    private fun resetSystemControls(v: View) {
        midiViewModel.apply {
            volume = SystemParameter.MASTER_VOLUME.default
            tuning = SystemParameter.MASTER_TUNE.default
            transpose = SystemParameter.TRANSPOSE.default
            midiSession.send(
                listOf(
                    MidiMessageUtility.getMasterVolumeChange(volume),
                    MidiMessageUtility.getTuningChange(tuning),
                    MidiMessageUtility.getTransposeChange(transpose)
                )
            )
        }
        updateViews()
    }

    private fun updateViews() {
        binding.apply {
            scvMasterVolume.value = midiViewModel.volume
            scvTranspose.value = midiViewModel.transpose
            scvMasterTune.value = midiViewModel.tuning
        }
    }
}