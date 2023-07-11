package com.yamahaw.xgbuddy.ui.voiceselect

import android.util.Log
import com.yamahaw.xgbuddy.MidiSession
import com.yamahaw.xgbuddy.data.voiceselect.VoiceListCategory
import com.yamahaw.xgbuddy.data.xg.SFXNormalVoice
import com.yamahaw.xgbuddy.data.xg.XGDrumKit
import com.yamahaw.xgbuddy.data.xg.XGNormalVoice
import com.yamahaw.xgbuddy.ui.MidiViewModel
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
                VoiceListCategory.QS300 -> {
                    val preset = qs300ViewModel.presets[index].clone()
                    midiViewModel.qsPartMap[selectedChannel]?.let { prevPreset ->
                        if (preset.voices.size == 1 && prevPreset.voices.size > 1) {
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
                    if (midiViewModel.qsPartMap.containsKey(selectedChannel - 1)) {
                        if (midiViewModel.qsPartMap[selectedChannel - 1]!!.voices.size > 1) {
                            midiViewModel.qsPartMap.remove(selectedChannel - 1)
                            updatedPartsList[selectedChannel - 1].setXGNormalVoice(XGNormalVoice.GRAND_PNO)
                            midiSession.send(
                                MidiMessageUtility.getXGNormalVoiceChange(
                                    selectedChannel - 1,
                                    XGNormalVoice.GRAND_PNO
                                )
                            )
                        }
                    }
                    midiViewModel.qsPartMap[selectedChannel] = preset
                    qs300ViewModel.preset.value = preset
                    qs300ViewModel.voice.value = 0
                    if (preset.voices.size > 1) {
                        if (selectedChannel + 1 == updatedPartsList.size) {
                            midiViewModel.qsPartMap.remove(selectedChannel - 1)
                            updatedPartsList[selectedChannel - 1].changeQS300Voice(
                                preset.voices[0],
                                0
                            )
                            updatedPart.changeQS300Voice(preset.voices[1], 1)
                            qs300ViewModel.voice.value = 1
                        } else {
                            midiViewModel.qsPartMap.remove(selectedChannel + 1)?.let {
                                if (it.voices.size == 2) {
                                    updatedPartsList[selectedChannel + 2].setXGNormalVoice(
                                        XGNormalVoice.GRAND_PNO
                                    )
                                    midiSession.send(
                                        MidiMessageUtility.getXGNormalVoiceChange(
                                            selectedChannel + 2, XGNormalVoice.GRAND_PNO
                                        )
                                    )
                                }
                            }
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
                        Log.d("VoiceItemSelected", "Before voice selection SelectedChanel = $selectedChannel, voiceIndex ")
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
                                selectedChannel
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
            val updatePreset = qs300ViewModel.presets[index].clone()
            var selectedChannel = midiViewModel.selectedChannel.value!!
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
                    selectedChannel = updateSelectedChannel
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
            } else if (currentVoiceCount == 1 && updateVoiceCount == 2) {
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
                midiSession.sendBulkMessage(
                    MidiMessageUtility.getQS300VoiceSelection(
                        firstChannel,
                        voiceIndex,
                    )
                )
                midiSession.sendBulkMessage(
                    MidiMessageUtility.getQS300BulkDump(
                        voice,
                        index,
                        firstChannel
                    )
                )

            }
        }
    }
}