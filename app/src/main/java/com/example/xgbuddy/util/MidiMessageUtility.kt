package com.example.xgbuddy.util

import android.util.Log
import com.example.xgbuddy.data.MidiConstants
import com.example.xgbuddy.data.MidiControlChange
import com.example.xgbuddy.data.MidiMessage
import com.example.xgbuddy.data.SetupModel
import com.example.xgbuddy.data.gm.MidiParameter
import com.example.xgbuddy.data.gm.MidiPart
import com.example.xgbuddy.data.qs300.QS300ElementParameter
import com.example.xgbuddy.data.qs300.QS300Voice
import com.example.xgbuddy.data.qs300.QS300VoiceParameter
import com.example.xgbuddy.data.xg.*
import com.example.xgbuddy.util.EnumFinder.findBy

object MidiMessageUtility {

    const val TAG = "MidiMessageUtility"

    fun getDrumHit(channel: Int, drumNote: Byte): List<MidiMessage> {
        Log.d(TAG, "GetDrumHit: Ch $channel, drumNote $drumNote")
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
        Log.d(TAG, "getProgramChange: Ch $channel, program $programNumber")
        val statusByte = (MidiConstants.STATUS_PROGRAM_CHANGE or channel).toByte()
        return MidiMessage(byteArrayOf(statusByte, programNumber), 0)
    }

    fun getControlChange(channel: Int, controlNumber: Byte, value: Byte): MidiMessage {
        Log.d(TAG, "getControlChange: Ch $channel, controlNumber $controlNumber")
        val statusByte = (MidiConstants.STATUS_CONTROL_CHANGE or channel).toByte()
        return MidiMessage(byteArrayOf(statusByte, controlNumber, value), 0)
    }

