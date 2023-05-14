package com.example.xgbuddy.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.xgbuddy.data.qs300.QS300Preset
import com.example.xgbuddy.util.QS300Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.reflect.full.memberProperties

@HiltViewModel
class QS300ViewModel @Inject constructor(
    repository: QS300Repository
) : ViewModel() {

    val presets: List<QS300Preset> = repository.getQS300Presets()

    val preset = MutableLiveData(presets[4])
    var currentParamIndex = 0
    var voice: Int = 0
    var element: Int = 0

    fun nextParameter() {
        val params = preset.value!!.voices[0].elements[0]::class.memberProperties
        if (currentParamIndex + 1 < params.size) {

        }
    }

}