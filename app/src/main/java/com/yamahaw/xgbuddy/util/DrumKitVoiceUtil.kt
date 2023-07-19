package com.yamahaw.xgbuddy.util

import com.yamahaw.xgbuddy.data.xg.drum.DrumVoice

object DrumKitVoiceUtil {
    private const val SURDO_MUTE = "Surdo Mute"
    private const val SURDO_OPEN = "Surdo Open"
    private const val HI_Q = "Hi Q"
    private const val WHIP_SLAP = "Whip Slap"
    private const val SCRATCH_PUSH = "Scratch Push"
    private const val SCRATCH_PULL = "Scratch Pull"
    private const val FINGER_SNAP = "Finger Snap"
    private const val CLICK_NOISE = "Click Noise"
    private const val METR_CLICK = "Metronome Clk"
    private const val METR_BELL = "Metronome Bell"
    private const val SEQ_CLICK_L = "Seq Click L"
    private const val SEQ_CLICK_H = "Seq Clk M"
    private const val BRUSH_TAP = "Brush Tap"
    private const val BRUSH_SWIRL_L = "Brush Swirl L"
    private const val BRUSH_SLAP = "Brush Slap"
    private const val BRUSH_SWIRL_H = "Brush Swirl H"
    private const val SNARE_ROLL = "Snare Roll"
    private const val CASTANET = "Castanet"
    private const val SNARE_L = "Snare L"
    private const val STICKS = "Sticks"
    private const val BASS_DRUM_L = "Bass Drum L"
    private const val BASS_DRUM_H = "Bass Drum H"
    private const val SIDE_STICK = "Side Stick"
    private const val OPEN_RIM = "Open Rim Shot"
    private const val BASS_DRUM_M = "Bass Drum M"
    private const val SNARE_M = "Snare M"
    private const val HAND_CLAP = "Hand Clap"
    private const val SNARE_H = "Snare H"
    private const val FLOOR_TOM_L = "Floor Tom L"
    private const val HH_CLOSE = "Hi-Hat Closed"
    private const val FLOOR_TOM_H = "Floor Tom H"
    private const val HH_PEDAL = "Hi-Hat Ped"
    private const val LOW_TOM = "Low Tom"
    private const val HH_OPEN = "Hi-Hat Open"
    private const val MID_TOM_L = "Mid Tom L"
    private const val MID_TOM_H = "Mid Tom H"
    private const val CRASH_CYM_1 = "Crash 1"
    private const val HIGH_TOM = "High Tom"
    private const val RIDE_CYM_1 = "Ride 1"
    private const val CHINESE_CYM = "China"
    private const val RIDE_CYM_CUP = "Ride Bell"
    private const val TAMBOURINE = "Tambourine"
    private const val SPLASH_CYM = "Splash"
    private const val COWBELL = "Cowbell"
    private const val CRASH_CYM_2 = "Crash 2"
    private const val VIBRASLAP = "Vibra"
    private const val RIDE_CYM_2 = "Ride 2"
    private const val BONGO_H = "Bongo H"
    private const val BONGO_L = "Bongo L"
    private const val CONGA_H_MUTE = "Conga H Mute"
    private const val CONGA_H_OPEN = "Conga H Open"
    private const val CONGA_L = "Conga L"
    private const val TIMBALE_H = "Timbale H"
    private const val TIMBALE_L = "Timbale L"
    private const val AGOGO_H = "Agogo H"
    private const val AGOGO_L = "Agogo L"
    private const val CABASA = "Cabasa"
    private const val MARACAS = "Maracas"
    private const val SAMBA_WHISTLE_H = "Samba Whistle H"
    private const val SAMBA_WHISTLE_L = "Samba Whistle L"
    private const val GUIRO_SHORT = "Guiro Short"
    private const val GUIRO_LONG = "Guiro Long"
    private const val CLAVES = "Claves"
    private const val WOOD_BLOCK_H = "Wood Block H"
    private const val WOOD_BLOCK_L = "Wood Block L"
    private const val CUICA_MUTE = "Cuica Mute"
    private const val CUICA_OPEN = "Cuica Open"
    private const val TRIANGLE_MUTE = "Triangle Mute"
    private const val TRIANGLE_OPEN = "Triangle Open"
    private const val SHAKER = "Shaker"
    private const val JINGLE_BELL = "Jingle Bell"
    private const val BELL_TREE = "Bell Tree"
    private const val SNARE_ROLL_2 = "Snare Roll 2"
    private const val SNARE_L_2 = "Snare L 2"
    private const val OPEN_RIM_2 = "Open Rim Shot 2"
    private const val BASS_DRUM_M_2 = "Bass Drum M 2"
    private const val BASS_DRUM_H_2 = "Bass Drum H 2"
    private const val SNARE_M_2 = "Snare M 2"
    private const val SNARE_H_2 = "Snare H 2"
    private const val SD_ROOM_L = "SD Room L"
    private const val SD_ROOM_H = "SD Room H"
    private const val ROOM_TOM_1 = "Room Tom 1"
    private const val ROOM_TOM_2 = "Room Tom 2"
    private const val ROOM_TOM_3 = "Room Tom 3"
    private const val ROOM_TOM_4 = "Room Tom 4"
    private const val ROOM_TOM_5 = "Room Tom 5"
    private const val ROOM_TOM_6 = "Room Tom 6"
    private const val SD_ROCK_M = "SD Rock M"
    private const val BASS_DRUM_H_3 = "Bass Drum H 3"
    private const val BD_ROCK = "BD Rock"
    private const val SD_ROCK = "SD Rock"
    private const val SD_ROCK_RIM = "SD Rock Rim"
    private const val ROCK_TOM_1 = "Rock Tom 1"
    private const val ROCK_TOM_2 = "Rock Tom 2"
    private const val ROCK_TOM_3 = "Rock Tom 3"
    private const val ROCK_TOM_4 = "Rock Tom 4"
    private const val ROCK_TOM_5 = "Rock Tom 5"
    private const val ROCK_TOM_6 = "Rock Tom 6"
    private const val REVERSE_CYM = "Reverse Cymbal"
    private const val BASS_DRUM_H_4 = "Bass Drum H 4"
    private const val BD_GATE = "BD Gate"
    private const val SD_ROCK_L = "SD Rock L"
    private const val SD_ROCK_H = "SD Rock H"
    private const val E_TOM_1 = "E. Tom 1"
    private const val E_TOM_2 = "E. Tom 2"
    private const val E_TOM_3 = "E. Tom 3"
    private const val E_TOM_4 = "E. Tom 4"
    private const val E_TOM_5 = "E. Tom 5"
    private const val E_TOM_6 = "E. Tom 6"
    private const val BD_ANALOG_L = "BD Analog L"
    private const val BD_ANALOG_H = "BD Analog H"
    private const val ANALOG_STICK = "Analog Stick"
    private const val ANALOG_SNARE_L = "Analog Snare L"
    private const val ANALOG_SNARE_H = "Analog Snare H"
    private const val ANALOG_TOM_1 = "Analog Tom 1"
    private const val ANALOG_TOM_2 = "Analog Tom 2"
    private const val ANALOG_TOM_3 = "Analog Tom 3"
    private const val ANALOG_TOM_4 = "Analog Tom 4"
    private const val ANALOG_TOM_5 = "Analog Tom 5"
    private const val ANALOG_TOM_6 = "Analog Tom 6"
    private const val ANALOG_HH_CLOSE = "Analog HH Close"
    private const val ANALOG_HH_CLOSE_2 = "Analog HH Close 2"
    private const val ANALOG_HH_OPEN = "Analog HH Open"
    private const val ANALOG_CYM = "Analog Cymbal"
    private const val ANALOG_COWBELL = "Analog Cowbell"
    private const val BD_JAZZ = "BD Jazz"
    private const val JAZZ_TOM_1 = "Jazz Tom 1"
    private const val JAZZ_TOM_2 = "Jazz Tom 2"
    private const val JAZZ_TOM_3 = "Jazz Tom 3"
    private const val JAZZ_TOM_4 = "Jazz Tom 4"
    private const val JAZZ_TOM_5 = "Jazz Tom 5"
    private const val JAZZ_TOM_6 = "Jazz Tom 6"
    private const val BRUSH_SLAP_L = "Brush Slap L"
    private const val BD_SOFT = "BD Soft"
    private const val BRUSH_SLAP_M = "Brush Slap M"
    private const val BRUSH_TAP_H = "Brush Tap H"
    private const val BRUSH_TOM_1 = "Brush Tom 1"
    private const val BRUSH_TOM_2 = "Brush Tom 2"
    private const val BRUSH_TOM_3 = "Brush Tom 3"
    private const val BRUSH_TOM_4 = "Brush Tom 4"
    private const val BRUSH_TOM_5 = "Brush Tom 5"
    private const val BRUSH_TOM_6 = "Brush Tom 6"
    private const val BASS_DRUM_L_2 = "Bass Drum L 2"
    private const val GRAN_CASSA = "Gran Cassa"
    private const val GRAN_CASSA_MUTE = "Gran Cassa Mute"
    private const val MARCH_SNARE_M = "March Snare M"
    private const val MARCH_SNARE_H = "March Snare H"
    private const val HAND_CYM_OPEN_L = "Hand Cym. Open L"
    private const val HAND_CYM_CLOSED_L = "Hand Cym. Close L"
    private const val HAND_CYM_OPEN_H = "Hand Cym. Open H"
    private const val HAND_CYM_CLOSED_H = "Hand Cym. Close H"
    private const val ANALOG_CONGA_H = "Analog Congo H"
    private const val ANALOG_CONGA_M = "Analog Congo M"
    private const val ANALOG_CONGA_L = "Analog Congo L"
    private const val ANALOG_MARACAS = "Analog Maracas"
    private const val ANALOG_CLAVES = "Analog Claves"

