package com.example.xgbuddy.util

import android.util.Log
import com.example.xgbuddy.data.MidiConstants
import com.example.xgbuddy.data.MidiControlChange
import com.example.xgbuddy.data.MidiMessage
import com.example.xgbuddy.data.gm.MidiParameter
import com.example.xgbuddy.data.qs300.QS300ElementParameter
import com.example.xgbuddy.data.qs300.QS300Voice
import com.example.xgbuddy.data.qs300.QS300VoiceParameter
import com.example.xgbuddy.data.xg.*
import com.example.xgbuddy.util.EnumFinder.findBy
import kotlin.math.min

object MidiMessageUtility {
    fun getDrumHit(channel: Int, drumNote: Byte): List<MidiMessage> {
        val noteOnData =
            byteArrayOf((MidiConstants.STATUS_NOTE_ON + channel).toByte(), drumNote, 100)
        val noteOffData =
            byteArrayOf((MidiConstants.STATUS_NOTE_OFF + channel).toByte(), drumNote, 0)
        return listOf(
            MidiMessage(noteOnData),
            MidiMessage(
                noteOffData,
                System.nanoTime() + 1000000000
            )
        )
    }

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

    fun getSFXNormalVoiceChange(channel: Int, sfxNormalVoice: SFXNormalVoice): List<MidiMessage> {
        val programChange = getProgramChange(channel, sfxNormalVoice.program)
        val ccMsb = getControlChange(
            channel,
            MidiControlChange.BANK_SELECT_MSB.controlNumber,
            MidiConstants.XG_SFX_VOICE_MSB
        )
        val ccLsb = getControlChange(
            channel,
            MidiControlChange.BANK_SELECT_LSB.controlNumber,
            MidiConstants.XG_SFX_VOICE_LSB
        )
        return listOf(ccMsb, ccLsb, programChange)
    }

    fun getDrumKitChange(channel: Int, drumKit: XGDrumKit): List<MidiMessage> {

        // TODO: Verify differences between changing drumkits in GM mode and XG mode
        val programChange = getProgramChange(channel, drumKit.programNumber)
        val ccMsb = getControlChange(
            channel,
            MidiControlChange.BANK_SELECT_MSB.controlNumber,
            MidiConstants.XG_DRUM_MSB
        )
        val ccLsb = getControlChange(
            channel,
            MidiControlChange.BANK_SELECT_LSB.controlNumber,
            MidiConstants.XG_DRUM_LSB
        )

        return listOf(ccMsb, ccLsb, programChange)
    }

    // TODO: Construct actual param change message
    fun getXGParamChange(channel: Int, parameter: MidiParameter, value: Byte): MidiMessage {
        val paramChange = byteArrayOf(
            MidiConstants.EXCLUSIVE_STATUS_BYTE,
            MidiConstants.YAMAHA_ID,
            MidiConstants.DEVICE_NUMBER,
            MidiConstants.MODEL_ID_XG,
            MidiConstants.XG_MP_PARAM_ADDR_HI,
            channel.toByte(),
            parameter.addrLo,
            value,
            MidiConstants.SYSEX_END
        )
        return MidiMessage(paramChange, 0)
    }

