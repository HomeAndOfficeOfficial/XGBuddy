package com.yamahaw.xgbuddy

import com.yamahaw.xgbuddy.data.MidiMessage
import com.yamahaw.xgbuddy.data.gm.MidiPart
import com.yamahaw.xgbuddy.ui.MidiViewModel
import kotlin.experimental.and
import kotlin.experimental.or

class QSPartNoteDuplicator(
    private val viewModel: MidiViewModel,
    private val midiSession: MidiSession
) : MidiReceiverListener() {
    override fun onNoteReceived(channel: Int, message: ByteArray) {
        super.onNoteReceived(channel, message)
        if (viewModel.channels.value!![channel].voiceType == MidiPart.VoiceType.QS300) {
            if (viewModel.qsPartMap.containsKey(channel)) {
                if (viewModel.qsPartMap[channel]!!.voices.size == 2) {
                    if (isChannelReceivingDefault(channel + 1)) {
                        midiSession.send(MidiMessage(duplicateMessage(message, channel + 1)))
                    }
                }
            } else {
                viewModel.qsPartMap[channel - 1]?.let {
                    if (isChannelReceivingDefault(channel - 1)) {
                        midiSession.send(MidiMessage(duplicateMessage(message, channel - 1)))
                    }
                }
            }
        }
    }

    private fun isChannelReceivingDefault(channel: Int): Boolean =
        viewModel.channels.value!![channel].receiveChannel == channel.toByte()

    private fun duplicateMessage(message: ByteArray, channel: Int): ByteArray {
        val duplicateMessage = message.copyOf()
        duplicateMessage[0] = (duplicateMessage[0] and 0xf0.toByte()) or channel.toByte()
        return duplicateMessage
    }
}