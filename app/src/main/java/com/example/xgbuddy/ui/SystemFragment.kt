package com.example.xgbuddy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.xgbuddy.MidiSession
import com.example.xgbuddy.databinding.FragmentSystemBinding
import com.example.xgbuddy.ui.custom.ParameterControlView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SystemFragment : Fragment() {

    @Inject
    lateinit var midiSession: MidiSession

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

    private fun setupViewParams() {
        binding.apply {
            scvMasterVol.apply {

            }
        }
    }

    private fun initControlListeners() {
        binding.apply {
            scvMasterVol.listener =
                ParameterControlView.OnParameterChangedListener { controlParameter, isTouching ->

                }
            scvTranspose.listener =
                ParameterControlView.OnParameterChangedListener { controlParameter, isTouching ->

                }
            scvMasterTune.listener =
                ParameterControlView.OnParameterChangedListener { controlParameter, isTouching ->

                }
            bResetDrum.setOnClickListener(this@SystemFragment::resetDrums)
            bResetParams.setOnClickListener(this@SystemFragment::resetParams)
            bInitSetup.setOnClickListener(this@SystemFragment::resetSetup)
            bPanic.setOnClickListener(this@SystemFragment::sendAllOff)
        }
    }

    private fun resetDrums(v: View) {

    }

    private fun resetParams(v: View) {

    }

    private fun resetSetup(v: View) {

    }

    private fun sendAllOff(v: View) {

    }

}