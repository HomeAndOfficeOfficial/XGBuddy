package com.example.xgbuddy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.xgbuddy.databinding.FragmentKeyboardBinding

class KeyboardFragment : Fragment() {

    private val binding: FragmentKeyboardBinding by lazy {
        FragmentKeyboardBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }
}