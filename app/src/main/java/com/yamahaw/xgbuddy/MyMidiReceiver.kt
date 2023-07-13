package com.yamahaw.xgbuddy

import android.media.midi.MidiInputPort
import android.media.midi.MidiReceiver
import android.util.Log
import com.yamahaw.xgbuddy.data.MidiConstants
import com.yamahaw.xgbuddy.data.MidiControlChange
import com.yamahaw.xgbuddy.util.EnumFinder.findBy
import kotlinx.coroutines.*
import kotlin.experimental.and

const val RECEIVER_MAX_LENGTH = MidiConstants.QS300_BULK_DUMP_TOTAL_SIZE
const val SYSEX_TIMEOUT_MS = 500L

class MyMidiReceiver() : MidiReceiver(RECEIVER_MAX_LENGTH) {

    private val midiSubscribers = mutableListOf<MidiReceiverListener>()
    private val sysexBuffer = mutableListOf<Byte>()
    private var isReceivingSysex = false
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Default + job)
    private val sysexTimeoutJob = startSysexTimer {
        resetSysexStatus()
    }

    var inputPort: MidiInputPort? = null

    var receivedPitchBend = false
    var receivedModWheel = false
    var continuousChannel = UNSET

    fun subscribe(listener: MidiReceiverListener) {
        midiSubscribers.add(listener)
    }

    fun unsubscribe(listener: MidiReceiverListener) {
        midiSubscribers.remove(listener)
    }

    fun unsubscribeAll() {
        midiSubscribers.clear()
    }

    override fun onSend(msg: ByteArray?, offset: Int, count: Int, timestamp: Long) {
        if (msg != null) {
            val midiMsg = msg.slice(offset until offset + count)
            Log.d(TAG, "received: ${midiMsg.joinToString { String.format("%02x ", it) }}")

            // Only applies to continuous data
//            if (continuousChannel != UNSET) {
//                if (midiMsg.size == MidiConstants.CONTINUOUS_DATA_LENGTH) {
//
//                } else {
//                    receivedPitchBend = isPitchBend(midiMsg[0])
//                    receivedModWheel = isModWheel(midiMsg[0], midiMsg[1])
//                    if (receivedModWheel || receivedPitchBend) {
//                        continuousChannel = (midiMsg[0] and 0x0f).toInt()
//                        if (receivedPitchBend) {
//                            parsePitchBend(midiMsg)
//                        } else {
//                            parseControlChange(midiMsg)
//                        }
//                    }
//                }
//            }

            if (isReceivingSysex) {
                sysexTimeoutJob.cancel()
                sysexTimeoutJob.start()
                sysexBuffer.addAll(midiMsg)
                if (sysexBuffer.last() == MidiConstants.SYSEX_END) {
                    sysexTimeoutJob.cancel()
                    parseSysexAndInvokeCallback()
                    resetSysexStatus()
                    inputPort?.send(sysexBuffer.toByteArray(), 0, sysexBuffer.size)
                }
            } else {
                inputPort?.send(midiMsg.toByteArray(), 0, midiMsg.size)
                // TODO: This is getting a little messy, should refactor
                if (isNote(midiMsg[0])) {
                    parseNote(midiMsg)
                } else if (isPitchBend(midiMsg[0])) {
                    parsePitchBend(midiMsg)
                } else if (isControlChange(midiMsg[0])) {
                    parseControlChange(midiMsg)
                }
            }
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

    private fun isModWheel(statusByte: Byte, controlByte: Byte): Boolean {
        return isControlChange(statusByte) && controlByte == MidiControlChange.MODULATION.controlNumber
    }

    private fun isControlChange(statusByte: Byte): Boolean {
        val status = (statusByte.toUByte() and 0xf0u)
        return status == MidiConstants.STATUS_CONTROL_CHANGE
    }

    private fun startSysexTimer(onTimeout: () -> Unit) = scope.launch {
        delay(SYSEX_TIMEOUT_MS)
        onTimeout()
    }

    private fun resetSysexStatus() {
        isReceivingSysex = false
        sysexBuffer.clear()
    }

    private fun parseSysexAndInvokeCallback() {
        /**
         * I guess first identify whether this is a sysex message we care about.
         * Then probably check the address
         * Depending on address, call appropriate callback method
         */
    }

    /**
     * This will require some actual testing. I think some control changes may come as a sequence
     * of values, but not sure how this works practically.
     */
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
        const val UNSET = -1
    }
}