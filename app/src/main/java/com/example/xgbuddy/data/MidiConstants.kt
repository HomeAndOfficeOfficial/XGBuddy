package com.example.xgbuddy.data

object MidiConstants {

    const val SETUP_SEQUENCE_INTERVAL_NANO = 60000000L
    const val SETUP_SEQUENCE_DRUM_INTERVAL = 30000000L

    const val STATUS_NOTE_OFF = 0x80
    const val STATUS_NOTE_ON = 0x90
    const val STATUS_CONTROL_CHANGE = 0xB0
    const val STATUS_PROGRAM_CHANGE = 0xC0

    const val EXCLUSIVE_STATUS_BYTE: Byte = -16
    const val YAMAHA_ID: Byte = 67
    const val DEVICE_NUMBER: Byte = 0x10
    const val DEVICE_NUMBER_BULK_DUMP: Byte = 0
    const val UNIVERSAL_NON_REALTIME: Byte = 0x7e
    const val TARGET_ID: Byte = 0x7f
    const val SUB_ID_GM_MSG: Byte = 9
    const val SUB_ID_GM_ON: Byte = 1

    const val SYSEX_END: Byte = -9 // 0xf7

    const val OFFSET_DEVICE_ID = 3

    // QS300 Bulk Dump
    const val QS300_BULK_DUMP_TOTAL_SIZE = 392
    const val QS300_MAX_ELEMENTS = 2
    const val QS300_BULK_DUMP_ELEMENTS = 4
    const val QS300_PARAM_CHANGE_ADDR_HI = 0x2eu
    const val QS300_PARAM_CHANGE_ADDR_MID = 0u

    const val OFFSET_QS300_BULK_DATA_START = 9

    // These should possibly go into their own enum like the other parameter types
    const val OFFSET_QS300_BULK_VOICE_COMMON_START = OFFSET_QS300_BULK_DATA_START + 10
    const val OFFSET_QS300_BULK_ELEMENT_DATA_START = OFFSET_QS300_BULK_DATA_START + 61

    const val OFFSET_QS300_ELEMENT_PARAM_CHANGE_ADDR = 0x3du

    const val QS300_VOICE_NAME_SIZE = 10
    const val QS300_ELEMENT_DATA_SIZE = 80

    const val MODEL_ID_QS300: Byte = 0x4b
    const val MODEL_ID_XG: Byte = 0x4c

    const val XG_MULTIPART_BULK_TOTAL_SIZE = 122
    const val XG_MULTIPART_BULK_DATA_SIZE: Byte = 111
    const val XG_EFFECT_BULK_TOTAL_SIZE = 129
    const val XG_EFFECT_BULK_DATA_SIZE: Byte = 118
    const val XG_NORMAL_VOICE_MSB: Byte = 0
    const val XG_SFX_VOICE_MSB: Byte = 64
    const val XG_SFX_VOICE_LSB: Byte = 0
    const val XG_DRUM_MSB: Byte = 127
    const val XG_DRUM_LSB: Byte = 0
    const val XG_MP_PARAM_ADDR_HI: Byte = 8
    const val XG_EFFECT_PARAM_ADDR_HI: Byte = 2
    const val XG_EFFECT_PARAM_ADDR_MID: Byte = 1
    const val XG_INITIAL_DRUM_NOTE = 13

    const val XG_SYS_ADDR_TUNE: Byte = 0
    const val XG_SYS_ADDR_VOLUME: Byte = 4
    const val XG_SYS_ADDR_TRANSPOSE: Byte = 6

    val XY_SYSTEM_ON_ARRAY = byteArrayOf(
        EXCLUSIVE_STATUS_BYTE,
        YAMAHA_ID,
        DEVICE_NUMBER,
        MODEL_ID_XG,
        0,
        0,
        0x7e,
        0,
        SYSEX_END
    )

    val GM_MODE_ON_ARRAY = byteArrayOf(
        EXCLUSIVE_STATUS_BYTE,
        UNIVERSAL_NON_REALTIME,
        TARGET_ID,
        SUB_ID_GM_MSG,
        SUB_ID_GM_ON,
        SYSEX_END
    )

    val DRUM_SETUP_RESET_ARRAY = byteArrayOf(
        EXCLUSIVE_STATUS_BYTE,
        YAMAHA_ID,
        DEVICE_NUMBER,
        MODEL_ID_XG,
        0,
        0,
        0x7d,
        0, // Replace this with drum setup number
        SYSEX_END
    )

    val ALL_PARAM_RESET_ARRAY = byteArrayOf(
        EXCLUSIVE_STATUS_BYTE,
        YAMAHA_ID,
        DEVICE_NUMBER,
        MODEL_ID_XG,
        0,
        0,
        0X7f,
        0,
        SYSEX_END
    )
}