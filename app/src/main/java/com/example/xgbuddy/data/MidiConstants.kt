package com.example.xgbuddy.data

object MidiConstants {

    const val END_BYTE: Byte = -9

    //TODO: Verify terminology for these constants
    const val OFFSET_DEVICE_ID = 3
    const val OFFSET_QS300_DATA_START = 9
    const val OFFSET_QS300_ELEMENT_DATA_START = OFFSET_QS300_DATA_START + 61

    const val QS300_VOICE_NAME_SIZE = 10
    const val QS300_ELEMENT_DATA_SIZE = 80

    const val MODEL_ID_QS300: Byte = 0x4b
}