    private const val INDEX_SNARE_ROLL = 16
    private const val INDEX_BRUSH_SWIRL_H = 15
    private const val INDEX_CASTANET = 17
    private const val INDEX_SNARE_L = 18
    private const val INDEX_BD_L = 20
    private const val INDEX_OPEN_RIM = 21
    private const val INDEX_BD_M = 22
    private const val INDEX_BD_H = 23
    private const val INDEX_STICK = 24
    private const val INDEX_SNARE_M = 25
    private const val INDEX_SNARE_H = 27
    private const val INDEX_FL_TOM_L = 28
    private const val INDEX_HH_CLOSE = 29
    private const val INDEX_FL_TOM_H = 30
    private const val INDEX_HH_PEDL = 31
    private const val INDEX_LOW_TOM = 32
    private const val INDEX_HH_OPEN = 33
    private const val INDEX_MID_TOM_L = 34
    private const val INDEX_MID_TOM_H = 35
    private const val INDEX_CRASH_1 = 36
    private const val INDEX_HI_TOM = 37
    private const val INDEX_RIDE_CYM_1 = 38
    private const val INDEX_COWBELL = 43
    private const val INDEX_CRASH_2 = 44
    private const val INDEX_RIDE_2 = 46
    private const val INDEX_CONGA_H_MUTE = 49
    private const val INDEX_CONGA_H_OPEN = 50
    private const val INDEX_CONGA_L = 51
    private const val INDEX_MARACAS = 57
    private const val INDEX_CLAVES = 62
    private const val INDEX_CUICA_MUTE = 65
    private const val INDEX_CUICA_OPEN = 66

