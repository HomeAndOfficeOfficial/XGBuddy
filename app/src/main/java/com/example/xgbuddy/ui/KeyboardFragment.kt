package com.example.xgbuddy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.xgbuddy.MidiSession
import com.example.xgbuddy.data.MidiMessage
import com.example.xgbuddy.data.gm.Note
import com.example.xgbuddy.databinding.FragmentKeyboardBinding
import com.example.xgbuddy.ui.custom.KeyView
import com.example.xgbuddy.util.EnumFinder.findBy
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject
import kotlin.math.max
import kotlin.math.min

@AndroidEntryPoint
class KeyboardFragment : Fragment() {

    @Inject
    lateinit var midiSession: MidiSession

    private var baseOctave = 5
        set(value) {
            field = value
            binding.tvOctave.text = "${value - 2}"
        }
    private var channel = 0

    private val binding: FragmentKeyboardBinding by lazy {
        FragmentKeyboardBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        for (i in 0 until binding.keysContainer.childCount) {
            (binding.keysContainer.getChildAt(i) as KeyView).listener =
                object : KeyView.OnKeyPressListener {
                    override fun onKeyDown(note: String) {
                        val keyOctave = baseOctave + i / 13
                        sendMidiNote(note, keyOctave, true)
                    }

                    override fun onKeyUp(note: String) {
                        val keyOctave = baseOctave + i / 13
                        sendMidiNote(note, keyOctave, false)
                    }
                }
        }
        binding.spKeyCh.apply {
            adapter = ArrayAdapter<Int>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                Array(16) { it + 1 }).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
            onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    channel = position
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }
        binding.bOctDown.setOnClickListener { baseOctave = max(0, baseOctave - 1) }
        binding.bOctUp.setOnClickListener { baseOctave = min(10, baseOctave + 1) }
        return binding.root
    }

    private fun sendMidiNote(note: String, octave: Int, isKeyDown: Boolean) {
        encodeNote(note, octave, isKeyDown)?.let {
            midiSession.send(MidiMessage(it, 0))
        }
    }

    private fun encodeNote(noteString: String, octave: Int, isKeyDown: Boolean): ByteArray? {
        val noteVal = (Note::name findBy noteString.uppercase(Locale.getDefault()))?.ordinal
        noteVal?.let { note ->
            val actualNote = note + (12 * octave)
            if (actualNote > 127) return null
            val noteStatus = if (isKeyDown) 0x90 else 0x80
            val buffer = ByteArray(3)
            buffer[0] = (noteStatus + channel).toByte() // Note On/Off status
            buffer[1] = actualNote.toByte()            // Pitch
            buffer[2] = if (isKeyDown) 96 else 0      // Velocity (nbd for now)
            return buffer
        }
        return null
    }

    companion object {
        const val TAG = "KeyboardFragment"
    }
}