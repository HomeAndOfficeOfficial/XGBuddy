package com.example.xgbuddy.ui.qs300

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.xgbuddy.MidiSession
import com.example.xgbuddy.databinding.FragmentQS300SessionBinding
import com.example.xgbuddy.util.MidiMessageUtility
import com.example.xgbuddy.viewmodel.QS300ViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class QS300SessionFragment : Fragment(), AdapterView.OnItemSelectedListener {

    @Inject
    lateinit var midiSession: MidiSession

    private val qS300ViewModel: QS300ViewModel by activityViewModels()

    private lateinit var binding: FragmentQS300SessionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQS300SessionBinding.inflate(layoutInflater)
        addPresetsToSpinner()
        return binding.root
    }

    private fun addPresetsToSpinner() {
        ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            qS300ViewModel.presets.map { it.name }
        ).also { arrayAdapter ->
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.sQSPresets.apply {
                adapter = arrayAdapter
                onItemSelectedListener = this@QS300SessionFragment
            }
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        qS300ViewModel.preset.value = qS300ViewModel.presets[position]
        midiSession.sendBulkMessage(
            MidiMessageUtility.getQS300BulkDump(
                qS300ViewModel.preset.value!!.voices[0] // Just working with single voice for now
            )
        )
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

}