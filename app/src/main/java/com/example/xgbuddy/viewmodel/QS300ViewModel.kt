package com.example.xgbuddy.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.xgbuddy.data.qs300.QS300Preset
import com.example.xgbuddy.util.QS300Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.experimental.and
import kotlin.experimental.inv
import kotlin.experimental.xor

@HiltViewModel
class QS300ViewModel @Inject constructor(
    repository: QS300Repository
) : ViewModel() {

    val presets: List<QS300Preset> = repository.getQS300Presets()

    val preset = MutableLiveData<QS300Preset?>(null)
    var voice = 0
    var elementStatus =
        (MutableLiveData(((preset.value?.voices?.get(voice)?.elementSwitch ?: 2) + 1).toByte()))
    // ^ Long way of saying set it to the element switch value or EL_12 if null

    fun updateElementStatus(elementIndex: Int, isOn: Boolean) {
        if (elementIndex < 2) { // Don't worry about 3 or 4 for now
            var updatedStatus = elementStatus.value!! xor ((1 shl elementIndex).toByte())
            if (updatedStatus == 0.toByte()) {
                elementStatus.value = elementStatus.value!!.inv() and 0x03
                updatedStatus = elementStatus.value!!
            } else {
                elementStatus.value = updatedStatus
            }
            preset.value!!.voices[voice].elementSwitch = updatedStatus
        }

    }
}