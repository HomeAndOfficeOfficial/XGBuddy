package com.example.xgbuddy.data.qs300

import com.example.xgbuddy.data.MidiConstants
import com.example.xgbuddy.data.MidiData
import com.example.xgbuddy.data.MidiMessage
import com.example.xgbuddy.util.EnumFinder.findBy

data class QS300Voice(var voiceName: String = ""): MidiData() {

    val elements: MutableList<QS300Element> = mutableListOf()

    var voiceLevel: Byte = QS300VoiceParameter.VOICE_LVL.default
    var elementSwitch: Byte = QS300VoiceParameter.EL_SWITCH.default
    var voiceCategory: Byte = QS300VoiceParameter.VOICE_CATEGORY.default
    var velSensDepth: Byte = QS300VoiceParameter.VEL_SENS_DEPTH.default
    var velSensOffset: Byte = QS300VoiceParameter.VEL_SENS_OFFSET.default
    var reverbSendLvl: Byte = QS300VoiceParameter.REVERB_SEND_LVL.default
    var chorusSendLvl: Byte = QS300VoiceParameter.CHORUS_SEND_LVL.default
    var sendChorToReverb: Byte = QS300VoiceParameter.SEND_CHOR_TO_REV.default
    var varTypeMsb: Byte = QS300VoiceParameter.VAR_TYPE_MSB.default
    var varTypeLsb: Byte = QS300VoiceParameter.VAR_TYPE_LSB.default
    var varParamMsb1: Byte = QS300VoiceParameter.VAR_PARAM_MSB_1.default
    var varParamLsb1: Byte = QS300VoiceParameter.VAR_PARAM_LSB_1.default
    var varParamMsb2: Byte = QS300VoiceParameter.VAR_PARAM_MSB_2.default
    var varParamLsb2: Byte = QS300VoiceParameter.VAR_PARAM_LSB_2.default
    var varParamMsb3: Byte = QS300VoiceParameter.VAR_PARAM_MSB_3.default
    var varParamLsb3: Byte = QS300VoiceParameter.VAR_PARAM_LSB_3.default
    var varParamMsb4: Byte = QS300VoiceParameter.VAR_PARAM_MSB_4.default
    var varParamLsb4: Byte = QS300VoiceParameter.VAR_PARAM_LSB_4.default
    var varParamMsb5: Byte = QS300VoiceParameter.VAR_PARAM_MSB_5.default
    var varParamLsb5: Byte = QS300VoiceParameter.VAR_PARAM_LSB_5.default
    var varAttenLvl: Byte = QS300VoiceParameter.VAR_ATTEN_LVL.default
    var varParamLsb10: Byte = QS300VoiceParameter.VAR_PARAM_LSB_10.default
    var playMode: Byte = QS300VoiceParameter.PLAY_MODE.default
    var portaSwitch: Byte = QS300VoiceParameter.PORTA_SWITCH.default
    var portaTime: Byte = QS300VoiceParameter.PORTA_TIME.default
    var bendWheelPitch: Byte = QS300VoiceParameter.BEND_WHEEL_PITCH.default
    var bendWheelCutoff: Byte = QS300VoiceParameter.BEND_WHEEL_CUTOFF.default
    var bendWheelAmp: Byte = QS300VoiceParameter.BEND_WHEEL_AMP.default
    var bendWheelPm: Byte = QS300VoiceParameter.BEND_WHEEL_PM.default
    var bendWheelFm: Byte = QS300VoiceParameter.BEND_WHEEL_FM.default
    var bendWheelAm: Byte = QS300VoiceParameter.BEND_WHEEL_AM.default
    var modWheelPitch: Byte = QS300VoiceParameter.MOD_WHEEL_PITCH.default
    var modWheelCutoff: Byte = QS300VoiceParameter.MOD_WHEEL_CUTOFF.default
    var modWheelAmp: Byte = QS300VoiceParameter.MOD_WHEEL_AMP.default
    var modWheelPm: Byte = QS300VoiceParameter.MOD_WHEEL_PM.default
    var modWheelFm: Byte = QS300VoiceParameter.MOD_WHEEL_FM.default
    var modWheelAm: Byte = QS300VoiceParameter.MOD_WHEEL_AM.default
    var modWheelVarEf: Byte = QS300VoiceParameter.MOD_WHEEL_VAR_EF.default
    var afterTouchPitch: Byte = QS300VoiceParameter.AFTER_TOUCH_PITCH.default
    var afterTouchCutoff: Byte = QS300VoiceParameter.AFTER_TOUCH_CUTOFF.default
    var afterTouchAmp: Byte = QS300VoiceParameter.AFTER_TOUCH_AMP.default
    var afterTouchPm: Byte = QS300VoiceParameter.AFTER_TOUCH_PM.default
    var afterTouchFm: Byte = QS300VoiceParameter.AFTER_TOUCH_FM.default
    var afterTouchAm: Byte = QS300VoiceParameter.AFTER_TOUCH_AM.default
    var footCtrlPitch: Byte = QS300VoiceParameter.FOOT_CTRL_PITCH.default
    var footCtrlCutoff: Byte = QS300VoiceParameter.FOOT_CTRL_CUTOFF.default
    var footCtrlAmp: Byte = QS300VoiceParameter.FOOT_CTRL_AMP.default
    var footCtrlPm: Byte = QS300VoiceParameter.FOOT_CTRL_PM.default
    var footCtrlFm: Byte = QS300VoiceParameter.FOOT_CTRL_FM.default
    var footCtrlAm: Byte = QS300VoiceParameter.FOOT_CTRL_AM.default
    var footCtrlVarEf: Byte = QS300VoiceParameter.FOOT_CTRL_VAR_EF.default

