package com.yamahaw.xgbuddy.ui.voiceselect

import android.util.Log
import com.yamahaw.xgbuddy.midi.MidiSession
import com.yamahaw.xgbuddy.data.gm.MidiParameter
import com.yamahaw.xgbuddy.data.voiceselect.VoiceListCategory
import com.yamahaw.xgbuddy.data.xg.SFXNormalVoice
import com.yamahaw.xgbuddy.data.xg.drum.XGDrumKit
import com.yamahaw.xgbuddy.data.xg.XGNormalVoice
import com.yamahaw.xgbuddy.viewmodel.MidiViewModel
import com.yamahaw.xgbuddy.util.EnumFinder.findBy
import com.yamahaw.xgbuddy.util.MidiMessageUtility
import com.yamahaw.xgbuddy.viewmodel.QS300ViewModel

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
                midiViewModel.qsPartMap.remove(selectedChannel)
            }
            midiViewModel.qsPartMap[selectedChannel - 1]?.let { preset ->
                if (preset.voices.size > 1) {
                    midiViewModel.channels.value!![selectedChannel - 1].setXGNormalVoice(
                        XGNormalVoice.GRAND_PNO
                    )
                    midiViewModel.qsPartMap.remove(selectedChannel - 1)
                    midiSession.send(
                        MidiMessageUtility.getXGNormalVoiceChange(
                            selectedChannel - 1,
                            XGNormalVoice.GRAND_PNO
                        )
                    )
                }
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
                VoiceListCategory.QS300,
                VoiceListCategory.QS300_USER -> {
                    val preset = if (category == VoiceListCategory.QS300_USER) {
                        qs300ViewModel.userPresets.values.toList()[index].clone()
                    } else {
                        qs300ViewModel.presets[index].clone()
                    }
                    midiViewModel.qsPartMap[selectedChannel]?.let { prevPreset ->
                        if (preset.voices.size == 1 && prevPreset.voices.size > 1) {
                            midiViewModel.channels.value!![selectedChannel + 1].apply {
                                setXGNormalVoice(XGNormalVoice.GRAND_PNO)
                                receiveChannel = (selectedChannel + 1).toByte()
                            }
                            midiSession.send(
                                mutableListOf(
                                    MidiMessageUtility.getXGParamChange(
                                        selectedChannel + 1, MidiParameter.RCV_CHANNEL,
                                        (selectedChannel + 1).toByte()
                                    )
                                ).apply {
                                    addAll(
                                        MidiMessageUtility.getXGNormalVoiceChange(
                                            selectedChannel + 1,
                                            XGNormalVoice.GRAND_PNO
                                        )
                                    )
                                }
                            )
                        }
                    }
                    if (midiViewModel.qsPartMap.containsKey(selectedChannel - 1)) {
                        if (midiViewModel.qsPartMap[selectedChannel - 1]!!.voices.size > 1) {
                            midiViewModel.qsPartMap.remove(selectedChannel - 1)
                            updatedPartsList[selectedChannel - 1].apply {
                                setXGNormalVoice(XGNormalVoice.GRAND_PNO)
                                receiveChannel = (selectedChannel - 1).toByte()
                            }
                            midiSession.send(
                                mutableListOf(
                                    MidiMessageUtility.getXGParamChange(
                                        selectedChannel - 1, MidiParameter.RCV_CHANNEL,
                                        (selectedChannel - 1).toByte()
                                    )
                                ).apply {
                                    addAll(
                                        MidiMessageUtility.getXGNormalVoiceChange(
                                            selectedChannel - 1,
                                            XGNormalVoice.GRAND_PNO
                                        )
                                    )
                                }
                            )
                        }
                    }
                    midiViewModel.qsPartMap[selectedChannel] = preset
                    qs300ViewModel.preset.value = preset
                    qs300ViewModel.voice.value = 0
                    if (preset.voices.size > 1) {
                        if (selectedChannel + 1 == updatedPartsList.size) {
                            midiViewModel.qsPartMap.remove(selectedChannel - 1)
                            updatedPartsList[selectedChannel - 1].apply {
                                changeQS300Voice(preset.voices[0], 0)
                                receiveChannel = ch.toByte()
                            }
                            updatedPart.apply {
                                changeQS300Voice(preset.voices[1], 1)
                                ch.toByte()
                            }
                            qs300ViewModel.voice.value = 1
                        } else {
                            midiViewModel.qsPartMap.remove(selectedChannel + 1)?.let {
                                if (it.voices.size == 2) {
                                    updatedPartsList[selectedChannel + 2].apply {
                                        setXGNormalVoice(XGNormalVoice.GRAND_PNO)
                                        receiveChannel = ch.toByte()
                                    }
                                    midiSession.send(
                                        mutableListOf(
                                            MidiMessageUtility.getXGParamChange(
                                                selectedChannel + 2, MidiParameter.RCV_CHANNEL,
                                                (selectedChannel + 2).toByte()
                                            )
                                        ).apply {
                                            addAll(
                                                MidiMessageUtility.getXGNormalVoiceChange(
                                                    selectedChannel + 2,
                                                    XGNormalVoice.GRAND_PNO
                                                )
                                            )
                                        }
                                    )
                                }
                            }
                            updatedPart.apply {
                                changeQS300Voice(preset.voices[0], 0)
                                receiveChannel = ch.toByte()
                            }
                            updatedPartsList[selectedChannel + 1].apply {
                                changeQS300Voice(preset.voices[1], 1)
                                receiveChannel = ch.toByte()
                            }
                        }
                    } else {
                        updatedPart.apply {
                            changeQS300Voice(preset.voices[0], 0)
                            receiveChannel = ch.toByte()
                        }
                    }
                    midiViewModel.channels.value = updatedPartsList
                    preset.voices.forEachIndexed { voiceIndex, voice ->
                        /**
                         * TODO: Along with sending a bulk dump, there will likely have to be some
                         *  channel initialization messages. Like if there is more than one voice, make
                         *  sure both midi part are receiving the same channel, set to the same mode,
                         *  etc.
                         */
                        Log.d(
                            "VoiceItemSelected",
                            "Before voice selection SelectedChanel = $selectedChannel, voiceIndex "
                        )
                        midiSession.sendBulkMessage(
                            MidiMessageUtility.getQS300VoiceSelection(
                                selectedChannel + voiceIndex,
                                voiceIndex,
                            )
                        )
                        midiSession.sendBulkMessage(
                            MidiMessageUtility.getQS300BulkDump(
                                voice,
                                voiceIndex,
                                selectedChannel + voiceIndex
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
            val updatePreset = if (category == VoiceListCategory.QS300_USER) {
                qs300ViewModel.userPresets.values.toList()[index].clone()
            } else {
                qs300ViewModel.presets[index].clone()
            }
            var selectedChannel = midiViewModel.selectedChannel.value!!
            val updateVoiceCount = updatePreset.voices.size
            if (currentVoiceCount == 2 && updateVoiceCount == 1) {
                // One of qs voices needs to be unselected - aka midi part for the second qs300 channel
                // needs to be set to default
                // if current voice is 1, set selected voice to selectedVOice - 1
                //
                if (currentVoice == 1) {
                    val updateSelectedChannel = selectedChannel - 1

                    /*
                    Current selected channel will be orphaned, so change voice grand piano, reset
                    receive channel to default value.
                     */
                    midiViewModel.channels.value!![selectedChannel].apply {
                        setXGNormalVoice(XGNormalVoice.GRAND_PNO)
                        receiveChannel = selectedChannel.toByte()
                    }

                    // Set the receive channel of the newly selected channel to its default value.
                    midiViewModel.channels.value!![updateSelectedChannel].receiveChannel =
                        updateSelectedChannel.toByte()

                    // Update viewModel selected channel
                    midiViewModel.selectedChannel.value = updateSelectedChannel

                    // Send messages for each of the above actions
                    midiSession.send(
                        mutableListOf(
                            MidiMessageUtility.getXGParamChange(
                                selectedChannel, MidiParameter.RCV_CHANNEL,
                                selectedChannel.toByte()
                            ),
                            MidiMessageUtility.getXGParamChange(
                                updateSelectedChannel, MidiParameter.RCV_CHANNEL,
                                updateSelectedChannel.toByte()
                            )
                        ).apply {
                            addAll(
                                MidiMessageUtility.getXGNormalVoiceChange(
                                    selectedChannel,
                                    XGNormalVoice.GRAND_PNO
                                )
                            )
                        }
                    )

                    currentVoice = 0
                    selectedChannel = updateSelectedChannel
                } else {
                    // Second voice will be orphaned so apply the same changes as above, reversed
                    midiViewModel.channels.value!![selectedChannel + 1].apply {
                        setXGNormalVoice(XGNormalVoice.GRAND_PNO)
                        receiveChannel = (selectedChannel + 1).toByte()
                    }
                    midiViewModel.channels.value!![selectedChannel].receiveChannel =
                        selectedChannel.toByte()

                    midiSession.send(mutableListOf(
                        MidiMessageUtility.getXGParamChange(
                            selectedChannel + 1, MidiParameter.RCV_CHANNEL,
                            (selectedChannel + 1).toByte()
                        ),
                        MidiMessageUtility.getXGParamChange(
                            selectedChannel, MidiParameter.RCV_CHANNEL, selectedChannel.toByte()
                        )
                    ).apply {
                        addAll(
                            MidiMessageUtility.getXGNormalVoiceChange(
                                selectedChannel + 1,
                                XGNormalVoice.GRAND_PNO
                            )
                        )
                    }
                    )
                }
            } else if (currentVoiceCount == 1 && updateVoiceCount == 2) {
                /*
                If there is a preset in the slot next to the current selected channel, remove it.
                I think the below voice selection messages should take care of overwriting the
                previous voice.
                 */
                midiViewModel.qsPartMap.remove(selectedChannel + 1)
            }
            val firstChannel =
                if (currentVoice == 1)
                    selectedChannel - 1
                else
                    selectedChannel
            midiViewModel.qsPartMap[firstChannel] = updatePreset
            qs300ViewModel.preset.value = updatePreset
            qs300ViewModel.preset.value!!.voices.forEachIndexed { voiceIndex, voice ->
                midiViewModel.channels.value!![firstChannel + voiceIndex].changeQS300Voice(
                    voice,
                    voiceIndex
                )

                /*
                I think not adding voiceIndex was wrong. I need to confirm this still changes
                the voice whether receive_channel has been changed or not. What a complicated mess.
                 */
                midiSession.sendBulkMessage(
                    MidiMessageUtility.getQS300VoiceSelection(
                        firstChannel + voiceIndex, // I think not adding voiceIndex was wrong.
                        voiceIndex,
                    )
                )
                midiSession.sendBulkMessage(
                    MidiMessageUtility.getQS300BulkDump(
                        voice,
                        voiceIndex,
                        firstChannel + voiceIndex
                    )
                )

            }
        }
    }
}
