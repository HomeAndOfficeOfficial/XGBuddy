package com.yamahaw.xgbuddy.data.qs300

import com.yamahaw.xgbuddy.R
import com.yamahaw.xgbuddy.util.DataFormatUtil
import kotlin.reflect.KMutableProperty

enum class QS300VoiceParameter(
    val nameRes: Int,
    val baseAddress: UByte,
    val min: Byte,
    val max: Byte,
    val default: Byte,
    val reflectedField: KMutableProperty<Byte>,
    val formatter: DataFormatUtil.DataAssignFormatter = DataFormatUtil.signed127Formatter
) {
    VOICE_CATEGORY(
        R.string.qs300_voice_voice_category,
        0x0au,
        0,
        0x15,
        0,
        QS300Voice::voiceCategory
    ),
    EL_SWITCH(R.string.qs300_voice_el_switch, 0x0bu, 0, 0x0f, 3, QS300Voice::elementSwitch),
    VOICE_LVL(R.string.qs300_voice_voice_lvl, 0x0cu, 0, 0x7f, 100, QS300Voice::voiceLevel),
    VEL_SENS_DEPTH(
        R.string.qs300_voice_vel_sens_depth,
        0x0du,
        0,
        0x7f,
        127,
        QS300Voice::velSensDepth
    ),
    VEL_SENS_OFFSET(
        R.string.qs300_voice_vel_sens_offset,
        0x0eu,
        0,
        0x7f,
        63,
        QS300Voice::velSensOffset
    ),
    REVERB_SEND_LVL(
        R.string.qs300_voice_reverb_send_lvl,
        0x0fu,
        0,
        0x7f,
        0,
        QS300Voice::reverbSendLvl
    ),
    CHORUS_SEND_LVL(
        R.string.qs300_voice_chorus_send_lvl,
        0x10u,
        0,
        0x7f,
        0,
        QS300Voice::chorusSendLvl
    ),
    SEND_CHOR_TO_REV(
        R.string.qs300_voice_send_chor_to_rev,
        0x11u,
        0,
        0x7f,
        0,
        QS300Voice::sendChorToReverb
    ),
    VAR_TYPE_MSB(R.string.qs300_voice_var_type_msb, 0x12u, 0, 0x7f, 0, QS300Voice::varTypeMsb),
    VAR_TYPE_LSB(R.string.qs300_voice_var_type_lsb, 0x13u, 0, 0x7f, 0, QS300Voice::varTypeLsb),
    VAR_PARAM_MSB_1(
        R.string.qs300_voice_var_param_msb_1,
        0x14u,
        0,
        0x7f,
        0,
        QS300Voice::varParamMsb1
    ),
    VAR_PARAM_LSB_1(
        R.string.qs300_voice_var_param_lsb_1,
        0x15u,
        0,
        0x7f,
        0,
        QS300Voice::varParamLsb1
    ),
    VAR_PARAM_MSB_2(
        R.string.qs300_voice_var_param_msb_2,
        0x16u,
        0,
        0x7f,
        0,
        QS300Voice::varParamMsb2
    ),
    VAR_PARAM_LSB_2(
        R.string.qs300_voice_var_param_lsb_2,
        0x17u,
        0,
        0x7f,
        0,
        QS300Voice::varParamLsb2
    ),
    VAR_PARAM_MSB_3(
        R.string.qs300_voice_var_param_msb_3,
        0x18u,
        0,
        0x7f,
        0,
        QS300Voice::varParamMsb3
    ),
    VAR_PARAM_LSB_3(
        R.string.qs300_voice_var_param_lsb_3,
        0x19u,
        0,
        0x7f,
        0,
        QS300Voice::varParamLsb3
    ),
    VAR_PARAM_MSB_4(
        R.string.qs300_voice_var_param_msb_4,
        0x1au,
        0,
        0x7f,
        0,
        QS300Voice::varParamMsb4
    ),
    VAR_PARAM_LSB_4(
        R.string.qs300_voice_var_param_lsb_4,
        0x1bu,
        0,
        0x7f,
        0,
        QS300Voice::varParamLsb4
    ),
    VAR_PARAM_MSB_5(
        R.string.qs300_voice_var_param_msb_5,
        0x1cu,
        0,
        0x7f,
        0,
        QS300Voice::varParamMsb5
    ),
    VAR_PARAM_LSB_5(
        R.string.qs300_voice_var_param_lsb_5,
        0x1du,
        0,
        0x7f,
        0,
        QS300Voice::varParamLsb5
    ),
    VAR_ATTEN_LVL(R.string.qs300_voice_var_atten_lvl, 0x1eu, 0, 0x7f, 0, QS300Voice::varAttenLvl),
    VAR_PARAM_LSB_10(
        R.string.qs300_voice_var_param_lsb_10,
        0x1fu,
        0,
        0x7f,
        0,
        QS300Voice::varParamLsb10
    ),
    PLAY_MODE(R.string.qs300_voice_play_mode, 0x20u, 0, 0x01, 1, QS300Voice::playMode),
    PORTA_SWITCH(R.string.qs300_voice_porta_switch, 0x21u, 0, 0x01, 0, QS300Voice::portaSwitch),
    PORTA_TIME(R.string.qs300_voice_porta_time, 0x22u, 0, 0x7f, 0, QS300Voice::portaTime),
    BEND_WHEEL_PITCH(
        R.string.qs300_voice_bend_wheel_pitch,
        0x23u,
        0x28,
        0x58,
        0x41,
        QS300Voice::bendWheelPitch
    ),
    BEND_WHEEL_CUTOFF(
        R.string.qs300_voice_bend_wheel_cutoff,
        0x24u,
        0,
        0x7f,
        0x3f,
        QS300Voice::bendWheelCutoff
    ),
    BEND_WHEEL_AMP(
        R.string.qs300_voice_bend_wheel_amp,
        0x25u,
        0,
        0x7f,
        0x3f,
        QS300Voice::bendWheelAmp
    ),
    BEND_WHEEL_PM(R.string.qs300_voice_bend_wheel_pm, 0x26u, 0, 0x7f, 0, QS300Voice::bendWheelPm),
    BEND_WHEEL_FM(R.string.qs300_voice_bend_wheel_fm, 0x27u, 0, 0x7f, 0, QS300Voice::bendWheelFm),
    BEND_WHEEL_AM(R.string.qs300_voice_bend_wheel_am, 0x28u, 0, 0x7f, 0, QS300Voice::bendWheelAm),
    MOD_WHEEL_PITCH(
        R.string.qs300_voice_mod_wheel_pitch,
        0x29u,
        0x28,
        0x58,
        0x41,
        QS300Voice::modWheelPitch
    ),
    MOD_WHEEL_CUTOFF(
        R.string.qs300_voice_mod_wheel_cutoff,
        0x2au,
        0,
        0x7f,
        0x3f,
        QS300Voice::modWheelCutoff
    ),
    MOD_WHEEL_AMP(
        R.string.qs300_voice_mod_wheel_amp,
        0x2bu,
        0,
        0x7f,
        0x3f,
        QS300Voice::modWheelAmp
    ),
    MOD_WHEEL_PM(R.string.qs300_voice_mod_wheel_pm, 0x2cu, 0, 0x7f, 0, QS300Voice::modWheelPm),
    MOD_WHEEL_FM(R.string.qs300_voice_mod_wheel_fm, 0x2du, 0, 0x7f, 0, QS300Voice::modWheelFm),
    MOD_WHEEL_AM(R.string.qs300_voice_mod_wheel_am, 0x2eu, 0, 0x7f, 0, QS300Voice::modWheelAm),
    MOD_WHEEL_VAR_EF(
        R.string.qs300_voice_mod_wheel_var_ef,
        0x2fu,
        0x01,
        0x7f,
        0x3f,
        QS300Voice::modWheelVarEf
    ),
    AFTER_TOUCH_PITCH(
        R.string.qs300_voice_after_touch_pitch,
        0x30u,
        0x28,
        0x58,
        0x3f,
        QS300Voice::afterTouchPitch
    ),
    AFTER_TOUCH_CUTOFF(
        R.string.qs300_voice_after_touch_cutoff,
        0x31u,
        0,
        0x7f,
        0x3f,
        QS300Voice::afterTouchCutoff
    ),
    AFTER_TOUCH_AMP(
        R.string.qs300_voice_after_touch_amp,
        0x32u,
        0,
        0x7f,
        0x3f,
        QS300Voice::afterTouchAmp
    ),
    AFTER_TOUCH_PM(
        R.string.qs300_voice_after_touch_pm,
        0x33u,
        0,
        0x7f,
        0,
        QS300Voice::afterTouchPm
    ),
    AFTER_TOUCH_FM(
        R.string.qs300_voice_after_touch_fm,
        0x34u,
        0,
        0x7f,
        0,
        QS300Voice::afterTouchFm
    ),
    AFTER_TOUCH_AM(
        R.string.qs300_voice_after_touch_am,
        0x35u,
        0,
        0x7f,
        0,
        QS300Voice::afterTouchAm
    ),
    FOOT_CTRL_PITCH(
        R.string.qs300_voice_foot_ctrl_pitch,
        0x36u,
        0x28,
        0x58,
        0x41,
        QS300Voice::footCtrlPitch
    ),
    FOOT_CTRL_CUTOFF(
        R.string.qs300_voice_foot_ctrl_cutoff,
        0x37u,
        0,
        0x7f,
        0x3f,
        QS300Voice::footCtrlCutoff
    ),
    FOOT_CTRL_AMP(
        R.string.qs300_voice_foot_ctrl_amp,
        0x38u,
        0,
        0x7f,
        0x3f,
        QS300Voice::footCtrlAmp
    ),
    FOOT_CTRL_PM(R.string.qs300_voice_foot_ctrl_pm, 0x39u, 0, 0x7f, 0, QS300Voice::footCtrlPm),
    FOOT_CTRL_FM(R.string.qs300_voice_foot_ctrl_fm, 0x3au, 0, 0x7f, 0, QS300Voice::footCtrlFm),
    FOOT_CTRL_AM(R.string.qs300_voice_foot_ctrl_am, 0x3bu, 0, 0x7f, 0, QS300Voice::footCtrlAm),
    FOOT_CTRL_VAR_EF(
        R.string.qs300_voice_foot_ctrl_var_ef,
        0x3cu,
        0x01,
        0x7f,
        0x3f,
        QS300Voice::footCtrlVarEf
    )
}