    fun getXGNormalVoiceChange(channel: Int, xgNormalVoice: XGNormalVoice): List<MidiMessage> {
        Log.d(TAG, "getXGNormalVoiceChange: Ch $channel, xgNormalVoice $xgNormalVoice")
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
        Log.d(TAG, "getSFXNormalVoiceChange: Ch $channel, sfxNormalVoice $sfxNormalVoice")
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
        Log.d(TAG, "getDrumKitChange: Ch $channel, drumKit $drumKit")
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

    /**
     * I'll have to look at how the param change messages are structured. I'll probably just end up
     * having to create another param change method for effect parameters, since there's a chance
     * they could have multiple bytes being sent. I can't remember if that's based on the address
     * and expected value or if a byte length is supposed to be specified.
     */

    fun getXGParamChange(channel: Int, parameter: MidiParameter, value: Byte): MidiMessage {
        Log.d(TAG, "getXGParamChange: Ch $channel, parameter $parameter, value $value")
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

    fun getDrumVoiceBulkDump(
        drumSetup: Int,
        drumVoice: DrumVoice,
        drumNote: Byte,
        timestamp: Long = 0
    ): MidiMessage {
        val data = ByteArray(MidiConstants.XG_DRUM_BULK_TOTAL_SIZE)
        data[0] = MidiConstants.EXCLUSIVE_STATUS_BYTE
        data[1] = MidiConstants.YAMAHA_ID
        data[2] = MidiConstants.DEVICE_NUMBER_BULK_DUMP
        data[3] = MidiConstants.MODEL_ID_XG
        data[4] = 0
        data[5] = MidiConstants.XG_DRUM_BULK_DATA_SIZE
        data[6] = (0x30 or drumSetup).toByte()
        data[7] = drumNote
        data[8] = 0
        var index = 9
        DrumVoiceParameter.values().forEach {
            data[index++] = drumVoice.getPropertyValue(it.reflectedField)
        }
        data[index++] = getChecksum(data, 4)
        data[index] = MidiConstants.SYSEX_END
        return MidiMessage(data, timestamp)
    }

    fun getDrumParamChange(
        param: DrumVoiceParameter,
        drumSetup: Int,
        drumNote: Int,
        value: Byte
    ): MidiMessage {
        Log.d(
            TAG,
            "getDrumParamChange: param $param, drumSetup $drumSetup, drumNote $drumNote, value $value"
        )
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

    fun getDrumSetupBulkDump(drumSetup: Int, drumNote: Int, timestamp: Long = 0): MidiMessage {
        return MidiMessage(null, timestamp)
    }

    fun getNRPNSet(channel: Int, nrpn: NRPN, drumNoteNumber: Byte? = null): List<MidiMessage> {
        Log.d(TAG, "getNRPNSet, channel $channel, nrpn $nrpn, drumNoteNumber: $drumNoteNumber")
        return listOf(
            getControlChange(channel, MidiControlChange.NRPN_MSB.controlNumber, nrpn.msb),
            getControlChange(
                channel,
                MidiControlChange.NRPN_LSB.controlNumber,
                drumNoteNumber ?: nrpn.lsb!!
            )
        )
    }

    fun getNRPNClear(channel: Int): List<MidiMessage> {
        Log.d(TAG, "getNRPNClear, channel $channel")
        return listOf(
            getControlChange(channel, MidiControlChange.NRPN_MSB.controlNumber, 0x7f),
            getControlChange(channel, MidiControlChange.NRPN_LSB.controlNumber, 0x7f)
        )
    }

    fun getRPNSet(channel: Int, rpn: RPN): List<MidiMessage> {
        Log.d(TAG, "getRPNSet, channel $channel, rpn $rpn")
        return listOf(
            getControlChange(channel, MidiControlChange.RPN_MSB.controlNumber, rpn.msb),
            getControlChange(channel, MidiControlChange.RPN_LSB.controlNumber, rpn.lsb)
        )
    }

    fun getRPNClear(channel: Int): List<MidiMessage> {
        Log.d(TAG, "getRPNClear, channel $channel")
        return listOf(
            getControlChange(channel, MidiControlChange.RPN_MSB.controlNumber, 0x7f),
            getControlChange(channel, MidiControlChange.RPN_LSB.controlNumber, 0x7f)
        )
    }

    fun getEffectPresetChange(effect: Effect): MidiMessage {
        Log.d(TAG, "getEffectPresetChange")
        val presetChange = byteArrayOf(
            MidiConstants.EXCLUSIVE_STATUS_BYTE,
            MidiConstants.YAMAHA_ID,
            MidiConstants.DEVICE_NUMBER,
            MidiConstants.MODEL_ID_XG,
            MidiConstants.XG_EFFECT_PARAM_ADDR_HI,
            MidiConstants.XG_EFFECT_PARAM_ADDR_MID,
            effect.baseAddr,
            effect.msb,
            effect.lsb,
            MidiConstants.SYSEX_END
        )
        return MidiMessage(presetChange)
    }

    fun getEffectParamChange(effectParameterData: EffectParameterData, value: Int): MidiMessage {
        Log.d(TAG, "getEffectParamChange param: ${effectParameterData.name} value $value")
        val paramChange = byteArrayOf(
            MidiConstants.EXCLUSIVE_STATUS_BYTE,
            MidiConstants.YAMAHA_ID,
            MidiConstants.DEVICE_NUMBER,
            MidiConstants.MODEL_ID_XG,
            MidiConstants.XG_EFFECT_PARAM_ADDR_HI,
            MidiConstants.XG_EFFECT_PARAM_ADDR_MID,
            effectParameterData.addrLo,
            (value and 0xff).toByte(),
            (value shr 8).toByte(),
            MidiConstants.SYSEX_END
        )
        return MidiMessage(paramChange)
    }

    fun getEffectsBulkDump(
        reverb: Reverb,
        chorus: Chorus,
        variation: Variation,
        timestamp: Long = 0L
    ): MidiMessage {
        val data = ByteArray(MidiConstants.XG_EFFECT_BULK_TOTAL_SIZE)
        data[0] = MidiConstants.EXCLUSIVE_STATUS_BYTE
        data[1] = MidiConstants.YAMAHA_ID
        data[2] = MidiConstants.DEVICE_NUMBER_BULK_DUMP
        data[3] = MidiConstants.MODEL_ID_XG
        data[4] = 0
        data[5] = MidiConstants.XG_EFFECT_BULK_DATA_SIZE
        data[6] = MidiConstants.XG_EFFECT_PARAM_ADDR_HI
        data[7] = MidiConstants.XG_EFFECT_PARAM_ADDR_MID
        data[8] = 0
        var index = 9
        EffectParameterData.values().forEach {
            when (it) {
                EffectParameterData.REVERB_TYPE -> {
                    data[index++] = reverb.msb
                    data[index++] = reverb.lsb
                }
                EffectParameterData.CHORUS_TYPE -> {
                    data[index++] = chorus.msb
                    data[index++] = chorus.lsb
                }
                EffectParameterData.VARIATION_TYPE -> {
                    data[index++] = variation.msb
                    data[index++] = variation.lsb
                }
                else -> {
                    if (it.size == 1.toByte()) {
                        if (it.name.startsWith("REVERB")) {
                            data[index++] = getEffectParamValue(it, reverb).toByte()
                        } else if (it.name.startsWith("CHORUS") || it.name.startsWith("SEND_CHOR")) {
                            data[index++] = getEffectParamValue(it, chorus).toByte()
                        } else {
                            data[index++] = getEffectParamValue(it, variation).toByte()
                        }
                    } else { // Has to be variation parameter
                        data[index++] = getEffectParamValue(it, variation).toByte() // lo
                        data[index++] = getEffectParamValue(it, variation, 1).toByte() // hi
                    }
                }
            }

            // Address jumps
            index += when (it) {
                EffectParameterData.REVERB_PAN -> 2
                EffectParameterData.REVERB_PARAM_16 -> 10
                EffectParameterData.SEND_CHOR_TO_REV -> 1
                EffectParameterData.CHORUS_PARAMETER_16 -> 10
                EffectParameterData.AC2_VARI_CTRL_DEPTH -> 15
                else -> 0
            }
        }
        data[index++] = getChecksum(data, 4)
        data[index] = MidiConstants.SYSEX_END
        return MidiMessage(data, timestamp)
    }

    private fun getEffectParamValue(
        effectParameterData: EffectParameterData,
        effect: Effect,
        byteIndex: Int = 0
    ): Int {
        return if (effectParameterData.reflectedField != null) {
            effect.getPropertyValue(effectParameterData.reflectedField).toInt()
        } else {
            effect.getPropertyValue(effectParameterData.reflectedBigField) shr 8 * byteIndex
        }
    }

    fun getXGSystemOn(): MidiMessage {
        Log.d(TAG, "getXGSystemOn")
        return MidiMessage(MidiConstants.XY_SYSTEM_ON_ARRAY, 0)
    }

    fun getGMModeOn(): MidiMessage {
        Log.d(TAG, "getGMModeOn")
        return MidiMessage(MidiConstants.GM_MODE_ON_ARRAY, 0)
    }

    fun getDrumSetupReset(setupNumber: Int): MidiMessage {
        val array = MidiConstants.DRUM_SETUP_RESET_ARRAY
        array[7] = setupNumber.toByte()
        return MidiMessage(array, 0)
    }

    fun getAllParameterReset(): MidiMessage {
        Log.d(TAG, "getAllParameterReset")
        return MidiMessage(MidiConstants.ALL_PARAM_RESET_ARRAY, 0)
    }

    fun getXGMultiPartBulkDump(part: MidiPart, partNumber: Int, timestamp: Long = 0): MidiMessage {
        val data = ByteArray(MidiConstants.XG_MULTIPART_BULK_TOTAL_SIZE)
        data[0] = MidiConstants.EXCLUSIVE_STATUS_BYTE
        data[1] = MidiConstants.YAMAHA_ID
        data[2] = MidiConstants.DEVICE_NUMBER_BULK_DUMP
        data[3] = MidiConstants.MODEL_ID_XG
        data[4] = 0
        data[5] = MidiConstants.XG_MULTIPART_BULK_DATA_SIZE
        data[6] = MidiConstants.XG_MP_PARAM_ADDR_HI
        data[7] = partNumber.toByte()
        data[8] = 0
        var index = 9
        MidiParameter.values().forEach {
            data[index++] = part.getPropertyValue(it.reflectedField)
            if (it == MidiParameter.BEND_LFO_AMOD_DEPTH) index += 7 // param addr jumps up here
        }
        data[index++] = getChecksum(data, 4)
        data[index] = MidiConstants.SYSEX_END
        return MidiMessage(data, timestamp)
    }


    fun getQS300VoiceSelection(channel: Int, userVoice: Int, timestamp: Long = 0): MidiMessage {
        Log.d(
            TAG,
            "getQS300VoiceSelection channel $channel, presetVoice $userVoice, userVoice: ${channel + userVoice}"
        )
        val data = ByteArray(14)
        data[0] = MidiConstants.EXCLUSIVE_STATUS_BYTE
        data[1] = MidiConstants.YAMAHA_ID
        data[2] = MidiConstants.DEVICE_NUMBER_BULK_DUMP
        data[3] = MidiConstants.MODEL_ID_XG
        data[4] = 0
        data[5] = 3
        data[6] = 8
        data[7] = channel.toByte()
        data[8] = 1
        data[9] = MidiConstants.QS300_USER_VOICE_MSB
        data[10] = MidiConstants.QS300_USER_VOICE_LSB
        data[11] = channel.toByte() // Program Number
        data[12] = getChecksum(data, 4)
        data[13] = MidiConstants.SYSEX_END

        return MidiMessage(data, timestamp)
    }

    fun getQS300BulkDump(
        voice: QS300Voice,
        voiceNumber: Int,
        partNumber: Int,
        timestamp: Long = 0
    ): MidiMessage {
        Log.d(
            TAG,
            "getQS300BulkDump voice $voice, presetVoice $voiceNumber, userVoice: ${voiceNumber + partNumber}"
        )
        val data = ByteArray(MidiConstants.QS300_BULK_DUMP_TOTAL_SIZE)
        data[0] = MidiConstants.EXCLUSIVE_STATUS_BYTE
        data[1] = MidiConstants.YAMAHA_ID
        data[2] = MidiConstants.DEVICE_NUMBER_BULK_DUMP
        data[3] = MidiConstants.MODEL_ID_QS300
        data[4] = 1 // Byte count hi (will always be zero unless packet size is greater than 7f)
        data[5] = 0x7d
        data[6] = 17 // Addr hi
        data[7] =
            (voiceNumber + partNumber).toByte() // Addr mid : todo: This value changes depending on normal voice selection
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

        return MidiMessage(data, timestamp)
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

    fun getSystemParamChange(addr: Byte, data: ByteArray?): MidiMessage {
        val msg = ByteArray(11 + (data?.size ?: 0))
        msg[0] = MidiConstants.EXCLUSIVE_STATUS_BYTE
        msg[1] = MidiConstants.YAMAHA_ID
        msg[2] = MidiConstants.DEVICE_NUMBER_BULK_DUMP
        msg[3] = MidiConstants.MODEL_ID_XG
        msg[4] = 0
        msg[5] = data?.size?.toByte() ?: 0
        msg[6] = 0
        msg[7] = 0
        msg[8] = addr

        var dataIndex = 9
        data?.forEach {
            msg[dataIndex++] = it
        }
        msg[msg.size - 2] = getChecksum(msg, 4)
        msg[msg.size - 1] = MidiConstants.SYSEX_END
        return MidiMessage(msg)
    }

    fun getMasterVolumeChange(volume: Int): MidiMessage =
        getSystemParamChange(MidiConstants.XG_SYS_ADDR_VOLUME, byteArrayOf(volume.toByte()))

    fun getTransposeChange(transpose: Int): MidiMessage =
        getSystemParamChange(MidiConstants.XG_SYS_ADDR_TRANSPOSE, byteArrayOf(transpose.toByte()))

    fun getTuningChange(tuning: Int): MidiMessage {
        val tuningBytes = byteArrayOf(
            0,
            ((tuning shr 16) and 0x0f).toByte(),
            ((tuning shr 8) and 0x0f).toByte(),
            (tuning and 0x0f).toByte()
        )
        return getSystemParamChange(0, tuningBytes)
    }

    fun getAllOff(): List<MidiMessage> {
        // Send All sounds off and all notes off for channels 1-16
        val messages = mutableListOf<MidiMessage>()
        for (i in 0 until 16) {
            messages.add(getControlChange(i, MidiControlChange.ALL_SOUND_OFF.controlNumber, 0))
            messages.add(getControlChange(i, MidiControlChange.ALL_NOTE_OFF.controlNumber, 0))
        }
        return messages
    }

    fun getResetControllers(): List<MidiMessage> {
        val messages = mutableListOf<MidiMessage>()
        for (i in 0 until 16) {
            messages.add(getControlChange(i, MidiControlChange.RESET_ALL_CTRL.controlNumber, 0))
        }
        return messages
    }

    fun getSetupSequence(setup: SetupModel): List<MidiMessage> = mutableListOf<MidiMessage>().let {
        var timestamp = System.nanoTime()
        // XG System On - initialize everythin
        it.add(getXGSystemOn())

        // Bulk dump for each Midi Part
        setup.parts.forEachIndexed { index, part ->
            timestamp += MidiConstants.SETUP_SEQUENCE_INTERVAL_NANO
            if (part.voiceType == MidiPart.VoiceType.QS300) {
                val qsVoiceIndex: Int
                val preset = if (setup.qsPresetMap.containsKey(index)) {
                    qsVoiceIndex = 0
                    setup.qsPresetMap[index]
                } else {
                    qsVoiceIndex = 1
                    setup.qsPresetMap[index - 1]
                }
                it.add(getXGMultiPartBulkDump(part, index, timestamp))
                timestamp += MidiConstants.SETUP_SEQUENCE_INTERVAL_NANO
                it.add(
                    getQS300BulkDump(
                        preset!!.voices[qsVoiceIndex],
                        qsVoiceIndex,
                        index,
                        timestamp
                    )
                )
            } else {
                it.add(getXGMultiPartBulkDump(part, index, timestamp))
                if (part.voiceType == MidiPart.VoiceType.DRUM) {
                    part.drumVoices?.forEachIndexed { drumVoiceIndex, drumVoice ->
                        timestamp += MidiConstants.SETUP_SEQUENCE_INTERVAL_NANO
                        it.add(
                            getDrumVoiceBulkDump(
                                0,
                                drumVoice,
                                (drumVoiceIndex + MidiConstants.XG_INITIAL_DRUM_NOTE).toByte(),
                                timestamp
                            )
                        )
                    }
                }
            }
        }

        // Effects
        timestamp += MidiConstants.SETUP_SEQUENCE_INTERVAL_NANO
        it.add(getEffectsBulkDump(setup.reverb, setup.chorus, setup.variation, timestamp))

        // Todo: System params

        it
    }
}