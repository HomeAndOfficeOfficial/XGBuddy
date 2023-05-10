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
import com.example.xgbuddy.R
import com.example.xgbuddy.adapter.DrumVoiceRecyclerAdapter
import com.example.xgbuddy.data.xg.XGDrumKit
import com.example.xgbuddy.databinding.FragmentDrumEditBinding
import com.example.xgbuddy.util.EnumFinder.findBy

class DrumEditFragment : Fragment(), DrumVoiceRecyclerAdapter.OnDrumClickListener {

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
        setupSpinner()
        return binding.root
    }

    private fun setupSpinner() {
        // Program number for drum kits is just 0-8 sequentially
        binding.spDrumKit.apply {
            setSelection(
                midiViewModel.channels.value!![midiViewModel.selectedChannel.value!!]
                    .programNumber.toInt()
            )
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    midiViewModel.channels.value!![midiViewModel.selectedChannel.value!!].setDrumKit(
                        (XGDrumKit::programNumber findBy position.toByte())!!
                    )
                    midiViewModel.channels.value = midiViewModel.channels.value

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
        // If not muted and fromButtonClick, send drum note on signal
        midiViewModel.selectedDrumVoice.value = position
    }
}