    private val standardKit: List<DrumVoice> = listOf(
        DrumVoice(SURDO_MUTE,64,64,102,3,51,95,95,127,0,0,1,64,64,64,64,64),
        DrumVoice(SURDO_OPEN,64,64,121,3,51,95,95,127,0,0,1,64,64,64,64,64),
        DrumVoice(HI_Q,64,64,63,0,51,127,127,127,0,0,1,64,64,64,64,64),
        DrumVoice(WHIP_SLAP,64,64,127,0,51,127,127,127,0,0,1,64,64,64,64,64),
        DrumVoice(SCRATCH_PUSH,64,64,93,4,52,63,63,127,0,0,1,64,64,64,64,64),
        DrumVoice(SCRATCH_PULL,64,64,116,4,52,63,63,127,0,0,1,64,64,64,64,64),
        DrumVoice(FINGER_SNAP,64,64,127,0,64,75,0,127,0,0,1,64,64,64,64,64),
        DrumVoice(CLICK_NOISE,64,64,127,0,64,127,127,127,0,0,1,64,64,64,64,64),
        DrumVoice(METR_CLICK,64,64,94,0,64,63,63,127,0,0,1,64,64,64,64,64),
        DrumVoice(METR_BELL,64,64,98,0,64,63,63,127,0,0,1,64,64,64,64,64),
        DrumVoice(SEQ_CLICK_L,64,64,87,0,64,127,127,127,0,0,1,64,64,64,64,64),
        DrumVoice(SEQ_CLICK_H,64,64,96,0,64,127,127,127,0,0,1,64,64,64,64,64),
        DrumVoice(BRUSH_TAP,64,64,49,0,64,127,127,127,0,0,1,64,64,64,64,64),
        DrumVoice(BRUSH_SWIRL_L,64,64,47,0,64,127,127,127,0,1,1,64,64,64,64,64),
        DrumVoice(BRUSH_SLAP,64,64,52,0,64,127,127,127,0,0,1,64,64,64,64,64),
        DrumVoice(BRUSH_SWIRL_H,64,64,45,0,64,127,127,127,0,1,1,64,64,64,64,64),
        DrumVoice(SNARE_ROLL,64,64,79,0,64,127,127,127,0,1,1,64,64,64,64,64),
      DrumVoice(CASTANET,64,64,127,0,64,63,63,127,0,0,1,64,64,64,64,64),
        DrumVoice(SNARE_L,64,64,75,0,64,127,127,127,0,0,1,64,64,64,64,64),
        DrumVoice(STICKS,64,64,127,0,64,127,127,127,0,0,1,64,64,64,64,64),
        DrumVoice(BASS_DRUM_L,64,64,116,0,64,32,32,127,0,0,1,64,64,64,64,64),
        DrumVoice(OPEN_RIM,64,64,127,0,64,127,127,127,0,0,1,64,64,64,64,64),
        DrumVoice(BASS_DRUM_M,64,64,102,0,64,32,32,127,0,0,1,64,64,64,64,64),
        DrumVoice(BASS_DRUM_H,64,64,127,0,64,32,32,127,0,0,1,64,64,64,64,64),
        DrumVoice(SIDE_STICK,64,64,93,0,64,127,127,127,0,0,1,64,64,64,64,64),
        DrumVoice(SNARE_M,64,64,127,0,64,127,127,127,0,0,1,64,64,64,64,64),
        DrumVoice(HAND_CLAP,64,64,110,0,64,127,127,127,0,0,1,64,64,64,64,64),
        DrumVoice(SNARE_H,64,64,123,0,64,127,127,127,0,0,1,64,64,64,64,64),
        DrumVoice(FLOOR_TOM_L,64,64,111,0,24,127,127,127,0,0,1,64,64,64,64,64),
        DrumVoice(HH_CLOSE,64,64,91,1,77,32,32,127,0,0,1,64,64,64,64,64),
        DrumVoice(FLOOR_TOM_H,64,64,113,0,39,127,127,127,0,0,1,64,64,64,64,64),
        DrumVoice(HH_PEDAL,64,64,97,1,77,32,32,127,0,0,1,64,64,64,64,64),
        DrumVoice(LOW_TOM,64,64,104,0,52,127,127,127,0,0,1,64,64,64,64,64),
        DrumVoice(HH_OPEN,64,64,96,1,77,32,32,127,0,0,1,64,64,64,64,64),
        DrumVoice(MID_TOM_L,64,64,87,0,64,127,127,127,0,0,1,64,64,64,64,64),
        DrumVoice(MID_TOM_H,64,64,103,0,83,127,127,127,0,0,1,64,64,64,64,64),
        DrumVoice(CRASH_CYM_1,64,64,127,0,69,127,127,127,0,0,1,64,64,64,64,64),
        DrumVoice(HIGH_TOM,64,64,116,0,104,127,127,127,0,0,1,64,64,64,64,64),
        DrumVoice(RIDE_CYM_1,64,64,105,0,34,127,127,127,0,0,1,64,64,64,64,64),
        DrumVoice(CHINESE_CYM,64,64,120,0,34,127,127,127,0,0,1,64,64,64,64,64),
        DrumVoice(RIDE_CYM_CUP,64,64,107,0,46,127,127,127,0,0,1,64,64,64,64,64),
        DrumVoice(TAMBOURINE,64,64,120,0,64,63,63,127,0,0,1,64,64,64,64,64),
        DrumVoice(SPLASH_CYM,64,64,127,0,64,127,127,127,0,0,1,64,64,64,64,64),
        DrumVoice(COWBELL,64,64,118,0,77,63,63,127,0,0,1,64,64,64,64,64),
        DrumVoice(CRASH_CYM_2, 64,64,127,0,51,127,127,127,0,0,1,64,64,64,64,64),
        DrumVoice(VIBRASLAP,64,64,106,0,25,127,127,127,0,0,1,64,64,64,64,64),
        DrumVoice(RIDE_CYM_2, 64,64,110,0,46,127,127,127,0,0,1,64,64,64,64,64),
       DrumVoice(BONGO_H,64,64,110,0,110,95,95,127,0,0,1,64,64,64,64,64),
        DrumVoice(BONGO_L,64,64,87,0,110,95,95,127,0,0,1,64,64,64,64,64),
        DrumVoice(CONGA_H_MUTE,64,64,73,0,39,127,127,127,0,0,1,64,64,64,64,64),
        DrumVoice(CONGA_H_OPEN,64,64,89,0,25,127,127,127,0,0,1,64,64,64,64,64),
        DrumVoice(CONGA_L,64,64,111,0,64,95,95,127,0,0,1,64,64,64,64,64),
        DrumVoice(TIMBALE_H,64,64,91,0,64,127,127,127,0,0,1,64,64,64,64,64),
        DrumVoice(TIMBALE_L,64,64,95,0,64,127,127,127,0,0,1,64,64,64,64,64),
        DrumVoice(AGOGO_H,64,64,108,0,34,100,100,127,0,0,1,64,64,64,64,64),
        DrumVoice(AGOGO_L,64,64,108,0,34,100,100,127,0,0,1,64,64,64,64,64),
        DrumVoice(CABASA,64,64,90,0,28,63,63,127,0,0,1,64,64,64,64,64),
        DrumVoice(MARACAS,64,64,103,0,21,63,63,127,0,0,1,64,64,64,64,64),
        DrumVoice(SAMBA_WHISTLE_H,64,64,103,0,101,127,127,127,0,1,1,64,64,64,64,64),
        DrumVoice(SAMBA_WHISTLE_L,64,64,110,0,101,127,127,127,0,1,1,64,64,64,64,64),
        DrumVoice(GUIRO_SHORT,64,64,124,0,95,63,63,127,0,0,1,64,64,64,64,64),
        DrumVoice(GUIRO_LONG,64,64,106,0,110,63,63,127,0,1,1,64,64,64,64,64),
        DrumVoice(CLAVES,64,64,88,0,64,95,95,127,0,0,1,64,64,64,64,64),
        DrumVoice(WOOD_BLOCK_H,64,64,107,0,104,95,95,127,0,0,1,64,64,64,64,64),
        DrumVoice(WOOD_BLOCK_L,64,64,96,0,104,95,95,127,0,0,1,64,64,64,64,64),
        DrumVoice(CUICA_MUTE,64,64,97,0,21,127,127,127,0,0,1,64,64,64,64,64),
        DrumVoice(CUICA_OPEN,64,64,107,0,34,127,127,127,0,0,1,64,64,64,64,64),
        DrumVoice(TRIANGLE_MUTE,64,64,127,2,25,95,95,127,0,0,1,64,64,64,64,64),
        DrumVoice(TRIANGLE_OPEN,64,64,127,2,25,127,127,127,0,0,1,64,64,64,64,64),
        DrumVoice(SHAKER,64,64,106,0,83,63,63,127,0,0,1,64,64,64,64,64),
        DrumVoice(JINGLE_BELL,64,64,123,0,105,127,127,127,0,0,1,64,64,64,64,64),
        DrumVoice(BELL_TREE,64,64,68,0,64,127,127,127,0,0,1,64,64,64,64,64)
    )

