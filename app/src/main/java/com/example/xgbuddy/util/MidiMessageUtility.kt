package com.example.xgbuddy.util

import com.example.xgbuddy.data.MidiConstants
import com.example.xgbuddy.data.MidiControlChange
import com.example.xgbuddy.data.MidiMessage
import com.example.xgbuddy.data.qs300.QS300ElementParameter
import com.example.xgbuddy.data.qs300.QS300Voice
import com.example.xgbuddy.data.qs300.QS300VoiceParameter
import com.example.xgbuddy.data.xg.NRPN
import com.example.xgbuddy.data.xg.RPN
import com.example.xgbuddy.data.xg.XGNormalVoice
import com.example.xgbuddy.util.EnumFinder.findBy

object MidiMessageUtility {
    /**
     * Let's think about a utility class to do this kind of thing.
     *
     * Probably called MidiMessageUtility or something.
     *
     * It would have methods that create midimessages like:
     *  - getQS300BulkDump(voice: QS300Voice)
     *    - This would be called from QS300ElementBaseFragment by first updating the viewModel (like
     *      it does already), then instead of calling the method below in this class, it would call
     *      midiSession.send(MidiMessageUtility.getQS300BulkDump(viewModelVoice))
     *  - getProgramChange(channel: Byte, programNumber: Byte)
     *  - getControlChange(channel: Byte, control: ControlChange, value: Byte)
     *
     *  Basically, any time someone needs a midi message for something, this utility should have a
     *  method for it.
     */

    fun getProgramChange(channel: Int, programNumber: Byte): MidiMessage {
        val statusByte = (MidiConstants.STATUS_PROGRAM_CHANGE or channel).toByte()
        return MidiMessage(byteArrayOf(statusByte, programNumber), 0)
    }

    fun getControlChange(channel: Int, controlNumber: Byte, value: Byte): MidiMessage {
        val statusByte = (MidiConstants.STATUS_CONTROL_CHANGE or channel).toByte()
        return MidiMessage(byteArrayOf(statusByte, controlNumber, value), 0)
    }

    fun getXGNormalVoiceChange(channel: Int, xgNormalVoice: XGNormalVoice): List<MidiMessage> {
        val programChange = getProgramChange(channel, xgNormalVoice.program)
        val ccMsb = getControlChange(
            channel,
            MidiControlChange.BANK_SELECT_MSB.controlNumber,
            MidiConstants.XG_NORMAL_VOICE_MSB
        )
        val ccLsb = getControlChange(
            channel,
            MidiControlChange.BANK_SELECT_LSB.controlNumber,
            xgNormalVoice.bank
        )
        return listOf(ccMsb, ccLsb, programChange)
    }

    // TODO: Construct actual param change message
    fun getXGParamChange(): MidiMessage {
        return MidiMessage(byteArrayOf(), 0)
    }

    fun getNRPNSet(channel: Int, nrpn: NRPN, drumNoteNumber: Byte? = null): List<MidiMessage> =
        listOf(
            getControlChange(channel, MidiControlChange.NRPN_MSB.controlNumber, nrpn.msb),
            getControlChange(
                channel,
                MidiControlChange.NRPN_LSB.controlNumber,
                drumNoteNumber ?: nrpn.lsb!!
            )
        )

    fun getNRPNClear(channel: Int): List<MidiMessage> = listOf(
        getControlChange(channel, MidiControlChange.NRPN_MSB.controlNumber, 0x7f),
        getControlChange(channel, MidiControlChange.NRPN_LSB.controlNumber, 0x7f)
    )

    fun getRPNSet(channel: Int, rpn: RPN): List<MidiMessage> = listOf(
        getControlChange(channel, MidiControlChange.RPN_MSB.controlNumber, rpn.msb),
        getControlChange(channel, MidiControlChange.RPN_LSB.controlNumber, rpn.lsb)
    )

    fun getRPNClear(channel: Int): List<MidiMessage> = listOf(
        getControlChange(channel, MidiControlChange.RPN_MSB.controlNumber, 0x7f),
        getControlChange(channel, MidiControlChange.RPN_LSB.controlNumber, 0x7f)
    )

    fun getQS300BulkDump(voice: QS300Voice): MidiMessage {
        var dataSum: Byte = 0
        val bulkDumpArray = ByteArray(MidiConstants.QS300_BULK_DUMP_TOTAL_SIZE) { 0 }.also {
            it[0] = MidiConstants.EXCLUSIVE_STATUS_BYTE
            it[1] = MidiConstants.YAMAHA_ID_BYTE
            it[2] = MidiConstants.DEVICE_NUMBER_BULK_DUMP
            it[3] = MidiConstants.MODEL_ID_QS300
            it[4] = 1   // Byte count hi
            it[5] = 125 // Byte count lo
            it[6] = 17  // Addr hi
            it[7] =
                0   // Addr mid TODO: This value is different depending on normal voice selection
            it[8] = 0   // Addr lo

            // Voice name
            for ((nameIndex, i) in (MidiConstants.OFFSET_QS300_BULK_DATA_START until MidiConstants.OFFSET_QS300_BULK_DATA_START + MidiConstants.QS300_VOICE_NAME_SIZE).withIndex()) {
                it[i] = if (nameIndex < voice.voiceName.length) {
                    voice.voiceName[nameIndex].code.toByte()
                } else {
                    32
                } // Space
            }

            // Voice common
            for (i in MidiConstants.OFFSET_QS300_BULK_VOICE_COMMON_START until MidiConstants.OFFSET_QS300_BULK_ELEMENT_DATA_START) {
                val addr = (i - MidiConstants.OFFSET_QS300_BULK_DATA_START).toUByte()
                val voiceParam = QS300VoiceParameter::baseAddress findBy addr
                val property = voiceParam?.reflectedField
                it[i] = voice.getPropertyValue(property)
            }

            // Element data
            for (i in 0 until MidiConstants.QS300_MAX_ELEMENTS) {
                if (i < voice.elements.size) {
                    val startIndex =
                        MidiConstants.OFFSET_QS300_BULK_ELEMENT_DATA_START + (i * MidiConstants.QS300_ELEMENT_DATA_SIZE)
                    for (j in startIndex until startIndex + MidiConstants.QS300_ELEMENT_DATA_SIZE) {
                        val baseAddress =
                            (j - MidiConstants.OFFSET_QS300_BULK_DATA_START - (i * MidiConstants.QS300_ELEMENT_DATA_SIZE)).toUByte()
                        val elementParam = QS300ElementParameter::baseAddress findBy baseAddress
                        val property = elementParam?.reflectedField
                        it[j] = voice.elements[i].getPropertyValue(property)
                    }
                }
            }

            // Checksum
            for (i in 4 until MidiConstants.QS300_BULK_DUMP_TOTAL_SIZE - 2) {
                dataSum = dataSum.plus(it[i]).toByte()
            }
            it[MidiConstants.QS300_BULK_DUMP_TOTAL_SIZE - 2] = (0 - dataSum).toByte()
            it[MidiConstants.QS300_BULK_DUMP_TOTAL_SIZE - 1] = MidiConstants.END_BYTE
        }

        return MidiMessage(bulkDumpArray, 0)
    }
}