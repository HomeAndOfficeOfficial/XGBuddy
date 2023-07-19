package com.yamahaw.xgbuddy.midi

import android.media.midi.MidiInputPort
import android.media.midi.MidiReceiver
import android.util.Log
import com.yamahaw.xgbuddy.data.MidiConstants
import com.yamahaw.xgbuddy.data.gm.MidiControlChange
import com.yamahaw.xgbuddy.util.EnumFinder.findBy
import kotlin.experimental.and

const val RECEIVER_MAX_LENGTH = MidiConstants.QS300_BULK_DUMP_TOTAL_SIZE

class MyMidiReceiver : MidiReceiver(RECEIVER_MAX_LENGTH) {

    private val midiSubscribers = mutableListOf<MidiReceiverListener>()

    // TODO: Expand to accept multiple InputPorts
    var inputPorts = mutableListOf<MidiInputPort>()

    fun subscribe(listener: MidiReceiverListener) {
        midiSubscribers.add(listener)
    }

    fun unsubscribe(listener: MidiReceiverListener) {
        midiSubscribers.remove(listener)
    }

    fun unsubscribeAll() {
        midiSubscribers.clear()
    }

    fun addInputPort(inputPort: MidiInputPort) {
        inputPorts.add(inputPort)
    }

    override fun onSend(msg: ByteArray?, offset: Int, count: Int, timestamp: Long) {
        if (msg != null) {
            val midiMsg = msg.slice(offset until offset + count)
            Log.d(TAG, "received: ${midiMsg.joinToString { String.format("%02x ", it) }}")
            inputPorts.forEach { it.send(midiMsg.toByteArray(), 0, midiMsg.size) }
            parseMidiForSubscribers(midiMsg)
        }
    }

    private fun parseMidiForSubscribers(midiMsg: List<Byte>) {
        if (isNote(midiMsg[0])) {
            parseNote(midiMsg)
        } else if (isPitchBend(midiMsg[0])) {
            parsePitchBend(midiMsg)
        } else if (isControlChange(midiMsg[0])) {
            parseControlChange(midiMsg)
        }
    }

    private fun isNote(statusByte: Byte): Boolean {
        val status = (statusByte.toUByte() and 0xf0u)
        return status == MidiConstants.STATUS_NOTE_ON || status == MidiConstants.STATUS_NOTE_OFF
    }

    private fun isPitchBend(statusByte: Byte): Boolean {
        val status = (statusByte.toUByte() and 0xf0u)
        return status == MidiConstants.STATUS_PITCH_BEND
    }

    private fun isControlChange(statusByte: Byte): Boolean {
        val status = (statusByte.toUByte() and 0xf0u)
        return status == MidiConstants.STATUS_CONTROL_CHANGE
    }

    private fun parseControlChange(msg: List<Byte>) {
        val channel = (msg[0] and 0x0f).toInt()
        val controlChange = MidiControlChange::controlNumber findBy msg[1]
        val value = msg[2].toInt()
        midiSubscribers.forEach {
            it.onControlChangeReceived(
                channel,
                controlChange!!,
                value,
                msg.toByteArray()
            )
        }
    }

    private fun parseNote(msg: List<Byte>) {
        val channel = (msg[0] and 0x0f).toInt()
        midiSubscribers.forEach { it.onNoteReceived(channel, msg.toByteArray()) }
    }

    private fun parsePitchBend(msg: List<Byte>) {
        val channel = (msg[0] and 0x0f).toInt()
        midiSubscribers.forEach { it.onPitchBendReceived(channel, msg.toByteArray()) }
    }

    private fun parseProgramChange(msg: List<Byte>) {
        val channel = (msg[0] and 0x0f).toInt()
        val programNumber = (msg[1]).toInt()
        midiSubscribers.forEach { it.onProgramChangeReceived(channel, programNumber) }
    }

    companion object {
        const val TAG = "MyMidiReceiver"
    }
}