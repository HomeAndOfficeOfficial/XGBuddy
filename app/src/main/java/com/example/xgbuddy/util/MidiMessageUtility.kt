package com.example.xgbuddy.util

import com.example.xgbuddy.data.MidiConstants
import com.example.xgbuddy.data.MidiControlChange
import com.example.xgbuddy.data.MidiMessage
import com.example.xgbuddy.data.gm.MidiParameter
import com.example.xgbuddy.data.qs300.QS300ElementParameter
import com.example.xgbuddy.data.qs300.QS300Voice
import com.example.xgbuddy.data.qs300.QS300VoiceParameter
import com.example.xgbuddy.data.xg.*
import com.example.xgbuddy.util.EnumFinder.findBy

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

    fun getQS300VoiceSelection(channel: Int, userVoice: Int): MidiMessage {
        val data = ByteArray(14)
        data[0] = MidiConstants.EXCLUSIVE_STATUS_BYTE
        data[1] = MidiConstants.YAMAHA_ID
        data[2] = MidiConstants.DEVICE_NUMBER_BULK_DUMP
        data[3] = MidiConstants.MODEL_ID_XG
        data[4] = 0
        data[5] = 3
        data[6] = 8
        data[7] = 0
        data[8] = 1
        data[9] = 0x3f
        data[10] = 0
        data[11] = userVoice.toByte()
        data[12] = getChecksum(data, 4)
        data[13] = MidiConstants.SYSEX_END

        return MidiMessage(data)
    }

    fun getQS300BulkDump(voice: QS300Voice): MidiMessage {
        val data = ByteArray(MidiConstants.QS300_BULK_DUMP_TOTAL_SIZE)
        data[0] = MidiConstants.EXCLUSIVE_STATUS_BYTE
        data[1] = MidiConstants.YAMAHA_ID
        data[2] = MidiConstants.DEVICE_NUMBER_BULK_DUMP
        data[3] = MidiConstants.MODEL_ID_QS300
        data[4] = 1 // Byte count hi (will always be zero unless packet size is greater than 7f)
        data[5] = 0x7d
        data[6] = 17 // Addr hi
        data[7] = 0 // Addr mid : todo: This value changes depending on normal voice selection
        data[8] = 0

        // Voice Name
        for (nameIndex in 0 until MidiConstants.QS300_VOICE_NAME_SIZE) {
            data[MidiConstants.OFFSET_QS300_BULK_DATA_START + nameIndex] =
                if (nameIndex < voice.voiceName.length) {
                    voice.voiceName[nameIndex].code.toByte()
                } else {
                    32 // Space character
                }
        }

        // Voice Common
        for (voiceCommIndex in MidiConstants.OFFSET_QS300_BULK_VOICE_COMMON_START
                until MidiConstants.OFFSET_QS300_BULK_ELEMENT_DATA_START) {
            val addr = (voiceCommIndex - MidiConstants.OFFSET_QS300_BULK_DATA_START).toUByte()
            val voiceParam = QS300VoiceParameter::baseAddress findBy addr
            val property = voiceParam?.reflectedField
            data[voiceCommIndex] = voice.getPropertyValue(property)
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
                    data[j] = voice.elements[i].getPropertyValue(elementParam?.reflectedField)
                }
            }
        }

        // Checksum
        data[data.size - 2] = getChecksum(data, 4)
        data[data.size - 1] = MidiConstants.SYSEX_END

        return MidiMessage(data)
    }

    private fun getChecksum(data: ByteArray, startIndex: Int): Byte {
        var datasum = 0
        for (i in startIndex until data.size - 2) {
            datasum += data[i]
        }
        var checksum = 128 - (datasum % 128)
        if (checksum == 128) {
            checksum = 0
        }
        return checksum.toByte()
    }
}