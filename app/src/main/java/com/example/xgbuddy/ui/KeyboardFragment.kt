package com.example.xgbuddy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.xgbuddy.MidiSession
import com.example.xgbuddy.databinding.FragmentKeyboardBinding
import com.example.xgbuddy.ui.custom.KeyView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class KeyboardFragment : Fragment() {

    @Inject
    lateinit var midiSession: MidiSession

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
                        // Encode note to message
                        // Create note-on MidiMessage
                        // midiSession.send(message)
                    }

                    override fun onKeyUp(note: String) {
                        // Encode note to message
                        // Create note-off MidiMessage
                        // midiSession.send(message)
                    }
                }
        }
        return binding.root
    }
}