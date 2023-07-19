package com.yamahaw.xgbuddy.ui.parts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.yamahaw.xgbuddy.adapter.PartsListAdapter
import com.yamahaw.xgbuddy.data.gm.MidiPart
import com.yamahaw.xgbuddy.databinding.FragmentPartsBinding
import com.yamahaw.xgbuddy.viewmodel.MidiViewModel
import com.yamahaw.xgbuddy.viewmodel.QS300ViewModel

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
            partsAdapter.updateAll(it)
        }
        midiViewModel.selectedChannel.observe(viewLifecycleOwner) {
            partsAdapter.selectRow(it)
            if (midiViewModel.channels.value!![it].voiceType == MidiPart.VoiceType.QS300) {
                if (midiViewModel.qsPartMap.containsKey(it)) {
                    qS300ViewModel.preset.value = midiViewModel.qsPartMap[it]
                } else {
                    // We're on a secondary channel. The selectedChannel - 1 must have a map entry
                    qS300ViewModel.preset.value = midiViewModel.qsPartMap[it-1]
                }
                qS300ViewModel.voice.value = midiViewModel.channels.value!![it].qs300VoiceNumber
            }
        }
    }

    companion object {
        const val TAG = "PartsFragment"
    }
}