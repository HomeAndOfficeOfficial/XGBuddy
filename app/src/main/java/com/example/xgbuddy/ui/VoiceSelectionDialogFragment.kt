package com.example.xgbuddy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.xgbuddy.viewmodel.QS300ViewModel
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class VoiceSelectionDialogFragment : DialogFragment() {

    @Inject
    lateinit var midiSession: MidiSession

    private val midiViewModel: MidiViewModel by activityViewModels()
    private val qs300ViewModel: QS300ViewModel by activityViewModels()

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
            buildCompleteVoiceList(),
            this::updateSelectedVoice
        )
    }

    private fun buildCompleteVoiceList(): List<Any> = mutableListOf<Any>().apply {
        addAll(XGNormalVoice.values())
        addAll(XGDrumKit.values())
        addAll(SFXNormalVoice.values())
        addAll(qs300ViewModel.presets)
    }.toList()

    private fun setupCategoryButtons() {
        binding.bgVoiceCategory.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                updateFilterButtons(checkedId)
                updateAdapterCategory(checkedId)
            }
        }
        binding.bgSecFilter.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                voiceListAdapter.updateSecondaryFilter()
            }
        }
        setInitialCategory()
    }

    private fun setInitialCategory() {
        val startCategoryId = arguments?.getInt(ARG_START_CATEGORY) ?: 0
        var categorySelected = false
        binding.bgVoiceCategory.children.forEach {
            val isCategoryId = startCategoryId == it.id
            (it as MaterialButton).isChecked = isCategoryId
            categorySelected = categorySelected || isCategoryId
        }
        if (!categorySelected) {
            binding.bgVoiceCategory.check(binding.bXGVoice.id)
        }
    }

    private fun updateFilterButtons(categoryId: Int) {
        binding.bgSecFilter.check(binding.bSecAll.id)
        when (categoryId) {

            /*
               Need some kind of data model that contains a list of values to map to buttons for the
               button group.
               For qs300, this is symbols (!@#$), nunmbers (0-9), and one for each alpha letter
               For xg voice, instrument group names would be used.
               Might just be as simple as defining two lists with the appropriate values

               The problem comes when trying to identify which button was pressed based on an id.
               I guess I'll need to maintain a list of pairs - id, filter value

             Another option is to create multiple buitton groups in xml.
             Just define all the buttons there and... maybe create some kind of filter item enum.
             Actually, this would be a good exercise to figure out sealed classes.
             There would be two types an XGVoiceFilterItem and a QS300PresetFilterItem or something.

             Each member of both classes would have an id field. This would be the view id of the
             corresponding button. QS type would have a regex field, defining the regex to use for
             the filter. XG type would have an instrument group field.

             So that's the path forward, I'll need to investigate sealed classes a little bit.

             The only downside is its like 30-40 buttons in the xml, but whatever.

             */
            CATEGORY_ID_NORMAL -> populateSecondaryFilterGroup(normalVoiceStuff)
            CATEGORY_ID_QS300 -> populateSecondaryFilterGroup(qs300Stuff)
            else -> depopulateSecondaryFilterGroup()

        }
        if (categoryId == CATEGORY_ID_NORMAL || categoryId == CATEGORY_ID_QS300) {
            binding.svSecondaryFilter.visibility = View.VISIBLE
            while (binding.bgSecFilter.childCount > 1) {
                binding.bgSecFilter.removeViewAt(1)
            }

        }
    }

    private fun populateSecondaryFilterGroup() {
        //
    }

    private fun depopulateSecondaryFilterGroup() {
        binding.svSecondaryFilter.visibility = View.INVISIBLE
        while (binding.bgSecFilter.childCount > 1) {
            binding.bgSecFilter.removeViewAt(1)
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
            QS300 -> {
                /**
                 * To account for multiple voices, I'll need to do some additional work here.
                 * I believe there is one voice per midi part
                 * I think some of the presets are made of multiple voices.
                 *
                 * I need to figure out the rules that dictate elements/voice, voice/part.
                 *
                 * I think i'll probably have to update adjacent parts when switching to a qs300
                 * preset to accommodate multiple voices.
                 *
                 * If I change one voice back to an XG parameter, I don't think there will be much
                 * of a problem when updating a parameter on the qs voice, because it should only
                 * send a bulk dump for that voice, and not every voice in the preset.
                 */
                val preset = qs300ViewModel.presets[voiceIndex]
                val selectedChannel = midiViewModel.selectedChannel.value!!
                qs300ViewModel.preset.value = preset
                qs300ViewModel.voice.value = 0
                if (preset.voices.size > 1) {
                    if (selectedChannel + 1 == updatedPartsList.size) {
                        updatedPartsList[selectedChannel - 1].changeQS300Voice(preset.voices[0], 0)
                        updatedPart.changeQS300Voice(preset.voices[1], 1)
                        qs300ViewModel.voice.value = 1
                    } else {
                        updatedPart.changeQS300Voice(preset.voices[0], 0)
                        updatedPartsList[selectedChannel + 1].changeQS300Voice(preset.voices[1], 1)
                    }
                } else {
                    updatedPart.changeQS300Voice(preset.voices[0], 0)
                }
                midiViewModel.channels.value = updatedPartsList
                preset.voices.forEachIndexed { index, voice ->
                    /**
                     * TODO: Along with sending a bulk dump, there will likely have to be some
                     *  channel initialization messages. Like if there is more than one voice, make
                     *  sure both midi part are receiving the same channel, set to the same mode,
                     *  etc.
                     */
                    midiSession.sendBulkMessage(MidiMessageUtility.getQS300BulkDump(voice, index))
                    midiSession.sendBulkMessage(
                        MidiMessageUtility.getQS300VoiceSelection(
                            selectedChannel + index,
                            index,
                        )
                    )
                }
            }
        }
        dismiss()
    }

    companion object {
        const val TAG = "VoiceSelectionDialogFragment"
        const val ARG_START_CATEGORY = "argStartCategoryId"
        const val ARG_START_VOICE = "argStartVoice"
        const val CATEGORY_ID_NORMAL = R.id.bXGVoice
        const val CATEGORY_ID_XGDRUM = R.id.bXGDrum
        const val CATEGORY_ID_SFX = R.id.bXGSfx
        const val CATEGORY_ID_QS300 = R.id.bQs300
        private val qs300FilterLabels = listOf(
            "!@#$%",
            "0-9",
            "a",
        )
    }
}