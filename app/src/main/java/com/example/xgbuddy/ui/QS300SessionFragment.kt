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

/**
 * I have some things to think about here...
 *
 * I think there needs to be an overall SessionFragment.
 * The SessionFragment will contain a bottom navigation with some different tabs.
 *
 * The first tab will be Parts
 * This will be a similat view to XGManager's parts. Basically just a table showing the
 * program/voice names and some main parameters. Clicking on a voice/program will
 * navigate to the Edit tab. THe tabs on the bottom nav will change Depending on if the
 * selected voice is a QS300 voice or a regular midi/XG program. Also the edit screen
 * that it navigates to will be appropriate to the selected voice.
 *
 * Additional note about parts - it would be nice to long click on an item and have some
 * options like "convert to XG" or "convert to QS300 Voice". This brings up some questions
 * about how presets, voices, and XG parts will be able to integrate and work in the same
 * setup files.
 *
 * There will probably be multiple edit tabs as well. One for QS Voice parameters, one for
 * overall QS preset parameters, one for XG parameters (which can be applied to QS300 as well),
 *
 * There will be an effects tab. Within this will be controls for Reverb, Chorus, and Variation
 *
 * THere will be a system tab with things like master volume, tune, transpose, etc.
 *
 *
 *
 * Note about controls: I think the way to start this is just make a custom viewgroup that
 * arranges a label, value text and contol element (like a seekbar at first), and takes in a
 * parameter that tells it what parameter it's controlling. All paramaters will eventually
 * need to be defined in enums. Right now I have all QS300 element parameters defined, but
 * I'll need to add things like QS300VoiceParameter, SystemParameter, XGParameter, etc.
 *
 * I'll need to research to figure out the best organization for some of these.
 *
 * Anyway, maybe to avoid weird type checking issues, I can create a class that adapts
 * those enum types to a common control type which has name, min, max, default, outputFormatter,
 * and a method to construct a midiMessage.
 */
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