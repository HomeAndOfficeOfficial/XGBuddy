package com.example.xgbuddy.data

object MidiConstants {

    const val SEND_SCHEDULE_INTERVAL_NANO = 2

    const val END_BYTE: Byte = -9

    const val OFFSET_DEVICE_ID = 3

    // QS300 Bulk Dump
    const val QS300_MAX_ELEMENTS = 2
    const val QS300_PARAM_CHANGE_ADDR_HI = 0x2eu
    const val QS300_PARAM_CHANGE_ADDR_MID = 0u

    const val OFFSET_QS300_BULK_DATA_START = 9

    // These should possibly go into their own enum like the other parameter types
    const val OFFSET_QS300_BULK_EL_SWITCH = OFFSET_QS300_BULK_DATA_START + 11
    const val OFFSET_QS300_BULK_VOICE_LEVEL = OFFSET_QS300_BULK_DATA_START + 12
    const val OFFSET_QS300_BULK_ELEMENT_DATA_START = OFFSET_QS300_BULK_DATA_START + 61

    const val OFFSET_QS300_ELEMENT_PARAM_CHANGE_ADDR = 0x3du

    const val QS300_VOICE_NAME_SIZE = 10
    const val QS300_ELEMENT_DATA_SIZE = 80

    const val MODEL_ID_QS300: Byte = 0x4b
}