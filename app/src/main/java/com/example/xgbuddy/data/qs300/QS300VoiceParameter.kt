package com.example.xgbuddy.data.qs300

import com.example.xgbuddy.R
import com.example.xgbuddy.util.DataFormatUtil
import kotlin.reflect.KMutableProperty

enum class QS300VoiceParameter(
    val baseAddress: UByte,
    val default: Byte,
    val min: Byte,
    val max: Byte,
    val descriptionRes: Int,
    val reflectedField: KMutableProperty<Byte>,
    val formatter: DataFormatUtil.DataAssignFormatter = DataFormatUtil.signed127Formatter
) {
    VOICE_CATEGORY(
        0x0au,
        0,
        0,
        0x15,
        R.string.qs300_voice_voice_category,
        QS300Voice::voiceCategory
    ),
    EL_SWITCH(0x0bu, 3, 0, 0x0f, R.string.qs300_voice_el_switch, QS300Voice::elementSwitch),
    VOICE_LVL(0x0cu, 100, 0, 0x7f, R.string.qs300_voice_voice_lvl, QS300Voice::voiceLevel),
    VEL_SENS_DEPTH(
        0x0du,
        127,
        0,
        0x7f,
        R.string.qs300_voice_vel_sens_depth,
        QS300Voice::velSensDepth
    ),
    VEL_SENS_OFFSET(
        0x0eu,
        63,
        0,
        0x7f,
        R.string.qs300_voice_vel_sens_offset,
        QS300Voice::velSensOffset
    ),
    REVERB_SEND_LVL(
        0x0fu,
        0,
        0,
        0x7f,
        R.string.qs300_voice_reverb_send_lvl,
        QS300Voice::reverbSendLvl
    ),
    CHORUS_SEND_LVL(
        0x10u,
        0,
        0,
        0x7f,
        R.string.qs300_voice_chorus_send_lvl,
        QS300Voice::chorusSendLvl
    ),
    SEND_CHOR_TO_REV(
        0x11u,
        0,
        0,
        0x7f,
        R.string.qs300_voice_send_chor_to_rev,
        QS300Voice::sendChorToReverb
    ),
    VAR_TYPE_MSB(0x12u, 0, 0, 0x7f, R.string.qs300_voice_var_type_msb, QS300Voice::varTypeMsb),
    VAR_TYPE_LSB(0x13u, 0, 0, 0x7f, R.string.qs300_voice_var_type_lsb, QS300Voice::varTypeLsb),
    VAR_PARAM_MSB_1(
        0x14u,
        0,
        0,
        0x7f,
        R.string.qs300_voice_var_param_msb_1,
        QS300Voice::varParamMsb1
    ),
    VAR_PARAM_LSB_1(
        0x15u,
        0,
        0,
        0x7f,
        R.string.qs300_voice_var_param_lsb_1,
        QS300Voice::varParamLsb1
    ),
    VAR_PARAM_MSB_2(
        0x16u,
        0,
        0,
        0x7f,
        R.string.qs300_voice_var_param_msb_2,
        QS300Voice::varParamMsb2
    ),
    VAR_PARAM_LSB_2(
        0x17u,
        0,
        0,
        0x7f,
        R.string.qs300_voice_var_param_lsb_2,
        QS300Voice::varParamLsb2
    ),
    VAR_PARAM_MSB_3(
        0x18u,
        0,
        0,
        0x7f,
        R.string.qs300_voice_var_param_msb_3,
        QS300Voice::varParamMsb3
    ),
    VAR_PARAM_LSB_3(
        0x19u,
        0,
        0,
        0x7f,
        R.string.qs300_voice_var_param_lsb_3,
        QS300Voice::varParamLsb3
    ),
    VAR_PARAM_MSB_4(
        0x1au,
        0,
        0,
        0x7f,
        R.string.qs300_voice_var_param_msb_4,
        QS300Voice::varParamMsb4
    ),
    VAR_PARAM_LSB_4(
        0x1bu,
        0,
        0,
        0x7f,
        R.string.qs300_voice_var_param_lsb_4,
        QS300Voice::varParamLsb4
    ),
    VAR_PARAM_MSB_5(
        0x1cu,
        0,
        0,
        0x7f,
        R.string.qs300_voice_var_param_msb_5,
        QS300Voice::varParamMsb5
    ),
    VAR_PARAM_LSB_5(
        0x1du,
        0,
        0,
        0x7f,
        R.string.qs300_voice_var_param_lsb_5,
        QS300Voice::varParamLsb5
    ),
    VAR_ATTEN_LVL(0x1eu, 0, 0, 0x7f, R.string.qs300_voice_var_atten_lvl, QS300Voice::varAttenLvl),
    VAR_PARAM_LSB_10(
        0x1fu,
        0,
        0,
        0x7f,
        R.string.qs300_voice_var_param_lsb_10,
        QS300Voice::varParamLsb10
    ),
    PLAY_MODE(0x20u, 1, 0, 0x01, R.string.qs300_voice_play_mode, QS300Voice::playMode),
    PORTA_SWITCH(0x21u, 0, 0, 0x01, R.string.qs300_voice_porta_switch, QS300Voice::portaSwitch),
    PORTA_TIME(0x22u, 0, 0, 0x7f, R.string.qs300_voice_porta_time, QS300Voice::portaTime),
    BEND_WHEEL_PITCH(
        0x23u,
        0x41,
        0x28,
        0x58,
        R.string.qs300_voice_bend_wheel_pitch,
        QS300Voice::bendWheelPitch
    ),
    BEND_WHEEL_CUTOFF(
        0x24u,
        0x3f,
        0,
        0x7f,
        R.string.qs300_voice_bend_wheel_cutoff,
        QS300Voice::bendWheelCutoff
    ),
    BEND_WHEEL_AMP(
        0x25u,
        0x3f,
        0,
        0x7f,
        R.string.qs300_voice_bend_wheel_amp,
        QS300Voice::bendWheelAmp
    ),
    BEND_WHEEL_PM(0x26u, 0, 0, 0x7f, R.string.qs300_voice_bend_wheel_pm, QS300Voice::bendWheelPm),
    BEND_WHEEL_FM(0x27u, 0, 0, 0x7f, R.string.qs300_voice_bend_wheel_fm, QS300Voice::bendWheelFm),
    BEND_WHEEL_AM(0x28u, 0, 0, 0x7f, R.string.qs300_voice_bend_wheel_am, QS300Voice::bendWheelAm),
    MOD_WHEEL_PITCH(
        0x29u,
        0x41,
        0x28,
        0x58,
        R.string.qs300_voice_mod_wheel_pitch,
        QS300Voice::modWheelPitch
    ),
    MOD_WHEEL_CUTOFF(
        0x2au,
        0x3f,
        0,
        0x7f,
        R.string.qs300_voice_mod_wheel_cutoff,
        QS300Voice::modWheelCutoff
    ),
    MOD_WHEEL_AMP(
        0x2bu,
        0x3f,
        0,
        0x7f,
        R.string.qs300_voice_mod_wheel_amp,
        QS300Voice::modWheelAmp
    ),
    MOD_WHEEL_PM(0x2cu, 0, 0, 0x7f, R.string.qs300_voice_mod_wheel_pm, QS300Voice::modWheelPm),
    MOD_WHEEL_FM(0x2du, 0, 0, 0x7f, R.string.qs300_voice_mod_wheel_fm, QS300Voice::modWheelFm),
    MOD_WHEEL_AM(0x2eu, 0, 0, 0x7f, R.string.qs300_voice_mod_wheel_am, QS300Voice::modWheelAm),
    MOD_WHEEL_VAR_EF(
        0x2fu,
        0x3f,
        0x01,
        0x7f,
        R.string.qs300_voice_mod_wheel_var_ef,
        QS300Voice::modWheelVarEf
    ),
    AFTER_TOUCH_PITCH(
        0x30u,
        0x3f,
        0x28,
        0x58,
        R.string.qs300_voice_after_touch_pitch,
        QS300Voice::afterTouchPitch
    ),
    AFTER_TOUCH_CUTOFF(
        0x31u,
        0x3f,
        0,
        0x7f,
        R.string.qs300_voice_after_touch_cutoff,
        QS300Voice::afterTouchCutoff
    ),
    AFTER_TOUCH_AMP(
        0x32u,
        0x3f,
        0,
        0x7f,
        R.string.qs300_voice_after_touch_amp,
        QS300Voice::afterTouchAmp
    ),
    AFTER_TOUCH_PM(
        0x33u,
        0,
        0,
        0x7f,
        R.string.qs300_voice_after_touch_pm,
        QS300Voice::afterTouchPm
    ),
    AFTER_TOUCH_FM(
        0x34u,
        0,
        0,
        0x7f,
        R.string.qs300_voice_after_touch_fm,
        QS300Voice::afterTouchFm
    ),
    AFTER_TOUCH_AM(
        0x35u,
        0,
        0,
        0x7f,
        R.string.qs300_voice_after_touch_am,
        QS300Voice::afterTouchAm
    ),
    FOOT_CTRL_PITCH(
        0x36u,
        0x41,
        0x28,
        0x58,
        R.string.qs300_voice_foot_ctrl_pitch,
        QS300Voice::footCtrlPitch
    ),
    FOOT_CTRL_CUTOFF(
        0x37u,
        0x3f,
        0,
        0x7f,
        R.string.qs300_voice_foot_ctrl_cutoff,
        QS300Voice::footCtrlCutoff
    ),
    FOOT_CTRL_AMP(
        0x38u,
        0x3f,
        0,
        0x7f,
        R.string.qs300_voice_foot_ctrl_amp,
        QS300Voice::footCtrlAmp
    ),
    FOOT_CTRL_PM(0x39u, 0, 0, 0x7f, R.string.qs300_voice_foot_ctrl_pm, QS300Voice::footCtrlPm),
    FOOT_CTRL_FM(0x3au, 0, 0, 0x7f, R.string.qs300_voice_foot_ctrl_fm, QS300Voice::footCtrlFm),
    FOOT_CTRL_AM(0x3bu, 0, 0, 0x7f, R.string.qs300_voice_foot_ctrl_am, QS300Voice::footCtrlAm),
    FOOT_CTRL_VAR_EF(
        0x3cu,
        0x3f,
        0x01,
        0x7f,
        R.string.qs300_voice_foot_ctrl_var_ef,
        QS300Voice::footCtrlVarEf
    )
}