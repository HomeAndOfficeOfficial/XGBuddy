package com.example.xgbuddy.data

import com.example.xgbuddy.data.gm.MidiPart
import com.example.xgbuddy.data.qs300.QS300Preset
import com.example.xgbuddy.data.xg.Chorus
import com.example.xgbuddy.data.xg.Reverb
import com.example.xgbuddy.data.xg.Variation

data class SetupModel(
    val parts: List<MidiPart>,
    val reverb: Reverb,
    val chorus: Chorus,
    val variation: Variation,
    val qsPresetMap: Map<Int, QS300Preset>
    // Todo: add tuning, volume, transpose
)