    val standardKit1: List<DrumVoice> = standardKit.map { it.copy() }

    val standardKit2: List<DrumVoice> = standardKit.map { it.copy() }.toMutableList().apply {
        this[INDEX_SNARE_ROLL] = DrumVoice(SNARE_ROLL_2, 64,64,79,0,64,127,127,0,0,1,1,64,64,64,64,64)
        this[INDEX_SNARE_L] = DrumVoice(SNARE_L_2,64,64,75,0,64,127,127,0,0,0,1,64,64,64,64,64)
        this[INDEX_OPEN_RIM] = DrumVoice(OPEN_RIM_2,64,64,127,0,64,127,127,0,0,0,1,64,64,64,64,64)
        this[INDEX_BD_M] = DrumVoice(BASS_DRUM_M_2,64,64,102,0,64,32,32,0,0,0,1,64,64,64,64,64)
        this[INDEX_BD_H] = DrumVoice(BASS_DRUM_H_2,64,64,127,0,64,32,32,0,0,0,1,64,64,64,64,64)
        this[INDEX_SNARE_M] = DrumVoice(SNARE_M_2,64,64,127,0,64,127,127,0,0,0,1,64,64,64,64,64)
        this[INDEX_SNARE_H] = DrumVoice(SNARE_H_2,64,64,123,0,64,127,127,0,0,0,1,64,64,64,64,64)
    }

