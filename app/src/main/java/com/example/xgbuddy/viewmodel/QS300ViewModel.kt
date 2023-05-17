package com.example.xgbuddy.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.xgbuddy.data.qs300.QS300Preset
import com.example.xgbuddy.util.QS300Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QS300ViewModel @Inject constructor(
    repository: QS300Repository
) : ViewModel() {

    val presets: List<QS300Preset> = repository.getQS300Presets()

    val preset = MutableLiveData<QS300Preset?>(null)
    var voice = 0
}