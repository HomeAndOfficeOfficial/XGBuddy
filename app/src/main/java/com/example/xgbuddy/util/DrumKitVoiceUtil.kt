package com.example.xgbuddy.util

import androidx.core.app.NotificationCompat.GroupAlertBehavior
import com.example.xgbuddy.data.xg.DrumVoice

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
        DrumVoice(SURDO_MUTE),
        DrumVoice(SURDO_OPEN),
        DrumVoice(HI_Q),
        DrumVoice(WHIP_SLAP),
        DrumVoice(SCRATCH_PUSH),
        DrumVoice(SCRATCH_PULL),
        DrumVoice(FINGER_SNAP),
        DrumVoice(CLICK_NOISE),
        DrumVoice(METR_CLICK),
        DrumVoice(METR_BELL),
        DrumVoice(SEQ_CLICK_L),
        DrumVoice(SEQ_CLICK_H),
        DrumVoice(BRUSH_TAP),
        DrumVoice(BRUSH_SWIRL_L),
        DrumVoice(BRUSH_SLAP),
        DrumVoice(BRUSH_SWIRL_H),
        DrumVoice(SNARE_ROLL),
        DrumVoice(CASTANET),
        DrumVoice(SNARE_L),
        DrumVoice(STICKS),
        DrumVoice(BASS_DRUM_L),
        DrumVoice(OPEN_RIM),
        DrumVoice(BASS_DRUM_M),
        DrumVoice(BASS_DRUM_H),
        DrumVoice(SIDE_STICK),
        DrumVoice(SNARE_M),
        DrumVoice(HAND_CLAP),
        DrumVoice(SNARE_H),
        DrumVoice(FLOOR_TOM_L),
        DrumVoice(HH_CLOSE),
        DrumVoice(FLOOR_TOM_H),
        DrumVoice(HH_PEDAL),
        DrumVoice(LOW_TOM),
        DrumVoice(HH_OPEN),
        DrumVoice(MID_TOM_L),
        DrumVoice(MID_TOM_H),
        DrumVoice(CRASH_CYM_1),
        DrumVoice(HIGH_TOM),
        DrumVoice(RIDE_CYM_1),
        DrumVoice(CHINESE_CYM),
        DrumVoice(RIDE_CYM_CUP),
        DrumVoice(TAMBOURINE),
        DrumVoice(SPLASH_CYM),
        DrumVoice(COWBELL),
        DrumVoice(CRASH_CYM_2),
        DrumVoice(VIBRASLAP),
        DrumVoice(RIDE_CYM_2),
        DrumVoice(BONGO_H),
        DrumVoice(BONGO_L),
        DrumVoice(CONGA_H_MUTE),
        DrumVoice(CONGA_H_OPEN),
        DrumVoice(CONGA_L),
        DrumVoice(TIMBALE_H),
        DrumVoice(TIMBALE_L),
        DrumVoice(AGOGO_H),
        DrumVoice(AGOGO_L),
        DrumVoice(CABASA),
        DrumVoice(MARACAS),
        DrumVoice(SAMBA_WHISTLE_H),
        DrumVoice(SAMBA_WHISTLE_L),
        DrumVoice(GUIRO_SHORT),
        DrumVoice(GUIRO_LONG),
        DrumVoice(CLAVES),
        DrumVoice(WOOD_BLOCK_H),
        DrumVoice(WOOD_BLOCK_L),
        DrumVoice(CUICA_MUTE),
        DrumVoice(CUICA_OPEN),
        DrumVoice(TRIANGLE_MUTE),
        DrumVoice(TRIANGLE_OPEN),
        DrumVoice(SHAKER),
        DrumVoice(JINGLE_BELL),
        DrumVoice(BELL_TREE)
    )

    val standardKit2: List<DrumVoice> = standardKit.map { it.copy() }.toMutableList().apply {
        this[INDEX_SNARE_ROLL].name = SNARE_ROLL_2
        this[INDEX_SNARE_L].name = SNARE_L_2
        this[INDEX_OPEN_RIM].name = OPEN_RIM_2
        this[INDEX_BD_M].name = BASS_DRUM_M_2
        this[INDEX_BD_H].name = BASS_DRUM_H_2
        this[INDEX_SNARE_M].name = SNARE_M_2
        this[INDEX_SNARE_H].name = SNARE_H_2
    }

    var roomKit: List<DrumVoice> = standardKit.map { it.copy() }.toMutableList().apply {
        this[INDEX_SNARE_M].name = SD_ROOM_L
        this[INDEX_SNARE_H].name = SD_ROOM_H
        this[INDEX_FL_TOM_L].name = ROOM_TOM_1
        this[INDEX_FL_TOM_H].name = ROOM_TOM_2
        this[INDEX_LOW_TOM].name = ROOM_TOM_3
        this[INDEX_MID_TOM_L].name = ROOM_TOM_4
        this[INDEX_MID_TOM_H].name = ROOM_TOM_5
        this[INDEX_HI_TOM].name = ROOM_TOM_6
    }

    var rockKit: List<DrumVoice> = standardKit.map { it.copy() }.toMutableList().apply {
        this[INDEX_SNARE_L].name = SD_ROCK_M
        this[INDEX_BD_L].name = BASS_DRUM_M
        this[INDEX_BD_M].name = BASS_DRUM_H_3
        this[INDEX_BD_H].name = BD_ROCK
        this[INDEX_SNARE_M].name = SD_ROCK
        this[INDEX_SNARE_H].name = SD_ROCK_RIM
        this[INDEX_FL_TOM_L].name = ROCK_TOM_1
        this[INDEX_FL_TOM_H].name = ROCK_TOM_2
        this[INDEX_LOW_TOM].name = ROCK_TOM_3
        this[INDEX_MID_TOM_L].name = ROCK_TOM_4
        this[INDEX_MID_TOM_H].name = ROCK_TOM_5
        this[INDEX_HI_TOM].name = ROCK_TOM_6
    }

    var electroKit: List<DrumVoice> = standardKit.map { it.copy() }.toMutableList().apply {
        this[INDEX_BRUSH_SWIRL_H].name = REVERSE_CYM
        this[INDEX_CASTANET].name = HI_Q
        this[INDEX_SNARE_L].name = SNARE_M
        this[INDEX_BD_L].name = BASS_DRUM_H_4
        this[INDEX_BD_M].name = BD_ROCK
        this[INDEX_BD_H].name = BD_GATE
        this[INDEX_SNARE_M].name = SD_ROCK_L
        this[INDEX_SNARE_H].name = SD_ROCK_H
        this[INDEX_FL_TOM_L].name = E_TOM_1
        this[INDEX_FL_TOM_H].name = E_TOM_2
        this[INDEX_LOW_TOM].name = E_TOM_3
        this[INDEX_MID_TOM_L].name = E_TOM_4
        this[INDEX_MID_TOM_H].name = E_TOM_5
        this[INDEX_HI_TOM].name = E_TOM_6
        this[INDEX_CUICA_MUTE].name = SCRATCH_PUSH
        this[INDEX_CUICA_OPEN].name = SCRATCH_PULL
    }

    var analogKit: List<DrumVoice> = standardKit.map { it.copy() }.toMutableList().apply {
        this[INDEX_BRUSH_SWIRL_H].name = REVERSE_CYM
        this[INDEX_CASTANET].name = HI_Q
        this[INDEX_SNARE_L].name = SD_ROCK_H
        this[INDEX_BD_L].name = BASS_DRUM_M
        this[INDEX_BD_M].name = BD_ANALOG_L
        this[INDEX_BD_H].name = BD_ANALOG_H
        this[INDEX_SNARE_M].name = ANALOG_STICK
        this[INDEX_SNARE_H].name = ANALOG_SNARE_H
        this[INDEX_FL_TOM_L].name = ANALOG_TOM_1
        this[INDEX_HH_CLOSE].name = ANALOG_HH_CLOSE
        this[INDEX_FL_TOM_H].name = ANALOG_TOM_2
        this[INDEX_HH_PEDL].name = ANALOG_HH_CLOSE_2
        this[INDEX_LOW_TOM].name = ANALOG_TOM_3
        this[INDEX_HH_OPEN].name = ANALOG_HH_OPEN
        this[INDEX_MID_TOM_L].name = ANALOG_TOM_4
        this[INDEX_MID_TOM_H].name = ANALOG_TOM_5
        this[INDEX_CRASH_1].name = ANALOG_CYM
        this[INDEX_HI_TOM].name = ANALOG_TOM_6
        this[INDEX_COWBELL].name = ANALOG_COWBELL
        this[INDEX_CONGA_H_MUTE].name = ANALOG_CONGA_H
        this[INDEX_CONGA_H_OPEN].name = ANALOG_CONGA_M
        this[INDEX_CONGA_L].name = ANALOG_CONGA_L
        this[INDEX_MARACAS].name = ANALOG_MARACAS
        this[INDEX_CLAVES].name = ANALOG_CLAVES
        this[INDEX_CUICA_MUTE].name = SCRATCH_PUSH
        this[INDEX_CUICA_OPEN].name = SCRATCH_PULL
    }

    var jazzKit: List<DrumVoice> = standardKit.map { it.copy() }.toMutableList().apply {
        this[INDEX_BD_H].name = BD_JAZZ
        this[INDEX_FL_TOM_L].name = JAZZ_TOM_1
        this[INDEX_FL_TOM_H].name = JAZZ_TOM_2
        this[INDEX_LOW_TOM].name = JAZZ_TOM_3
        this[INDEX_MID_TOM_L].name = JAZZ_TOM_4
        this[INDEX_MID_TOM_H].name = JAZZ_TOM_5
        this[INDEX_HI_TOM].name = JAZZ_TOM_6
    }

    var brushKit: List<DrumVoice> = standardKit.map { it.copy() }.toMutableList().apply {
        this[INDEX_SNARE_L].name = BRUSH_SLAP_L
        this[INDEX_BD_H].name = BD_SOFT
        this[INDEX_SNARE_M].name = BRUSH_SLAP_M
        this[INDEX_SNARE_H].name = BRUSH_TAP_H
        this[INDEX_FL_TOM_L].name = BRUSH_TOM_1
        this[INDEX_FL_TOM_H].name = BRUSH_TOM_2
        this[INDEX_LOW_TOM].name = BRUSH_TOM_3
        this[INDEX_MID_TOM_L].name = BRUSH_TOM_4
        this[INDEX_MID_TOM_H].name = BRUSH_TOM_5
        this[INDEX_HI_TOM].name = BRUSH_TOM_6
    }

    var classicKit: List<DrumVoice> = standardKit.map { it.copy() }.toMutableList().apply {
        this[INDEX_BD_L].name = BASS_DRUM_L_2
        this[INDEX_BD_M].name = GRAN_CASSA
        this[INDEX_BD_H].name = GRAN_CASSA_MUTE
        this[INDEX_SNARE_M].name = MARCH_SNARE_M
        this[INDEX_SNARE_H].name = MARCH_SNARE_H
        this[INDEX_FL_TOM_L].name = JAZZ_TOM_1
        this[INDEX_FL_TOM_H].name = JAZZ_TOM_2
        this[INDEX_LOW_TOM].name = JAZZ_TOM_3
        this[INDEX_MID_TOM_L].name = JAZZ_TOM_4
        this[INDEX_MID_TOM_H].name = JAZZ_TOM_5
        this[INDEX_CRASH_1].name = HAND_CYM_OPEN_L
        this[INDEX_HI_TOM].name = JAZZ_TOM_6
        this[INDEX_RIDE_CYM_1].name = HAND_CYM_CLOSED_L
        this[INDEX_CRASH_2].name = HAND_CYM_OPEN_H
        this[INDEX_RIDE_2].name = HAND_CYM_CLOSED_H
    }
}