    var roomKit: List<DrumVoice> = standardKit.map { it.copy() }.toMutableList().apply {
        this[INDEX_SNARE_M] = DrumVoice(SD_ROOM_L,64,64,127,0,64,127,127,127,0,0,1,64,64,64,64,64)
        this[INDEX_SNARE_H] = DrumVoice(SD_ROOM_H,64,64,123,0,64,127,127,127,0,0,1,64,64,64,64,64)
        this[INDEX_FL_TOM_L] = DrumVoice(ROOM_TOM_1,64,64,123,0,24,127,127,127,0,0,1,64,64,64,64,64)
        this[INDEX_FL_TOM_H] = DrumVoice(ROOM_TOM_2,64,64,127,0,39,127,127,127,0,0,1,64,64,64,64,64)
        this[INDEX_LOW_TOM] = DrumVoice(ROOM_TOM_3,64,64,117,0,52,127,127,127,0,0,1,64,64,64,64,64)
        this[INDEX_MID_TOM_L] = DrumVoice(ROOM_TOM_4,64,64,121,0,64,127,127,127,0,0,1,64,64,64,64,64)
        this[INDEX_MID_TOM_H] = DrumVoice(ROOM_TOM_5,64,64,126,0,83,127,127,127,0,0,1,64,64,64,64,64)
        this[INDEX_HI_TOM] = DrumVoice(ROOM_TOM_6,64,64,124,0,95,127,127,127,0,0,1,64,64,64,64,64)
    }

