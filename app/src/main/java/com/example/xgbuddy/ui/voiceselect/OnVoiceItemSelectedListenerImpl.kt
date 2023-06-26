package com.example.xgbuddy.ui.voiceselect

import com.example.xgbuddy.MidiSession
import com.example.xgbuddy.data.voiceselect.VoiceListCategory
import com.example.xgbuddy.data.xg.SFXNormalVoice
import com.example.xgbuddy.data.xg.XGDrumKit
import com.example.xgbuddy.data.xg.XGNormalVoice
import com.example.xgbuddy.ui.MidiViewModel
import com.example.xgbuddy.util.EnumFinder.findBy
import com.example.xgbuddy.util.MidiMessageUtility
import com.example.xgbuddy.viewmodel.QS300ViewModel

sealed class OnVoiceItemSelectedListenerImpl(
    protected val qs300ViewModel: QS300ViewModel,
    protected val midiViewModel: MidiViewModel,
    protected val midiSession: MidiSession
) : OnVoiceItemSelectedListener {
    class MidiPartImpl(
        qs300ViewModel: QS300ViewModel,
        midiViewModel: MidiViewModel,
        midiSession: MidiSession
    ) : OnVoiceItemSelectedListenerImpl(qs300ViewModel, midiViewModel, midiSession) {

        private fun removeQSParts() {
            val selectedChannel = midiViewModel.selectedChannel.value!!
            midiViewModel.qsPartMap[selectedChannel]?.let { preset ->
                if (preset.voices.size > 1) {
                    midiViewModel.channels.value!![selectedChannel + 1].changeXGVoice(XGNormalVoice.GRAND_PNO)
                }
                midiViewModel.qsPartMap.remove(selectedChannel)
            }
        }

        override fun onVoiceItemSelected(index: Int, category: VoiceListCategory) {
            val updatedPartsList = midiViewModel.channels.value!!
            val selectedChannel = midiViewModel.selectedChannel.value!!
            val updatedPart = updatedPartsList[selectedChannel]
            if (category != VoiceListCategory.QS300) {
                removeQSParts()
            }

            when (category) {
                VoiceListCategory.XG_NORMAL -> {
                    (XGNormalVoice::ordinal findBy index)?.let { xgVoice ->
                        updatedPart.changeXGVoice(xgVoice)
                        updatedPartsList[selectedChannel] = updatedPart
                        midiViewModel.channels.value = updatedPartsList
                        midiSession.send(
                            MidiMessageUtility.getXGNormalVoiceChange(
                                updatedPart.ch,
                                xgVoice
                            )
                        )
                    }
                }
                VoiceListCategory.XG_DRUM -> {
                    (XGDrumKit::ordinal findBy index)?.let { drumKit ->
                        updatedPart.setDrumKit(drumKit)
                        updatedPartsList[selectedChannel] = updatedPart
                        midiViewModel.channels.value = updatedPartsList
                        midiSession.send(
                            MidiMessageUtility.getDrumKitChange(
                                updatedPart.ch,
                                drumKit
                            )
                        )
                    }
                }
                VoiceListCategory.SFX -> {
                    (SFXNormalVoice::ordinal findBy index)?.let { sfx ->
                        updatedPart.changeSFXVoice(sfx)
                        updatedPartsList[selectedChannel] = updatedPart
                        midiViewModel.channels.value = updatedPartsList
                        midiSession.send(
                            MidiMessageUtility.getSFXNormalVoiceChange(
                                updatedPart.ch,
                                sfx
                            )
                        )
                    }
                }
                VoiceListCategory.QS300 -> {
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
                    val preset = qs300ViewModel.presets[index].clone()
                    midiViewModel.qsPartMap[selectedChannel]?.let { prevPreset ->
                        if (preset.voices.size == 1 && prevPreset.voices.size > 1) {
                            midiViewModel.channels.value!![selectedChannel + 1].changeXGVoice(
                                XGNormalVoice.GRAND_PNO
                            )
                            midiSession.send(
                                MidiMessageUtility.getXGNormalVoiceChange(
                                    selectedChannel + 1,
                                    XGNormalVoice.GRAND_PNO
                                )
                            )
                        }
                    }

                    /* When selecting a preset, and setting the value in the viewmodel, I need to
                    make a copy of the preset instead of setting the value directly, otherwise if I
                    have two midi parts that use the same preset, any changes made to one will
                    also be applied to the other.

                    Essentially, just need to make sure the map and the qs300Viewmodel "preset"
                    are referencing the same object, and the master presets list should be copied
                    from.

                    I may have to make a custom clone method or something.
                     */

                    midiViewModel.qsPartMap[selectedChannel] = preset

                    qs300ViewModel.preset.value = preset
                    qs300ViewModel.voice.value = 0
                    if (preset.voices.size > 1) {
                        if (selectedChannel + 1 == updatedPartsList.size) {
                            updatedPartsList[selectedChannel - 1].changeQS300Voice(
                                preset.voices[0],
                                0
                            )
                            updatedPart.changeQS300Voice(preset.voices[1], 1)
                            qs300ViewModel.voice.value = 1
                        } else {
                            updatedPart.changeQS300Voice(preset.voices[0], 0)
                            updatedPartsList[selectedChannel + 1].changeQS300Voice(
                                preset.voices[1],
                                1
                            )
                        }
                    } else {
                        updatedPart.changeQS300Voice(preset.voices[0], 0)
                    }
                    midiViewModel.channels.value = updatedPartsList
                    preset.voices.forEachIndexed { voiceIndex, voice ->
                        /**
                         * TODO: Along with sending a bulk dump, there will likely have to be some
                         *  channel initialization messages. Like if there is more than one voice, make
                         *  sure both midi part are receiving the same channel, set to the same mode,
                         *  etc.
                         */
                        midiSession.sendBulkMessage(
                            MidiMessageUtility.getQS300BulkDump(
                                voice,
                                voiceIndex
                            )
                        )
                        midiSession.sendBulkMessage(
                            MidiMessageUtility.getQS300VoiceSelection(
                                selectedChannel + voiceIndex,
                                voiceIndex,
                            )
                        )
                    }
                }
            }
        }
    }

    class VoiceEditImpl(
        qs300ViewModel: QS300ViewModel,
        midiViewModel: MidiViewModel,
        midiSession: MidiSession
    ) :
        OnVoiceItemSelectedListenerImpl(qs300ViewModel, midiViewModel, midiSession) {
        override fun onVoiceItemSelected(index: Int, category: VoiceListCategory) {
            val currentVoiceCount = qs300ViewModel.preset.value!!.voices.size
            var currentVoice = qs300ViewModel.voice.value!!
            val updatePreset = qs300ViewModel.presets[index]
            val selectedChannel = midiViewModel.selectedChannel.value!!
            val updateVoiceCount = updatePreset.voices.size
            if (currentVoiceCount == 2 && updateVoiceCount == 1) {
                // One of qs voices needs to be unselected - aka midi part for the second qs300 channel
                // needs to be set to default
                // if current voice is 1, set selected voice to selectedVOice - 1
                //
                if (currentVoice == 1) {
                    val updateSelectedChannel = selectedChannel - 1
                    midiViewModel.channels.value!![selectedChannel].setXGNormalVoice(
                        XGNormalVoice.GRAND_PNO
                    )
                    midiViewModel.selectedChannel.value = updateSelectedChannel
                    midiSession.send(
                        MidiMessageUtility.getXGNormalVoiceChange(
                            updateSelectedChannel + 1,
                            XGNormalVoice.GRAND_PNO
                        )
                    )
                    currentVoice = 0
                } else {
                    midiViewModel.channels.value!![selectedChannel + 1].setXGNormalVoice(
                        XGNormalVoice.GRAND_PNO
                    )
                    midiSession.send(
                        MidiMessageUtility.getXGNormalVoiceChange(
                            selectedChannel + 1,
                            XGNormalVoice.GRAND_PNO
                        )
                    )
                }
            }
            val firstChannel =
                if (currentVoice == 1)
                    selectedChannel - 1
                else
                    selectedChannel
            qs300ViewModel.preset.value = qs300ViewModel.presets[index]
            qs300ViewModel.preset.value!!.voices.forEachIndexed { voiceIndex, voice ->
                midiViewModel.channels.value!![firstChannel + voiceIndex].changeQS300Voice(
                    voice,
                    index
                )
                midiSession.sendBulkMessage(MidiMessageUtility.getQS300BulkDump(voice, index))
                midiSession.sendBulkMessage(
                    MidiMessageUtility.getQS300VoiceSelection(
                        firstChannel + voiceIndex,
                        voiceIndex,
                    )
                )
            }
        }
    }

}
