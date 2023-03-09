package com.example.xgbuddy

import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.xgbuddy.data.MidiMessage
import com.example.xgbuddy.databinding.FragmentQs300PresetCaptureBinding
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
import javax.inject.Inject

private const val PRESETS_FILE_NAME = "qs300_presets.json"

@AndroidEntryPoint
class QS300PresetCaptureFragment : DialogFragment(), MidiSession.OnMidiReceivedListener {

    @Inject
    lateinit var midiSession: MidiSession

    private lateinit var binding: FragmentQs300PresetCaptureBinding

    private val presetMessages: MutableList<MidiMessage> = mutableListOf()
    private var presetName = ""
    private var qs300PresetsJSON: JSONObject? = null

    private var status: QS300PresetCaptureStatus = QS300PresetCaptureStatus.READY


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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        qs300PresetsJSON = if (savedInstanceState == null) {
            JSONObject(readPresetsJSON() ?: "{}")
        } else {
            JSONObject(savedInstanceState.getString(ARG_PRESET_JSON) ?: "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQs300PresetCaptureBinding.inflate(layoutInflater)
        midiSession.outputDeviceOpened.observe(viewLifecycleOwner) {
            val status =
                if (it) QS300PresetCaptureStatus.READY else QS300PresetCaptureStatus.NO_OUTPUT
            setViewStatus(status)
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        midiSession.registerForMidiCallbacks(this)
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
                addMessage(message)
                presetMessages.add(message)
                setViewStatus(QS300PresetCaptureStatus.CAPTURING)
            }
        } else {
            addMessage(message)
            presetMessages.add(message)
            if (isValidEndMessage(message)) {
                setViewStatus(QS300PresetCaptureStatus.COMPLETE)
            } else {
                if (status != QS300PresetCaptureStatus.CAPTURING) {
                    setViewStatus(QS300PresetCaptureStatus.CAPTURING)
                }
            }
        }
    }

    private fun addMessage(message: MidiMessage) {
        presetMessages.add(message)
        binding.tvCaptureData.text = StringBuilder().apply {
            append(binding.tvCaptureData.text)
            append("\n")
            append(message.msg?.joinToString(" "))
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

    private fun setViewStatus(status: QS300PresetCaptureStatus) {
        this.status = status
        binding.bSavePreset.isEnabled = status.isSaveEnabled
        binding.bClearData.isEnabled = status.isClearEnabled
        binding.tvStatusText.text = getString(status.statusTextRes)
        binding.ivStatusIcon.setImageResource(status.iconRes)
        if (status == QS300PresetCaptureStatus.CAPTURING) {
            (binding.ivStatusIcon.drawable as AnimatedVectorDrawable).start()
        }
    }

    private fun onClearDataClicked(v: View) {
        reset()
    }

    private fun onSavePresetClicked(v: View) {
        if (presetMessages.isNotEmpty()) {
            requireContext().openFileOutput(PRESETS_FILE_NAME, Context.MODE_APPEND).apply {

            }
        }
        reset()
    }

    private fun reset() {
        presetMessages.clear()
        if (status != QS300PresetCaptureStatus.NO_OUTPUT) {
            setViewStatus(QS300PresetCaptureStatus.READY)
        }
        binding.tvCaptureData.text = ""
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

    enum class QS300PresetCaptureStatus(
        val statusTextRes: Int,
        val iconRes: Int,
        val isSaveEnabled: Boolean,
        val isClearEnabled: Boolean
    ) {
        NO_OUTPUT(R.string.qs_cap_no_output, R.drawable.baseline_close_24, true, true),
        READY(R.string.qs_cap_ready, R.drawable.baseline_check_circle_outline_24, false, true),
        COMPLETE(R.string.qs_cap_complete, R.drawable.baseline_check_circle_24, true, true),
        CAPTURING(R.string.qs_cap_capturing, R.drawable.hourglass_animation, false, true)
    }

    companion object {
        private const val TAG = "QS300PresetCaptureFragment"
        private const val ARG_PRESET_JSON = "arg_preset_json"
    }
}