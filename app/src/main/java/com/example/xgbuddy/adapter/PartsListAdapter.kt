package com.example.xgbuddy.adapter

import android.content.Context
import android.view.ViewGroup
import com.example.xgbuddy.data.MidiChannel
import com.example.xgbuddy.ui.MidiViewModel
import com.example.xgbuddy.ui.custom.PartsRowItem

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
                setChannelInfo(midiChannel)
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
    }

    companion object {
        private const val NOT_SET = -1
    }
}