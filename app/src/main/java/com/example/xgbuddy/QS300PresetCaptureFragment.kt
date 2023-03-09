package com.example.xgbuddy

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.xgbuddy.data.MidiMessage
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
import java.io.FileNotFoundException
import javax.inject.Inject

private const val PRESETS_FILE_NAME = "qs300_presets.json"

@AndroidEntryPoint
class QS300PresetCaptureFragment : DialogFragment(), MidiSession.OnMidiReceivedListener {

    @Inject
    lateinit var midiSession: MidiSession

    private val presetMessages: MutableList<MidiMessage> = mutableListOf()
    private var presetName = ""
    private var qs300PresetsJSON: JSONObject? = null

    private var isReadyToReceive = false
    private var isReadyForWrite = false


    /**
     * Here's how this should work.
     * 1. Check if midiSession has an outputPort (outputPort receives data I think? As in it's used
     *  for receiving output)
     * 2. If so, display a ready status.
     * 3. If outputPort is not already opened (and connected to a MidiReceiver?), open it.
     * 4. Now, I think I need to register some kind of callback in this fragment that waits for the
     *  start of a QS300 preset message (verify what that is using XG Manager). Add that to a list
     *  of midi messages as text probably (MutableList<String>). I guess each subsequent message
     *  should be validated before being added to the list? Maybe save that for a later step.
     * 5. Listen for an end message, or maybe not necessary? Click "save preset" after the preset
     *  has been transmitted.
     * 6. Open file QS300Presets.json and add an entry to it.
     *      "preset_name": [ "midi hex command", "next command", ... ],
     *    Probably should not respond to midi transmission at this time.
     * 7. Once file is written, clear presetMessages, disable buttons
     */

    /**
     * About writing to json
     * Instead of writing to a file every time save preset is clicked, just write to a JSONObject.
     *
     * When this fragment is started, open qs300_presets.json and create a JSONObject from it.
     *
     * Each time save preset is clicked, first check the JSONObject to see if it already contains
     * the preset name, and if it doesn't add it to the JSONObject.
     *
     * When this fragment is dismissed, first write the JSONObject to qs300_presets.json, then let
     * the fragment dismiss. While it writes, it may need to display a "Saving presets" message.
     */

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        midiSession.outputDeviceOpened.observe(viewLifecycleOwner) { isReadyToReceive = it }
        qs300PresetsJSON = if (savedInstanceState == null) {
            JSONObject(readPresetsJSON() ?: "{}")
        } else {
            JSONObject(savedInstanceState.getString(ARG_PRESET_JSON) ?: "")
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        if (isReadyToReceive) { // <- This is set by observing midiSession.outputDeviceOpened
            midiSession.registerForMidiCallbacks(this)
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        // Write jsonObject to file if it has content
        // Display saving message
        // Call super.ondismiss when it's finished and close file
        writePresetsToFile()
        super.onDismiss(dialog)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(ARG_PRESET_JSON, qs300PresetsJSON.toString())
    }

    override fun onPause() {
        super.onPause()
        midiSession.unregisterMidiListener(this)
    }

    override fun onMidiMessageReceived(message: MidiMessage) {
        if (presetMessages.isEmpty()) {
            if (isValidPresetHeader(message)) {
                presetMessages.add(message)
                //TODO:
                // Change status message to "receiving data" or something
                // bClearData.enabled = true
            }
        } else {
            if (!isReadyForWrite) {
                presetMessages.add(message)
                if (isValidEndMessage(message)) {
                    isReadyForWrite = true
                    //TODO:
                    // bSavePreset.enabled = true
                    // Change status message to "Ready to save preset"
                }
            }
        }
    }

    private fun readPresetsJSON(): String? {
        try {
            requireContext().openFileInput(PRESETS_FILE_NAME).apply {
                val buffer = ByteArray(available())
                read(buffer)
                close()
                return String(buffer)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception reading from qs300_presets.json: ${e.message}")
            return null
        }
    }

    private fun writePresetsToFile() {
        requireContext().openFileOutput(PRESETS_FILE_NAME, Context.MODE_PRIVATE).apply {
            write(qs300PresetsJSON.toString().toByteArray())
            close()
        }
    }

    private fun onClearDataClicked(v: View) {
        presetMessages.clear()
        isReadyForWrite = false
        // Change status message to Waiting for data
    }

    private fun onSavePresetClicked(v: View) {
        requireContext().openFileOutput(PRESETS_FILE_NAME, Context.MODE_APPEND).apply {

        }

    }

    private fun isValidPresetHeader(message: MidiMessage): Boolean {
        return true
        /**
         * Hard to say how to best implement this until I know more about how these messages are
         * structured. This type of message will probably be defined somewhere as a constant so it
         * will probably be as simple as just checking the content of the message against that.
         */
    }

    private fun isValidEndMessage(message: MidiMessage): Boolean {
        return true
        /**
         * Same thing as above
         */
    }

    companion object {
        private const val TAG = "QS300PresetCaptureFragment"
        private const val ARG_PRESET_JSON = "arg_preset_json"
    }
}