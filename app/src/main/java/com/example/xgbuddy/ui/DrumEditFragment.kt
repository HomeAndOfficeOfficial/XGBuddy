package com.example.xgbuddy.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.xgbuddy.MidiSession
import com.example.xgbuddy.adapter.DrumVoiceRecyclerAdapter
import com.example.xgbuddy.data.MidiConstants
import com.example.xgbuddy.data.xg.XGDrumKit
import com.example.xgbuddy.databinding.FragmentDrumEditBinding
import com.example.xgbuddy.util.EnumFinder.findBy
import com.example.xgbuddy.util.MidiMessageUtility
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DrumEditFragment : Fragment(), DrumVoiceRecyclerAdapter.OnDrumClickListener {

    @Inject
    lateinit var midiSession: MidiSession

    private val midiViewModel: MidiViewModel by activityViewModels()
    private val binding: FragmentDrumEditBinding by lazy {
        FragmentDrumEditBinding.inflate(layoutInflater)
    }
    private val drumVoiceAdapter: DrumVoiceRecyclerAdapter by lazy {
        DrumVoiceRecyclerAdapter(
            midiViewModel.selectedDrumVoice.value!!,
            midiViewModel.channels.value!![midiViewModel.selectedChannel.value!!]
                .drumVoices!!.toMutableList(),
            this
        )
    }

    private var isSpinnerUpdating = true
    private var currentDrumProgram: Byte = -1

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        midiViewModel.selectedDrumVoice.observe(viewLifecycleOwner) {
            drumVoiceAdapter.updateSelection(it)
        }
        midiViewModel.channels.observe(viewLifecycleOwner) {
            val voiceIndex = midiViewModel.selectedDrumVoice.value!!
            val viewModelProgram = it[midiViewModel.selectedChannel.value!!].programNumber
            if (currentDrumProgram < 0) {
                currentDrumProgram = viewModelProgram
            }
            if (viewModelProgram != currentDrumProgram) {
                currentDrumProgram = viewModelProgram
                drumVoiceAdapter.apply {
                    drumVoices = it[midiViewModel.selectedChannel.value!!]
                        .drumVoices!!.toMutableList()
                    notifyDataSetChanged()
                }
            } else {
                drumVoiceAdapter.updateValues(
                    voiceIndex,
                    it[midiViewModel.selectedChannel.value!!]
                        .drumVoices!![voiceIndex]
                )
            }
        }
        binding.rvDrumVoices.apply {
            layoutManager =
                GridLayoutManager(requireContext(), 6, GridLayoutManager.VERTICAL, false)
            adapter = drumVoiceAdapter
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setupSpinner()
    }

    override fun onPause() {
        super.onPause()
        binding.spDrumKit.onItemSelectedListener = null
    }

    private fun setupSpinner() {
        // Program number for drum kits is just 0-8 sequentially
        binding.spDrumKit.apply {
            val programNumber =
                midiViewModel.channels.value!![midiViewModel.selectedChannel.value!!]
                    .programNumber
            setSelection(
                (XGDrumKit::programNumber findBy programNumber)!!.ordinal
            )
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (isSpinnerUpdating) {
                        isSpinnerUpdating = false
                    } else {
                        val drumKit = XGDrumKit::ordinal findBy position
                        midiViewModel.channels.value!![midiViewModel.selectedChannel.value!!].setDrumKit(
                            drumKit!!
                        )
                        midiViewModel.channels.value = midiViewModel.channels.value
                        midiSession.send(
                            MidiMessageUtility.getDrumKitChange(
                                midiViewModel.selectedChannel.value!!,
                                drumKit
                            )
                        )
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }

    }

    override fun onStop() {
        super.onStop()
        currentDrumProgram = -1
    }

    override fun onDrumClicked(position: Int, fromButtonClick: Boolean) {
        if (binding.swSendDrumHit.isChecked && fromButtonClick) {
            midiSession.send(
                MidiMessageUtility.getDrumHit(
                    midiViewModel.selectedChannel.value!!,
                    (position + MidiConstants.XG_INITIAL_DRUM_NOTE).toByte()
                )
            )
        }
        midiViewModel.selectedDrumVoice.value = position
    }
}