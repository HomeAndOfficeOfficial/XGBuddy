package com.example.xgbuddy.data.qs300

import com.example.xgbuddy.data.MidiConstants
import com.example.xgbuddy.data.MidiMessage
import com.example.xgbuddy.util.EnumFinder.findBy

class QS300Voice {

    val elements: MutableList<QS300Element> = mutableListOf()
    var voiceLevel: Byte = 100
    var elementSwitch: Byte = EL_12
    var voiceName: String = ""

    /** To troubleshoot the issue with this bulk dump seemingly not being recognized by my MU10,
     * I think I will need to do a full implementation of the preset data.
     *
     * Add a QS300VoiceCommonParameter enum that has all the fields from voice name to the start of
     * the element parameters.
     *
     * Don't assume only two elements, just go ahead and populate all four. This should result in
     * an identical bulk dump message to what is read in, then I can see if my checksum calculation
     * is the issue or if something else is wrong.
     *
     */


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