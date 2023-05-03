package com.example.xgbuddy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

@AndroidEntryPoint
class KeyboardFragment : Fragment() {

    @Inject
    lateinit var midiSession: MidiSession

    private var octave = 5
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
                        sendMidiNote(note, true)
                    }

                    override fun onKeyUp(note: String) {
                        sendMidiNote(note, false)
                    }
                }
        }
        return binding.root
    }

    private fun sendMidiNote(note: String, isKeyDown: Boolean) {
        val msg = encodeNote(note, isKeyDown)
        midiSession.send(MidiMessage(msg, 0))
    }

    private fun encodeNote(noteString: String, isKeyDown: Boolean): ByteArray? {
        val noteVal = (Note::name findBy noteString.uppercase(Locale.getDefault()))?.ordinal
        noteVal?.let { note ->
            val actualNote = note + (12 * octave)
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