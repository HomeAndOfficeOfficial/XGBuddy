package com.example.xgbuddy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.xgbuddy.adapter.PartsListAdapter
import com.example.xgbuddy.data.gm.MidiPart
import com.example.xgbuddy.databinding.FragmentPartsBinding
import com.example.xgbuddy.viewmodel.QS300ViewModel

class PartsFragment : Fragment() {

    private val midiViewModel: MidiViewModel by activityViewModels()
    private val qS300ViewModel: QS300ViewModel by activityViewModels()

    private lateinit var partsAdapter: PartsListAdapter

    private var binding: FragmentPartsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPartsBinding.inflate(layoutInflater)
        partsAdapter = PartsListAdapter(requireContext(), midiViewModel, binding!!.llParts)
        initObservers()
        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun initObservers() {
        midiViewModel.channels.observe(viewLifecycleOwner) {
            val selectedChannel = midiViewModel.selectedChannel.value!!
            partsAdapter.updateRow(it[selectedChannel])

            // Todo: Clean this up. This logic is duplicated from VoiceSelectionDialogFragment
            if (it[selectedChannel].voiceType == MidiPart.VoiceType.QS300) {
                val secondaryChannel =
                    if (selectedChannel + 1 == midiViewModel.channels.value!!.size) {
                        selectedChannel - 1
                    } else {
                        selectedChannel + 1
                    }
                partsAdapter.updateRow(it[secondaryChannel])
            }
        }
        midiViewModel.selectedChannel.observe(viewLifecycleOwner) {
            partsAdapter.selectRow(it)
            if (midiViewModel.channels.value!![it].voiceType == MidiPart.VoiceType.QS300) {
                qS300ViewModel.voice = midiViewModel.channels.value!![it].qs300VoiceNumber
            }
        }
    }
}