package com.example.xgbuddy

import android.media.midi.MidiInputPort
import android.media.midi.MidiReceiver
import android.util.Log
import com.example.xgbuddy.data.MidiConstants
import com.example.xgbuddy.data.MidiControlChange
import com.example.xgbuddy.util.EnumFinder.findBy
import kotlinx.coroutines.*
import kotlin.experimental.and

const val RECEIVER_MAX_LENGTH = MidiConstants.QS300_BULK_DUMP_TOTAL_SIZE
const val SYSEX_TIMEOUT_MS = 500L

class MyMidiReceiver(
    private val receiverListener: MidiReceiverListener
) : MidiReceiver(RECEIVER_MAX_LENGTH) {

    private val sysexBuffer = mutableListOf<Byte>()
    private var isReceivingSysex = false
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Default + job)
    private val sysexTimeoutJob = startSysexTimer {
        resetSysexStatus()
    }

    var inputPort: MidiInputPort? = null

    override fun onSend(msg: ByteArray?, offset: Int, count: Int, timestamp: Long) {
        if (msg != null) {
            val midiMsg = msg.slice(offset until offset + count)
            Log.d(TAG, "received: ${midiMsg.joinToString { String.format("%02x ", it) }}")
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
                when (midiMsg[0].toInt() and 0xf0) {
                    MidiConstants.STATUS_CONTROL_CHANGE -> {
                        parseControlChange(midiMsg)
                    }
                    MidiConstants.STATUS_PROGRAM_CHANGE -> {
                        parseProgramChange(midiMsg)
                    }
                    else -> { /* Don't care */
                    }
                }
                inputPort?.send(midiMsg.toByteArray(), 0, midiMsg.size)
            }
        }
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
        receiverListener.onControlChangeReceived(channel, controlChange!!, value)
    }

    private fun parseProgramChange(msg: List<Byte>) {
        val channel = (msg[0] and 0x0f).toInt()
        val programNumber = (msg[1]).toInt()
        receiverListener.onProgramChangeReceived(channel, programNumber)
    }

    companion object {
        const val TAG = "MyMidiReceiver"
    }
}