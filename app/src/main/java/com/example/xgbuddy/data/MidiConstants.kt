package com.example.xgbuddy.data

object MidiConstants {

    const val SEND_SCHEDULE_INTERVAL_NANO = 2

    const val END_BYTE: Byte = -9

    const val OFFSET_DEVICE_ID = 3

    // QS300 Bulk Dump
    const val QS300_MAX_ELEMENTS = 2

    const val OFFSET_QS300_DATA_START = 9
    const val OFFSET_QS300_EL_SWITCH = OFFSET_QS300_DATA_START + 11
    const val OFFSET_QS300_VOICE_LEVEL = OFFSET_QS300_DATA_START + 12
    const val OFFSET_QS300_ELEMENT_DATA_START = OFFSET_QS300_DATA_START + 61

    const val QS300_VOICE_NAME_SIZE = 10
    const val QS300_ELEMENT_DATA_SIZE = 80

    const val MODEL_ID_QS300: Byte = 0x4b
}