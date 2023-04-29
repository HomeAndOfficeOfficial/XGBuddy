package com.example.xgbuddy.data

import com.example.xgbuddy.R

enum class Reverb(val nameRes: Int, val msb: Byte, val lsb: Byte) {
    NO_EFFECT(R.string.vari_no_effect, 0, 0),
    HALL1(R.string.vari_hall_1, 0x01, 0),
    HALL2(R.string.vari_hall_2, 0x01, 0x01),
    ROOM1(R.string.vari_room_1, 0x02, 0),
    ROOM2(R.string.vari_room_2, 0x02, 0x01),
    ROOM3(R.string.vari_room_3, 0x02, 0x02),
    STAGE1(R.string.vari_stage_1, 0x03, 0),
    STAGE2(R.string.vari_stage_2, 0x03, 0x01),
    PLATE(R.string.vari_plate, 0x04, 0),
    WHITE_ROOM(R.string.reverb_white, 0x10, 0),
    TUNNEL(R.string.reverb_basement, 0x13, 0)
}