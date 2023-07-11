package com.yamahaw.xgbuddy.ui.voiceselect

import com.yamahaw.xgbuddy.data.voiceselect.VoiceListCategory

sealed interface OnVoiceItemSelectedListener {
    fun onVoiceItemSelected(index: Int, category: VoiceListCategory)
}