package com.example.xgbuddy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.xgbuddy.R
import com.example.xgbuddy.adapter.DrumVoiceRecyclerAdapter
import com.example.xgbuddy.databinding.FragmentDrumEditBinding

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
            drumVoiceAdapter.updateValues(
                voiceIndex,
                midiViewModel.channels.value!![midiViewModel.selectedChannel.value!!]
                    .drumVoices!![voiceIndex]
            )
        }
        binding.rvDrumVoices.apply {
            layoutManager =
                GridLayoutManager(requireContext(), 6, GridLayoutManager.VERTICAL, false)
            adapter = drumVoiceAdapter
        }
        return binding.root
    }

    override fun onDrumClicked(position: Int, fromButtonClick: Boolean) {
        // If not muted and fromButtonClick, send drum note on signal
        midiViewModel.selectedDrumVoice.value = position
    }
}