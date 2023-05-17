package com.example.xgbuddy.data.qs300

import com.example.xgbuddy.data.MidiData

data class QS300Voice(var voiceName: String = "") : MidiData() {

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

    fun updateElementStatus(elementIndex: Int, isOn: Boolean) {
        if (elementIndex < 2) { // Don't mess with 3 or 4 for now
            // TODO : Figure out how to do this
        }
    }

    companion object {
        const val EL_1: Byte = 0
        const val EL_2: Byte = 1
        const val EL_12: Byte = 2
    }
}