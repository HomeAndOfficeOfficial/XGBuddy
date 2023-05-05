package com.example.xgbuddy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.xgbuddy.adapter.PartsListAdapter
import com.example.xgbuddy.databinding.FragmentPartsBinding

class PartsFragment : Fragment() {

    private val midiViewModel: MidiViewModel by activityViewModels()

    private lateinit var partsAdapter: PartsListAdapter

    private val binding: FragmentPartsBinding by lazy {
        FragmentPartsBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        partsAdapter = PartsListAdapter(requireContext(), midiViewModel, binding.llParts)
        initObservers()
        return binding.root
    }

    private fun initObservers() {
        midiViewModel.channels.observe(viewLifecycleOwner) {
            partsAdapter.updateRow(it[midiViewModel.selectedChannel.value!!])
        }
        midiViewModel.selectedChannel.observe(viewLifecycleOwner) {
            partsAdapter.selectRow(it)
        }
    }
}