    var rockKit: List<DrumVoice> = standardKit.map { it.copy() }.toMutableList().apply {
        this[INDEX_SNARE_L] = DrumVoice(SD_ROCK_M,64,64,121,0,64,127,127,127,0,0,1,64,64,64,64,64)
        this[INDEX_BD_L] = DrumVoice(BASS_DRUM_M,64,64,111,0,64,32,32,127,0,0,1,64,64,64,64,64)
        this[INDEX_BD_M] = DrumVoice(BASS_DRUM_H_3,64,64,127,0,64,32,32,127,0,0,1,64,64,64,64,64)
        this[INDEX_BD_H] = DrumVoice(BD_ROCK,64,64,119,0,64,32,32,127,0,0,1,64,64,64,64,64)
        this[INDEX_SNARE_M] = DrumVoice(SD_ROCK,64,64,110,0,64,127,127,127,0,0,1,64,64,64,64,64)
        this[INDEX_SNARE_H] = DrumVoice(SD_ROCK_RIM,64,64,119,0,64,127,127,127,0,0,1,64,64,64,64,64)
        this[INDEX_FL_TOM_L] = DrumVoice(ROCK_TOM_1,64,64,123,0,24,127,127,127,0,0,1,64,64,64,64,64)
        this[INDEX_FL_TOM_H] = DrumVoice(ROCK_TOM_2,64,64,127,0,39,127,127,127,0,0,1,64,64,64,64,64)
        this[INDEX_LOW_TOM] = DrumVoice(ROCK_TOM_3,64,64,117,0,52,127,127,127,0,0,1,64,64,64,64,64)
        this[INDEX_MID_TOM_L] = DrumVoice(ROCK_TOM_4,64,64,121,0,64,127,127,127,0,0,1,64,64,64,64,64)
        this[INDEX_MID_TOM_H] = DrumVoice(ROCK_TOM_5,64,64,123,0,83,127,127,127,0,0,1,64,64,64,64,64)
        this[INDEX_HI_TOM] = DrumVoice(ROCK_TOM_6,64,64,124,0,95,127,127,127,0,0,1,64,64,64,64,64)
    }

    var electroKit: List<DrumVoice> = standardKit.map { it.copy() }.toMutableList().apply {
        this[INDEX_BRUSH_SWIRL_H] = DrumVoice(REVERSE_CYM,64,64,100,0,64,127,127,127,0,1,1,64,64,64,64,64)
        this[INDEX_CASTANET] = DrumVoice(HI_Q,64,64,127,0,64,63,63,127,0,0,1,64,64,64,64,64)
        this[INDEX_SNARE_L] = DrumVoice(SNARE_M,64,64,114,0,64,127,127,127,0,0,1,64,64,64,64,64)
        this[INDEX_BD_L] = DrumVoice(BASS_DRUM_H_4,64,64,123,0,64,32,32,127,0,0,1,64,64,64,64,64)
        this[INDEX_BD_M] = DrumVoice(BD_ROCK,64,64,127,0,64,32,32,127,0,0,1,64,64,64,64,64)
        this[INDEX_BD_H] = DrumVoice(BD_GATE,64,64,122,0,64,32,32,127,0,0,1,64,64,64,64,64)
        this[INDEX_SNARE_M] = DrumVoice(SD_ROCK_L,64,64,107,0,64,127,127,127,0,0,1,64,64,64,64,64)
        this[INDEX_SNARE_H] = DrumVoice(SD_ROCK_H,64,64,102,0,64,127,127,127,0,0,1,64,64,64,64,64)
        this[INDEX_FL_TOM_L] = DrumVoice(E_TOM_1,64,64,92,0,24,127,127,127,0,0,1,64,64,64,64,64)
        this[INDEX_FL_TOM_H] = DrumVoice(E_TOM_2,64,64,94,0,39,127,127,127,0,0,1,64,64,64,64,64)
        this[INDEX_LOW_TOM] = DrumVoice(E_TOM_3,64,64,97,0,52,127,127,127,0,0,1,64,64,64,64,64)
        this[INDEX_MID_TOM_L] = DrumVoice(E_TOM_4,64,64,93,0,64,127,127,127,0,0,1,64,64,64,64,64)
        this[INDEX_MID_TOM_H] = DrumVoice(E_TOM_5,64,64,102,0,83,127,127,127,0,0,1,64,64,64,64,64)
        this[INDEX_HI_TOM] = DrumVoice(E_TOM_6,64,64,97,0,101,127,127,127,0,0,1,64,64,64,64,64)
        this[INDEX_CUICA_MUTE] = DrumVoice(SCRATCH_PUSH,64,64,89,4,21,127,127,127,0,0,1,64,64,64,64,64)
        this[INDEX_CUICA_OPEN] = DrumVoice(SCRATCH_PULL,64,64,94,4,34,127,127,127,0,0,1,64,64,64,64,64)
    }

