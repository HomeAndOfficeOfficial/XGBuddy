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
import com.example.xgbuddy.MidiSession
import com.example.xgbuddy.R
import com.example.xgbuddy.adapter.VoiceListAdapter
import com.example.xgbuddy.adapter.VoiceListAdapter.VoiceListCategory.*
import com.example.xgbuddy.data.xg.SFXNormalVoice
import com.example.xgbuddy.data.xg.XGDrumKit
import com.example.xgbuddy.data.xg.XGNormalVoice
import com.example.xgbuddy.databinding.FragmentVoiceSelectionDialogBinding
import com.example.xgbuddy.util.EnumFinder.findBy
import com.example.xgbuddy.util.MidiMessageUtility
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class VoiceSelectionDialogFragment : DialogFragment() {

    @Inject
    lateinit var midiSession: MidiSession

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

    override fun onResume() {
        super.onResume()
        val width = resources.getDimensionPixelSize(R.dimen.voice_select_dialog_width)
        val height = resources.getDimensionPixelSize(R.dimen.voice_select_dialog_height)
        dialog?.window?.let {
            it.setLayout(width, height)
            it.setBackgroundDrawableResource(R.drawable.popup_bg)
        }
    }

    private fun initAdapter() {
        voiceListAdapter = VoiceListAdapter(
            buildCompleteVoiceList(), // TODO: Add other voice types)
            this::updateSelectedVoice
        )
    }

    private fun buildCompleteVoiceList(): List<Any> = mutableListOf<Any>().apply {
        addAll(XGNormalVoice.values())
        addAll(XGDrumKit.values())
        addAll(SFXNormalVoice.values())
    }.toList()

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
            CATEGORY_ID_NORMAL -> XG_NORMAL
            CATEGORY_ID_XGDRUM -> XG_DRUM
            CATEGORY_ID_SFX -> SFX
            CATEGORY_ID_QS300 -> QS300 // Ugh, might have to make a complete list of QS300 voice presets
            else -> null
        }
        category?.let { voiceListAdapter.updateCategory(it) }
    }

    private fun updateSelectedVoice(
        voiceIndex: Int,
        voiceCategory: VoiceListAdapter.VoiceListCategory
    ) {
        val updatedPartsList = midiViewModel.channels.value!!
        val updatedPart = updatedPartsList[midiViewModel.selectedChannel.value!!]
        when (voiceCategory) {
            XG_NORMAL -> {
                (XGNormalVoice::ordinal findBy voiceIndex)?.let { xgVoice ->
                    updatedPart.changeXGVoice(xgVoice)
                    updatedPartsList[midiViewModel.selectedChannel.value!!] = updatedPart
                    midiViewModel.channels.value = updatedPartsList
                    midiSession.send(
                        MidiMessageUtility.getXGNormalVoiceChange(
                            updatedPart.ch,
                            xgVoice
                        )
                    )
                }
            }
            XG_DRUM -> {
                (XGDrumKit::ordinal findBy voiceIndex)?.let { drumKit ->
                    updatedPart.setDrumKit(drumKit)
                    updatedPartsList[midiViewModel.selectedChannel.value!!] = updatedPart
                    midiViewModel.channels.value = updatedPartsList
                    midiSession.send(MidiMessageUtility.getDrumKitChange(updatedPart.ch, drumKit))
                }
            }
            SFX -> {
                (SFXNormalVoice::ordinal findBy voiceIndex)?.let { sfx ->
                    updatedPart.changeSFXVoice(sfx)
                    updatedPartsList[midiViewModel.selectedChannel.value!!] = updatedPart
                    midiViewModel.channels.value = updatedPartsList
                    midiSession.send(
                        MidiMessageUtility.getSFXNormalVoiceChange(
                            updatedPart.ch,
                            sfx
                        )
                    )
                }
            }
            QS300 -> TODO()
        }
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