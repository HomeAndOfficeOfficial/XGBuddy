package com.yamahaw.xgbuddy.adapter

import android.content.Context
import android.view.ViewGroup
import com.yamahaw.xgbuddy.R
import com.yamahaw.xgbuddy.data.gm.MidiPart
import com.yamahaw.xgbuddy.ui.MidiViewModel
import com.yamahaw.xgbuddy.ui.custom.PartsRowItem

class PartsListAdapter(
    context: Context,
    private val viewModel: MidiViewModel,
    private val partsContainer: ViewGroup
) : PartsRowItem.OnPartsRowTouchListener {

    private var selectedRow = NOT_SET

    init {
        populateContainer(context)
    }

    private fun populateContainer(context: Context) {
        viewModel.channels.value?.forEach { midiChannel ->
            partsContainer.addView(PartsRowItem(context).apply {
                val name = midiChannel.voiceName.ifEmpty {
                    context.getString(midiChannel.voiceNameRes)
                }
                setChannelInfo(midiChannel, name)
                listener = this@PartsListAdapter
            })
        }
    }

    override fun onPartsRowTouched(partNumber: Int) {
        viewModel.selectedChannel.value = partNumber
    }

    fun selectRow(rowNumber: Int) {
        if (rowNumber < 0 || rowNumber >= partsContainer.childCount) {
            return
        }
        if (selectedRow > NOT_SET && rowNumber != selectedRow) {
            (partsContainer.getChildAt(selectedRow) as PartsRowItem).isRowSelected = false
        }
        (partsContainer.getChildAt(rowNumber) as PartsRowItem).isRowSelected = true
        selectedRow = rowNumber
    }

    fun updateRow(midiPart: MidiPart) {
        (partsContainer.getChildAt(midiPart.ch) as PartsRowItem).apply {
            setChannelInfo(
                midiPart,
                midiPart.voiceName.ifEmpty { context.getString(midiPart.voiceNameRes) })
        }
    }

    fun updateAll(parts: List<MidiPart>) {
        parts.forEachIndexed { index, part ->
            (partsContainer.getChildAt(index) as PartsRowItem).apply {
                val voiceName = part.voiceName.ifEmpty { context.getString(part.voiceNameRes) }
                setChannelInfo(part, voiceName)
            }
        }
    }

    companion object {
        private const val NOT_SET = -1
    }
}