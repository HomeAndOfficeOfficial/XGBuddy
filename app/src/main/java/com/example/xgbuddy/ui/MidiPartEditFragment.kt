package com.example.xgbuddy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.xgbuddy.databinding.FragmentMidiPartEditBinding

class MidiPartEditFragment : Fragment() {

    private val midiViewModel: MidiViewModel by activityViewModels()
    private val binding: FragmentMidiPartEditBinding by lazy {
        FragmentMidiPartEditBinding.inflate(layoutInflater)
    }


    /**
     * The first control should probably a button that lets you choose the voice.
     * Since a MidiPart can contain a lot of different types of voices (QS300, XG normal, sfx,
     * drum), I'd like to have a fragment dedicated to organizing and displaying these views.
     *
     * This would most likely be a tabbed dialog fragment, with a tab for each of the voice
     * categories.
     *
     * I think there's a potential to add filters, subcategories, etc to make navigating the list
     * more convenient, but for now I'll keep it simple and just have a group of tabs, selecting the
     * tab changes the list that is displayed. I'll worry about adding sticky labels, subcategories,
     * and filters later.
     *
     * Anyway, that's all it's own fragment.
     *
     * Voice/Sound selection would be an EditText with softInputOnFocus disabled, and a click
     * listener to open that selection dialog.
     *
     * Below that, probably just all the midi main control groups, since I think these will be
     * present no matter what type of voice is selected.
     *
     */

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.etPartVoiceName.apply {
            showSoftInputOnFocus = false
            setOnClickListener { openVoiceSelectionDialog() }
        }
        midiViewModel.channels.observe(viewLifecycleOwner) {
            // update views here
        }
        return binding.root
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
}