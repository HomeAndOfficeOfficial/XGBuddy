package com.example.xgbuddy.ui.voiceselect

import com.example.xgbuddy.data.voiceselect.VoiceListCategory

sealed interface OnVoiceItemSelectedListener {
    fun onVoiceItemSelected(index: Int, category: VoiceListCategory)
}