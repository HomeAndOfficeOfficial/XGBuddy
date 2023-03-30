package com.example.xgbuddy.adapter

import android.content.Context
import android.view.ViewGroup
import com.example.xgbuddy.data.MidiChannel
import com.example.xgbuddy.ui.custom.PartsRowItem

/**
 * Probably should send a viewmodel to this as well so it can update it when a row is clicked.
 */
class PartsListAdapter(
    context: Context,
    private val partsContainer: ViewGroup,
    private var channels: List<MidiChannel>
) : PartsRowItem.OnPartsRowTouchListener {

    private var selectedRow = NOT_SET

    init {
        populateContainer(context)
    }

    private fun populateContainer(context: Context) {
        channels.forEach { midiChannel ->
            partsContainer.addView(PartsRowItem(context).apply {
                setChannelInfo(midiChannel)
                listener = this@PartsListAdapter
            })
        }
    }

    override fun onPartsRowTouched(partNumber: Int) {
        // Update viewmodel
        // Observing fragment then calls selectRow
    }

    fun selectRow(rowNumber: Int) {
        if (selectedRow > NOT_SET && rowNumber != selectedRow) {
            (partsContainer.getChildAt(selectedRow) as PartsRowItem).isRowSelected = false
        }
        (partsContainer.getChildAt(rowNumber) as PartsRowItem).isRowSelected = true
    }

    companion object {
        private const val NOT_SET = -1
    }
}