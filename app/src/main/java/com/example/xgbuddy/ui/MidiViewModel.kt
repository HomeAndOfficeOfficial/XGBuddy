package com.example.xgbuddy.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.xgbuddy.data.MidiSetup
import com.example.xgbuddy.data.SetupModel
import com.example.xgbuddy.data.gm.MidiPart
import com.example.xgbuddy.data.qs300.QS300Preset
import com.example.xgbuddy.data.xg.*
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import org.json.JSONStringer

class MidiViewModel : ViewModel() {
    val channels = MutableLiveData(MutableList(16) { MidiPart(it) })
    val selectedChannel = MutableLiveData(0)
    val selectedDrumVoice = MutableLiveData(0)
    val setupResetFlag = MutableLiveData(false)
    var reverb = Reverb(ReverbType.HALL1)
    var chorus = Chorus(ChorusType.CHORUS1)
    var variation = Variation(VariationType.DELAY_LCR)
    var tuning = SystemParameter.MASTER_TUNE.default
    var volume = SystemParameter.MASTER_VOLUME.default
    var transpose = SystemParameter.TRANSPOSE.default
    var qsPartMap = mutableMapOf<Int, QS300Preset>()

    fun resetToDefaultSetup() {
        val defaultSetup = MidiSetup.getXGDefault()
        channels.value = defaultSetup.parts.toMutableList()
        reverb = Reverb(ReverbType.HALL1)
        chorus = Chorus(ChorusType.CHORUS1)
        variation = Variation(VariationType.DELAY_LCR)
        tuning = SystemParameter.MASTER_TUNE.default
        volume = SystemParameter.MASTER_VOLUME.default
        transpose = SystemParameter.TRANSPOSE.default
        qsPartMap.clear()
    }

    fun toSetupModel(): SetupModel = SetupModel(
        channels.value!!,
        reverb,
        chorus,
        variation,
        qsPartMap
    )

    fun readSetupJson(jsonString: String): SetupModel? =
        try {
            Gson().fromJson(jsonString, SetupModel::class.java).let {
                setupResetFlag.value = true
                channels.value = it.parts.toMutableList()
                reverb = it.reverb
                chorus = it.chorus
                variation = it.variation
                qsPartMap = it.qsPresetMap.toMutableMap()
                it
            }
        } catch (e: JsonSyntaxException) {
            Log.e("MidiViewModel", "Couldn't read setup from file: ${e.message}")
            null
        }
}