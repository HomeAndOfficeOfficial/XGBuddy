package com.example.xgbuddy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.view.children
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.xgbuddy.R
import com.example.xgbuddy.adapter.VoiceListAdapter
import com.example.xgbuddy.data.xg.XGNormalVoice
import com.example.xgbuddy.databinding.FragmentVoiceSelectionDialogBinding

class VoiceSelectionDialogFragment : DialogFragment() {

    private val midiViewModel: MidiViewModel by activityViewModels()
    private val binding: FragmentVoiceSelectionDialogBinding by lazy {
        FragmentVoiceSelectionDialogBinding.inflate(layoutInflater)
    }

    private lateinit var voiceListAdapter: VoiceListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initAdapter()
        binding.rvVoiceSelect.apply {
            adapter = voiceListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        setupCategoryButtons()
        return binding.root
    }

    private fun initAdapter() {
        voiceListAdapter = VoiceListAdapter(
            XGNormalVoice.values().toList() // TODO: Add other voice types
        ) {
            /**
             * TODO: Not sure the best way to go about "selecting" the voice.
             *  Probably need to wait until all the data structures are sorted out.
             */
            updateSelectedVoice(it)
        }
    }

    private fun setupCategoryButtons() {
        binding.rgVoiceCategory.setOnCheckedChangeListener { _, checkedId ->
            updateAdapterCategory(checkedId)
        }
        setInitialCategory()
    }

    private fun setInitialCategory() {
        val startCategoryId = arguments?.getInt(ARG_START_CATEGORY) ?: 0
        var categorySelected = false
        binding.rgVoiceCategory.children.forEach {
            val isCategoryId = startCategoryId == it.id
            (it as RadioButton).isChecked = isCategoryId
            categorySelected = categorySelected || isCategoryId
        }
        if (!categorySelected) {
            binding.rbXGVoice.isSelected = true
        }
    }

    private fun updateAdapterCategory(categoryId: Int) {
        val category = when (categoryId) {
            CATEGORY_ID_NORMAL -> VoiceListAdapter.VoiceListCategory.XG_NORMAL
            CATEGORY_ID_XGDRUM -> VoiceListAdapter.VoiceListCategory.XG_DRUM
            CATEGORY_ID_SFX -> VoiceListAdapter.VoiceListCategory.SFX
            CATEGORY_ID_QS300 -> VoiceListAdapter.VoiceListCategory.QS300 // Ugh, might have to make a complete list of QS300 voice presets
            else -> null
        }
        category?.let { voiceListAdapter.updateCategory(it) }
    }

    private fun updateSelectedVoice(voiceIndex: Int) {
        // TODO: Update viewmodel
        dismiss()
    }

    companion object {
        const val TAG = "VoiceSelectionDialogFragment"
        const val ARG_START_CATEGORY = "argStartCategoryId"
        const val ARG_START_VOICE = "argStartVoice"
        const val CATEGORY_ID_NORMAL = R.id.rbXGVoice
        const val CATEGORY_ID_XGDRUM = R.id.rbXGDrum
        const val CATEGORY_ID_SFX = R.id.rbXGSfx
        const val CATEGORY_ID_QS300 = R.id.rbQs300
    }
}