    // TODO: Create constants for indices and setup values
    // TODO: Create method that creates header byte array
    fun generateBulkDump(): MidiMessage {
        val midiBytes = ByteArray(MidiConstants.QS300_BULK_DUMP_TOTAL_SIZE) { 0 }
        midiBytes[0] = MidiConstants.EXCLUSIVE_STATUS_BYTE
        midiBytes[1] = MidiConstants.YAMAHA_ID_BYTE
        midiBytes[2] = MidiConstants.DEVICE_NUMBER_BULK_DUMP
        midiBytes[3] = MidiConstants.MODEL_ID_QS300
        midiBytes[4] = 1   // Byte count hi
        midiBytes[5] = 125 // Byte count lo
        midiBytes[6] = 17  // Addr hi
        midiBytes[7] =
            0   // Addr mid TODO: This value is different depending on normal voice selection
        midiBytes[8] = 0   // Addr lo

        // Voice name
        for ((nameIndex, i) in (MidiConstants.OFFSET_QS300_BULK_DATA_START until MidiConstants.OFFSET_QS300_BULK_DATA_START + MidiConstants.QS300_VOICE_NAME_SIZE).withIndex()) {
            midiBytes[i] = if (nameIndex < voiceName.length) {
                voiceName[nameIndex].code.toByte()
            } else {
                32
            } // Space
        }

        // Voice common
        for (i in MidiConstants.OFFSET_QS300_BULK_VOICE_COMMON_START until MidiConstants.OFFSET_QS300_BULK_ELEMENT_DATA_START) {
            val addr = i - MidiConstants.OFFSET_QS300_BULK_DATA_START
            val voiceParam = QS300VoiceParameter::baseAddress findBy addr
            val property = voiceParam?.reflectedField
            midiBytes[i] = getPropertyValue(property)
        }

        // Element data
        for (i in 0 until MidiConstants.QS300_MAX_ELEMENTS) {
            if (i < elements.size) {
                val startIndex =
                    MidiConstants.OFFSET_QS300_BULK_ELEMENT_DATA_START + (i * MidiConstants.QS300_ELEMENT_DATA_SIZE)
                for (j in startIndex until startIndex + MidiConstants.QS300_ELEMENT_DATA_SIZE) {
                    val baseAddress =
                        (j - MidiConstants.OFFSET_QS300_BULK_DATA_START - (i * MidiConstants.QS300_ELEMENT_DATA_SIZE)).toUByte()
                    val elementParam = QS300ElementParameter::baseAddress findBy baseAddress
                    val property = elementParam?.reflectedField
                    midiBytes[j] = elements[i].getPropertyValue(property)
                }
            }
        }

        // Checksum
        var dataSum: Byte = 0
        for (i in 4 until MidiConstants.QS300_BULK_DUMP_TOTAL_SIZE - 2) {
            dataSum = dataSum.plus(midiBytes[i]).toByte()
        }
        midiBytes[MidiConstants.QS300_BULK_DUMP_TOTAL_SIZE - 2] = (0 - dataSum).toByte()
        midiBytes[MidiConstants.QS300_BULK_DUMP_TOTAL_SIZE - 1] = MidiConstants.END_BYTE
        return MidiMessage(midiBytes, System.nanoTime() + 10)
    }

    companion object {
        const val EL_1: Byte = 0
        const val EL_2: Byte = 1
        const val EL_12: Byte = 2
    }
}