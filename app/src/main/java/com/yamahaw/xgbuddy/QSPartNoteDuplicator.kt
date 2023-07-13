package com.yamahaw.xgbuddy

import com.yamahaw.xgbuddy.data.MidiControlChange
import com.yamahaw.xgbuddy.data.MidiMessage
import com.yamahaw.xgbuddy.data.gm.MidiPart
import com.yamahaw.xgbuddy.ui.MidiViewModel
import kotlin.experimental.and
import kotlin.experimental.or

class QSPartNoteDuplicator(
    private val viewModel: MidiViewModel,
    private val midiSession: MidiSession
) : MidiReceiverListener() {

    private fun rerouteMessage(channel: Int, message: ByteArray) {
        when (getDuplicateDirection(channel)) {
            Direction.PLUS -> midiSession.send(
                MidiMessage(
                    duplicateMessage(message, channel + 1)
                )
            )
            Direction.MINUS -> midiSession.send(
                MidiMessage(
                    duplicateMessage(message, channel - 1)
                )
            )
            else -> {}
        }
    }

    private fun getDuplicateDirection(channel: Int): Direction {
        if (viewModel.channels.value!![channel].voiceType == MidiPart.VoiceType.QS300) {
            if (viewModel.qsPartMap.containsKey(channel)) {
                if (viewModel.qsPartMap[channel]!!.voices.size == 2) {
                    if (isChannelReceivingDefault(channel + 1)) {
                        return Direction.PLUS
                    }
                }
            } else {
                viewModel.qsPartMap[channel - 1]?.let {
                    if (isChannelReceivingDefault(channel - 1)) {
                        return Direction.MINUS
                    }
                }
            }
        }
        return Direction.NONE
    }

    private fun isChannelReceivingDefault(channel: Int): Boolean =
        viewModel.channels.value!![channel].receiveChannel == channel.toByte()

    private fun duplicateMessage(message: ByteArray, channel: Int): ByteArray {
        val duplicateMessage = message.copyOf()
        duplicateMessage[0] = (duplicateMessage[0] and 0xf0.toByte()) or channel.toByte()
        return duplicateMessage
    }

    override fun onNoteReceived(channel: Int, message: ByteArray) {
        super.onNoteReceived(channel, message)
        rerouteMessage(channel, message)
    }

    override fun onPitchBendReceived(channel: Int, message: ByteArray) {
        super.onPitchBendReceived(channel, message)
        rerouteMessage(channel, message)
    }

    override fun onControlChangeReceived(
        channel: Int,
        cc: MidiControlChange,
        value: Int,
        msg: ByteArray
    ) {
        super.onControlChangeReceived(channel, cc, value, msg)
        if (cc == MidiControlChange.MODULATION) {
            rerouteMessage(channel, msg)
        }
    }

    enum class Direction { PLUS, MINUS, NONE }
}