    fun getDrumParamChange(
        param: DrumVoiceParameter,
        drumSetup: Int,
        drumNote: Int,
        value: Byte
    ): MidiMessage {
        val paramChange = byteArrayOf(
            MidiConstants.EXCLUSIVE_STATUS_BYTE,
            MidiConstants.YAMAHA_ID,
            MidiConstants.DEVICE_NUMBER,
            MidiConstants.MODEL_ID_XG,
            (0x30 or drumSetup).toByte(),
            drumNote.toByte(),
            param.getAddrLo(),
            value,
            MidiConstants.SYSEX_END
        )
        return MidiMessage(paramChange, 0)
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

    fun getXGSystemOn(): MidiMessage = MidiMessage(MidiConstants.XY_SYSTEM_ON_ARRAY, 0)

    fun getGMModeOn(): MidiMessage = MidiMessage(MidiConstants.GM_MODE_ON_ARRAY, 0)

    fun getDrumSetupReset(setupNumber: Int): MidiMessage {
        val array = MidiConstants.DRUM_SETUP_RESET_ARRAY
        array[7] = setupNumber.toByte()
        return MidiMessage(array, 0)
    }

    fun getAllParameterReset(): MidiMessage = MidiMessage(MidiConstants.ALL_PARAM_RESET_ARRAY, 0)

    fun getQS300BulkDump(voice: QS300Voice): List<MidiMessage> {
        val messages = mutableListOf<MidiMessage>()
        var bytesSent = 0
        val packetSize = 64
        while (bytesSent < MidiConstants.QS300_BULK_DUMP_TOTAL_SIZE - 11) {
            val dataLength = min(
                packetSize,
                MidiConstants.QS300_BULK_DUMP_TOTAL_SIZE
                        - 11
                        - bytesSent
            )
            var packetDataBytes = 0
            val data = ByteArray(11 + dataLength) // header bytes + checksum + end byte = 11
            data[0] = MidiConstants.EXCLUSIVE_STATUS_BYTE
            data[1] = MidiConstants.YAMAHA_ID
            data[2] = MidiConstants.DEVICE_NUMBER_BULK_DUMP
            data[3] = MidiConstants.MODEL_ID_QS300
            data[4] = 0 // Byte count hi (will always be zero unless packet size is greater than 7f)
            data[5] = dataLength.toByte()
            data[6] = 17 // Addr hi
            data[7] = 0 // Addr mid : todo: This value changes depending on normal voice selection

            // Todo: Verify how larger addresses are handled. Does it carry over to the addr mid byte?
            data[8] = bytesSent.toByte()

            // Check if in range of Voice name
            if (bytesSent < MidiConstants.QS300_VOICE_NAME_SIZE) {
                var nameIndex = bytesSent
                while (nameIndex < MidiConstants.QS300_VOICE_NAME_SIZE && packetDataBytes < dataLength) {
                    data[MidiConstants.OFFSET_QS300_BULK_DATA_START + packetDataBytes] =
                        if (nameIndex < voice.voiceName.length) {
                            voice.voiceName[nameIndex].code.toByte()
                        } else {
                            32
                        }
                    packetDataBytes++
                    nameIndex++
                    bytesSent++
                }
            }

            // Check if in range of Voice Common
            if (bytesSent in MidiConstants.QS300_VOICE_NAME_SIZE
                until
                MidiConstants.OFFSET_QS300_BULK_ELEMENT_DATA_START
                - MidiConstants.OFFSET_QS300_BULK_DATA_START
            ) {
                while (bytesSent < MidiConstants.OFFSET_QS300_BULK_ELEMENT_DATA_START
                    - MidiConstants.OFFSET_QS300_BULK_DATA_START
                    && packetDataBytes < dataLength
                ) {
                    val voiceParam = QS300VoiceParameter::baseAddress findBy bytesSent.toUByte()
                    val property = voiceParam?.reflectedField
                    data[MidiConstants.OFFSET_QS300_BULK_DATA_START + packetDataBytes] =
                        voice.getPropertyValue(property)
                    packetDataBytes++
                    bytesSent++
                }
            }

            // Element data
            var elementIndex = (bytesSent - 61) / MidiConstants.QS300_ELEMENT_DATA_SIZE
            while (elementIndex < MidiConstants.QS300_MAX_ELEMENTS && packetDataBytes < dataLength) {
                val addr = bytesSent - (elementIndex * MidiConstants.QS300_ELEMENT_DATA_SIZE)
                val elementParam = QS300ElementParameter::baseAddress findBy addr.toUByte()
                val property = elementParam?.reflectedField
                data[MidiConstants.OFFSET_QS300_BULK_DATA_START + packetDataBytes] =
                    voice.elements[elementIndex].getPropertyValue(property)
                packetDataBytes++
                bytesSent++
                elementIndex = (bytesSent - 61) / MidiConstants.QS300_ELEMENT_DATA_SIZE
            }

            // Checksum
            var dataSum: Byte = 0
            for (i in 4 until data.size - 2) {
                dataSum = dataSum.plus(data[i]).toByte()
            }
            data[data.size - 2] = (0 - dataSum).toByte()
            data[data.size - 1] = MidiConstants.SYSEX_END

            messages.add(MidiMessage(data))
        }

//        testMessages(messages, packetSize, bytesSent)

        return messages
    }

//    private fun testMessages(messages: List<MidiMessage>, packetSize: Int, bytesSent: Int) {
//        val tag = "MidiMessageUtility"
//        Log.d(tag, "Bytes sent = $bytesSent")
//        Log.d(tag, "Packet size = $packetSize")
//        var totalData = 0
//        messages.forEachIndexed { i, message ->
//            totalData += message.msg!![5]
//            Log.d(
//                tag,
//                "Message $i: Length = ${message.msg.size}; Data length = ${message.msg[5]}; Start addr = ${message.msg[8]}"
//            )
//        }
//        Log.d(tag, "Total data length: $totalData, Expected length: 381")
//    }
}