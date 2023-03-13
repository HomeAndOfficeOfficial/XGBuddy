package com.example.xgbuddy.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import com.example.xgbuddy.MidiSession
import com.example.xgbuddy.data.QS300Preset
import com.example.xgbuddy.databinding.FragmentQS300SessionBinding
import com.example.xgbuddy.util.MidiStoredDataUtility
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class QS300SessionFragment : Fragment(), OnItemSelectedListener {

    @Inject
    lateinit var midiSession: MidiSession

    @Inject
    lateinit var dataUtility: MidiStoredDataUtility

    private lateinit var binding: FragmentQS300SessionBinding
    private lateinit var presets: List<QS300Preset>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presets = dataUtility.getQS300Presets()
    }

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
            presets.map { it.name }
        ).also { arrayAdapter ->
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.sQSPresets.apply {
                adapter = arrayAdapter
                onItemSelectedListener = this@QS300SessionFragment
            }
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val midiBulkDump = dataUtility.getQS300BulkDumpMessage(presets[position])
        midiSession.send(midiBulkDump)

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

}