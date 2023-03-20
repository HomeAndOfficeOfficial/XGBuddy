package com.example.xgbuddy.data

import com.example.xgbuddy.util.EnumFinder.findBy
import kotlin.experimental.inv


/**
 * Thoughts about layout of Voice Edit Screen:
 *
 * Each voice only contains 2 elements
 * I think the fragment can be split into three sections
 * On the left most section, which would take up about 20% of the screen width, an editText for
 * voice name, a control for voice level, then a set of main controls for each element (volume, pan,
 * filter, res, wave, on/off switch which would map to elementSwitch)
 *
 * Then there would be a panel for each element (color coded to match the main control box?)
 * The control groups would collapse into rows that just say the category name and have a little
 * up/down arrow to indicate they can be collapsed/expanded.
 *
 * Each of these (ElementEditFragment) panels would be scrollable up and down, so I should probably
 * leave some padding on either side to allow for scrolling.
 *
 * This layout can be achieved using LinearLayout and weights for each of the sections.
 */

class QS300Voice {

    val elements: MutableList<QS300Element> = mutableListOf()
    var voiceLevel: Byte = 100
    var elementSwitch: Byte = EL_12
    var voiceName: String = ""


    // TODO: Create constants for indices and setup values
    // TODO: Create method that creates header byte array
    fun generateBulkDump(): MidiMessage {
        val midiBytes = ByteArray(MidiConstants.QS300_BULK_DUMP_TOTAL_SIZE) { 0 }
        midiBytes[0] = MidiConstants.EXCLUSIVE_STATUS_BYTE
        midiBytes[1] = MidiConstants.YAMAHA_ID_BYTE
        midiBytes[2] = MidiConstants.DEVICE_NUMBER_BULK_DUMP
        midiBytes[3] = MidiConstants.MODEL_ID_QS300
        midiBytes[4] = 1   // Byte count hi
        midiBytes[5] = 125 // Byte count lo
        midiBytes[6] = 17  // Addr hi
        midiBytes[7] =
            0   // Addr mid TODO: This value is different depending on normal voice selection
        midiBytes[8] = 0   // Addr lo

        // Voice name
        for ((nameIndex, i) in (MidiConstants.OFFSET_QS300_BULK_DATA_START until MidiConstants.OFFSET_QS300_BULK_DATA_START + MidiConstants.QS300_VOICE_NAME_SIZE).withIndex()) {
            midiBytes[i] = if (nameIndex < voiceName.length) {
                voiceName[nameIndex].code.toByte()
            } else {
                32
            } // Space
        }

        midiBytes[MidiConstants.OFFSET_QS300_BULK_EL_SWITCH] = elementSwitch
        midiBytes[MidiConstants.OFFSET_QS300_BULK_VOICE_LEVEL] = voiceLevel

        // Element data
        for (i in 0 until MidiConstants.QS300_MAX_ELEMENTS) {
            if (i < elements.size) {
                val startIndex =
                    MidiConstants.OFFSET_QS300_BULK_ELEMENT_DATA_START + (i * MidiConstants.QS300_ELEMENT_DATA_SIZE)
                for (j in startIndex until startIndex + MidiConstants.QS300_ELEMENT_DATA_SIZE) {
                    val baseAddress =
                        (j - MidiConstants.OFFSET_QS300_BULK_DATA_START - (i * MidiConstants.QS300_ELEMENT_DATA_SIZE)).toUByte()
                    val elementParam = QS300ElementParameter::baseAddress findBy baseAddress
                    val property = elementParam?.reflectedField
                    midiBytes[j] = elements[i].getPropertyValue(property)
                }
            }
        }

        // Checksum
        var dataSum: Byte = 0
        for (i in 4 until MidiConstants.QS300_BULK_DUMP_TOTAL_SIZE - 2) {
            dataSum = dataSum.plus(midiBytes[i]).toByte()
        }
        midiBytes[MidiConstants.QS300_BULK_DUMP_TOTAL_SIZE - 2] = (0 - dataSum).toByte()
        midiBytes[MidiConstants.QS300_BULK_DUMP_TOTAL_SIZE - 1] = MidiConstants.END_BYTE
        return MidiMessage(midiBytes, System.nanoTime() + 10)
    }

    companion object {
        const val EL_1: Byte = 0
        const val EL_2: Byte = 1
        const val EL_12: Byte = 2
    }
}