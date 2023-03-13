package com.example.xgbuddy

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import com.example.xgbuddy.data.MidiConstants
import com.example.xgbuddy.data.MidiMessage
import com.example.xgbuddy.databinding.FragmentQs300PresetCaptureBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.json.JSONArray
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
        setupViewListeners()
        midiSession.outputDeviceOpened.observe(viewLifecycleOwner) {
            val status =
                if (it) QS300PresetCaptureStatus.READY else QS300PresetCaptureStatus.NO_OUTPUT
            setViewStatus(status)
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val width = resources.getDimensionPixelSize(R.dimen.preset_capture_dialog_width)
        val height = resources.getDimensionPixelSize(R.dimen.preset_capture_dialog_height)
        dialog?.window?.let {
            it.setLayout(width, height)
            it.setBackgroundDrawableResource(R.drawable.popup_bg)
        }
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

    private fun setupViewListeners() {
        binding.etPresetName.addTextChangedListener {
            if (it != null) {
                binding.bSavePreset.isEnabled = it.isNotEmpty()
            }
        }
        binding.bSavePreset.setOnClickListener(this::onSavePresetClicked)
        binding.bClearData.setOnClickListener(this::onClearDataClicked)
    }

    override fun onMidiMessageReceived(message: MidiMessage) {
        presetMessages.add(message)
        runBlocking(Dispatchers.Main) {
            binding.tvCaptureData.text = StringBuilder().apply {
                append(binding.tvCaptureData.text)
                append("\n")
                append(message.msg?.joinToString(" "))
            }
            checkForVoiceName(message)
        }
    }

    private fun checkForVoiceName(message: MidiMessage) {
        val qsId: Byte = try {
            message.msg?.get(MidiConstants.OFFSET_DEVICE_ID)
        } catch (e: IndexOutOfBoundsException) {
            -1
        } ?: -1
        if (qsId == MidiConstants.MODEL_ID_QS300) {
            presetName = String(
                message.msg?.copyOfRange(
                    MidiConstants.OFFSET_QS300_DATA_START,
                    MidiConstants.OFFSET_QS300_DATA_START + MidiConstants.QS300_VOICE_NAME_SIZE
                ) ?: byteArrayOf(), Charsets.US_ASCII
            ).trim()
            binding.etPresetName.setText(presetName)
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
        binding.tvStatusText.text = getString(status.statusTextRes)
        binding.ivStatusIcon.setImageResource(status.iconRes)
    }

    private fun onClearDataClicked(v: View) {
        reset()
    }

    private fun onSavePresetClicked(v: View) {
        if (presetMessages.isEmpty()) {
            return
        }
        val midiMessageArray = JSONArray()
        presetMessages.forEach {
            val dataString = String(it.msg ?: byteArrayOf())
            midiMessageArray.put(dataString)
        }
        val key = presetName.ifEmpty { binding.etPresetName.text.toString() }
        qs300PresetsJSON?.put(key, midiMessageArray)
        reset()
    }

    private fun reset() {
        presetMessages.clear()
        binding.tvCaptureData.text = ""
        presetName = ""
        binding.etPresetName.setText("")
    }

    enum class QS300PresetCaptureStatus(
        val statusTextRes: Int,
        val iconRes: Int
    ) {
        NO_OUTPUT(R.string.qs_cap_no_output, R.drawable.baseline_close_24),
        READY(R.string.qs_cap_ready, R.drawable.baseline_check_circle_outline_24)
    }

    companion object {
        const val TAG = "QS300PresetCaptureFragment"
        private const val ARG_PRESET_JSON = "arg_preset_json"
    }
}