    var analogKit: List<DrumVoice> = standardKit.map { it.copy() }.toMutableList().apply {
        this[INDEX_BRUSH_SWIRL_H] = DrumVoice(REVERSE_CYM,64,64,100,0,64,127,127,127,0,1,1,64,64,64,64,64)
        this[INDEX_CASTANET] = DrumVoice(HI_Q,64,64,127,0,64,63,63,127,0,0,1,64,64,64,64,64)
        this[INDEX_SNARE_L] = DrumVoice(SD_ROCK_H,64,64,120,0,64,127,127,127,0,0,1,64,64,64,64,64)
        this[INDEX_BD_L] = DrumVoice(BASS_DRUM_M,64,64,111,0,64,32,32,127,0,0,1,64,64,64,64,64)
        this[INDEX_BD_M] = DrumVoice(BD_ANALOG_L,64,64,123,0,64,32,32,127,0,0,1,64,64,64,64,64)
        this[INDEX_BD_H] = DrumVoice(BD_ANALOG_H,64,64,127,0,64,32,32,127,0,0,1,64,64,64,64,64)
        this[INDEX_STICK] = DrumVoice(ANALOG_STICK,64,64,127,0,64,127,127,127,0,0,1,64,64,64,64,64)
        this[INDEX_SNARE_M] = DrumVoice(ANALOG_SNARE_L,64,64,107,0,64,127,127,127,0,0,1,64,64,64,64,64)
        this[INDEX_SNARE_H] = DrumVoice(ANALOG_SNARE_H,64,64,102,0,64,127,127,127,0,0,1,64,64,64,64,64)
        this[INDEX_FL_TOM_L] = DrumVoice(ANALOG_TOM_1,64,64,127,0,24,127,127,127,0,0,1,64,64,64,64,64)
        this[INDEX_HH_CLOSE] = DrumVoice(ANALOG_HH_CLOSE,64,64,108,1,77,32,32,127,0,0,1,64,64,64,64,64)
        this[INDEX_FL_TOM_H] = DrumVoice(ANALOG_TOM_2,64,64,112,0,39,127,127,127,0,0,1,64,64,64,64,64)
        this[INDEX_HH_PEDL] = DrumVoice(ANALOG_HH_CLOSE_2,64,64,91,1,77,32,32,127,0,0,1,64,64,64,64,64)
        this[INDEX_LOW_TOM] = DrumVoice(ANALOG_TOM_3,64,64,108,0,52,127,127,127,0,0,1,64,64,64,64,64)
        this[INDEX_HH_OPEN] = DrumVoice(ANALOG_HH_OPEN,64,64,96,1,77,32,32,127,0,0,1,64,64,64,64,64)
        this[INDEX_MID_TOM_L] = DrumVoice(ANALOG_TOM_4,64,64,112,0,64,127,127,127,0,0,1,64,64,64,64,64)
        this[INDEX_MID_TOM_H] = DrumVoice(ANALOG_TOM_5,64,64,109,0,83,127,127,127,0,0,1,64,64,64,64,64)
        this[INDEX_CRASH_1] = DrumVoice(ANALOG_CYM,64,64,109,0,69,127,127,127,0,0,1,64,64,64,64,64)
        this[INDEX_HI_TOM] = DrumVoice(ANALOG_TOM_6,64,64,109,0,101,127,127,127,0,0,1,64,64,64,64,64)
        this[INDEX_COWBELL] = DrumVoice(ANALOG_COWBELL,64,64,118,0,77,63,63,127,0,0,1,64,64,64,64,64)
        this[INDEX_CONGA_H_MUTE] = DrumVoice(ANALOG_CONGA_H,64,64,89,0,39,127,127,127,0,0,1,64,64,64,64,64)
        this[INDEX_CONGA_H_OPEN] = DrumVoice(ANALOG_CONGA_M,64,64,89,0,25,127,127,127,0,0,1,64,64,64,64,64)
        this[INDEX_CONGA_L] = DrumVoice(ANALOG_CONGA_L,64,64,115,0,64,95,95,127,0,0,1,64,64,64,64,64)
        this[INDEX_MARACAS] = DrumVoice(ANALOG_MARACAS,64,64,96,0,21,63,63,127,0,0,1,64,64,64,64,64)
        this[INDEX_CLAVES] = DrumVoice(ANALOG_CLAVES,64,64,88,0,64,95,95,127,0,0,1,64,64,64,64,64)
        this[INDEX_CUICA_MUTE] = DrumVoice(SCRATCH_PUSH,64,64,89,4,21,127,127,127,0,0,1,64,64,64,64,64)
        this[INDEX_CUICA_OPEN] = DrumVoice(SCRATCH_PULL,64,64,94,4,34,127,127,127,0,0,1,64,64,64,64,64)
    }

    var jazzKit: List<DrumVoice> = standardKit.map { it.copy() }.toMutableList().apply {
        this[INDEX_BD_H] = DrumVoice(BD_JAZZ,64,64,120,0,64,32,32,127,0,0,1,64,64,64,64,64)
        this[INDEX_FL_TOM_L] = DrumVoice(JAZZ_TOM_1,64,64,113,0,24,127,127,127,0,0,1,64,64,64,64,64)
        this[INDEX_FL_TOM_H] = DrumVoice(JAZZ_TOM_2,64,64,122,0,39,127,127,127,0,0,1,64,64,64,64,64)
        this[INDEX_LOW_TOM] = DrumVoice(JAZZ_TOM_3,64,64,112,0,52,127,127,127,0,0,1,64,64,64,64,64)
        this[INDEX_MID_TOM_L] = DrumVoice(JAZZ_TOM_4,64,64,127,0,64,127,127,127,0,0,1,64,64,64,64,64)
        this[INDEX_MID_TOM_H] = DrumVoice(JAZZ_TOM_5,64,64,110,0,83,127,127,127,0,0,1,64,64,64,64,64)
        this[INDEX_HI_TOM] = DrumVoice(JAZZ_TOM_6,64,64,116,0,104,127,127,127,0,0,1,64,64,64,64,64)
    }

