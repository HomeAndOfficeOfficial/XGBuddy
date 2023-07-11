package com.yamahaw.xgbuddy.data

import com.yamahaw.xgbuddy.data.gm.MidiPart
import com.yamahaw.xgbuddy.data.qs300.QS300Preset
import com.yamahaw.xgbuddy.data.xg.Chorus
import com.yamahaw.xgbuddy.data.xg.Reverb
import com.yamahaw.xgbuddy.data.xg.Variation

data class SetupModel(
    val parts: List<MidiPart>,
    val reverb: Reverb,
    val chorus: Chorus,
    val variation: Variation,
    val qsPresetMap: Map<Int, QS300Preset>
    // Todo: add tuning, volume, transpose
)