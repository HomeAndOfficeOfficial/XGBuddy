package com.example.xgbuddy.ui.qs300

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.xgbuddy.databinding.FragmentQS300SessionBinding
import com.example.xgbuddy.viewmodel.QS300ViewModel

class QS300SessionFragment : Fragment() {

    private val qs300ViewModel: QS300ViewModel by activityViewModels()
    private lateinit var binding: FragmentQS300SessionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQS300SessionBinding.inflate(layoutInflater)
        binding.tvPresetName.text =
            qs300ViewModel.preset.value!!.voices[qs300ViewModel.voice].voiceName
        return binding.root
    }
}