    var brushKit: List<DrumVoice> = standardKit.map { it.copy() }.toMutableList().apply {
        this[INDEX_SNARE_L] = DrumVoice(BRUSH_SLAP_L,64,64,85,0,64,127,127,127,0,0,1,64,64,64,64,64)
        this[INDEX_BD_H] = DrumVoice(BD_SOFT,64,64,117,0,64,32,32,127,0,0,1,64,64,64,64,64)
        this[INDEX_SNARE_M] = DrumVoice(BRUSH_SLAP_M,64,64,84,0,64,127,127,127,0,0,1,64,64,64,64,64)
        this[INDEX_SNARE_H] = DrumVoice(BRUSH_TAP_H,64,64,74,0,64,127,127,127,0,0,1,64,64,64,64,64)
        this[INDEX_FL_TOM_L] = DrumVoice(BRUSH_TOM_1,64,64,127,0,24,127,127,127,0,0,1,64,64,64,64,64)
        this[INDEX_FL_TOM_H] = DrumVoice(BRUSH_TOM_2,64,64,127,0,39,127,127,127,0,0,1,64,64,64,64,64)
        this[INDEX_LOW_TOM] = DrumVoice(BRUSH_TOM_3,64,64,127,0,52,127,127,127,0,0,1,64,64,64,64,64)
        this[INDEX_MID_TOM_L] = DrumVoice(BRUSH_TOM_4,64,64,127,0,64,127,127,127,0,0,1,64,64,64,64,64)
        this[INDEX_MID_TOM_H] = DrumVoice(BRUSH_TOM_5,64,64,120,0,83,127,127,127,0,0,1,64,64,64,64,64)
        this[INDEX_HI_TOM] = DrumVoice(BRUSH_TOM_6,64,64,122,0,104,127,127,127,0,0,1,64,64,64,64,64)
    }

    var classicKit: List<DrumVoice> = standardKit.map { it.copy() }.toMutableList().apply {
        this[INDEX_BD_L] = DrumVoice(BASS_DRUM_L_2,64,64,116,0,64,32,32,127,0,0,1,64,64,64,64,64)
       this[INDEX_BD_M] = DrumVoice(GRAN_CASSA,64,64,127,0,64,32,32,127,0,0,1,64,64,64,64,64)
        this[INDEX_BD_H] = DrumVoice(GRAN_CASSA_MUTE,64,64,127,0,64,32,32,127,0,0,1,64,64,64,64,64)
        this[INDEX_SNARE_M] = DrumVoice(MARCH_SNARE_M,64,64,79,0,64,127,127,127,0,0,1,64,64,64,64,64)
        this[INDEX_SNARE_H] = DrumVoice(MARCH_SNARE_H,64,64,79,0,64,127,127,127,0,0,1,64,64,64,64,64)
        this[INDEX_FL_TOM_L] = DrumVoice(JAZZ_TOM_1,64,64,111,0,24,127,127,127,0,0,1,64,64,64,64,64)
        this[INDEX_FL_TOM_H] = DrumVoice(JAZZ_TOM_2,64,64,113,0,39,127,127,127,0,0,1,64,64,64,64,64)
        this[INDEX_LOW_TOM] = DrumVoice(JAZZ_TOM_3,64,64,104,0,52,127,127,127,0,0,1,64,64,64,64,64)
        this[INDEX_MID_TOM_L] = DrumVoice(JAZZ_TOM_4,64,64,87,0,64,127,127,127,0,0,1,64,64,64,64,64)
        this[INDEX_MID_TOM_H] = DrumVoice(JAZZ_TOM_5,64,64,103,0,83,127,127,127,0,0,1,64,64,64,64,64)
       this[INDEX_CRASH_1] = DrumVoice(HAND_CYM_OPEN_L,64,64,123,0,64,127,127,127,0,0,1,64,64,64,64,64)
        this[INDEX_HI_TOM] = DrumVoice(JAZZ_TOM_6,64,64,116,0,104,127,127,127,0,0,1,64,64,64,64,64)
        this[INDEX_RIDE_CYM_1] = DrumVoice(HAND_CYM_CLOSED_L,64,64,124,0,34,127,127,127,0,0,1,64,64,64,64,64)
        this[INDEX_CRASH_2] = DrumVoice(HAND_CYM_OPEN_H,64,64,127,0,51,127,127,127,0,0,1,64,64,64,64,64)
        this[INDEX_RIDE_2] = DrumVoice(HAND_CYM_CLOSED_H,64,64,106,0,46,127,127,127,0,0,1,64,64,64,64,64)
    }
}