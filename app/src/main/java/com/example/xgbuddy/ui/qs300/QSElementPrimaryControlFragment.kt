package com.example.xgbuddy.ui.qs300

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.CompoundButton
import android.widget.CompoundButton.OnCheckedChangeListener
import android.widget.Spinner
import android.widget.TextView
import com.example.xgbuddy.R
import com.example.xgbuddy.data.qs300.QS300Preset
import com.example.xgbuddy.ui.custom.ControlViewGroup
import com.example.xgbuddy.util.MidiMessageUtility
import com.google.android.material.switchmaterial.SwitchMaterial
import kotlin.experimental.and

class QSElementPrimaryControlFragment : QS300ElementBaseFragment() {

    private lateinit var waveValues: IntArray

    override val elementAttrs: IntArray = R.styleable.ElementEditFragment_MembersInjector
    override val attrIndexElIndex: Int =
        R.styleable.QSElementPrimaryControlFragment_MembersInjector_elementIndex

    private var isSpinnerUpdating = false
    private var isSwitchUpdating = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v =
            layoutInflater.inflate(R.layout.fragment_qs_element_primary_control, container, false)
        v.backgroundTintList =
            ColorStateList.valueOf(resources.getIntArray(R.array.element_container_colors)[elementIndex])
        isSpinnerUpdating = true
        isSwitchUpdating = true
        findViews(v)
        initControlGroup(cvgElementMain, isInteractive = false, shouldShowColoredHeader = false)
        viewModel.elementStatus.observe(viewLifecycleOwner) {
            swElementOn.isChecked =
                (it and (elementIndex + 1).toByte() == (elementIndex + 1).toByte())
        }
        return v
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        waveValues = resources.getIntArray(R.array.qs300_wave_values)
    }

    override fun onResume() {
        super.onResume()
        initListeners()
    }

    private fun initListeners() {
        spWave.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (!isSpinnerUpdating) {
                    val waveValue = waveValues[position]
                    val voice = viewModel.preset.value!!.voices[viewModel.voice]
                    val element = voice.elements[elementIndex]
                    if (decodeWave(element.waveHi, element.waveLo) != waveValue) {
                        element.setWaveValue(waveValue)
                        midiSession.send(MidiMessageUtility.getQS300BulkDump(voice))
                    }
                }
                isSpinnerUpdating = false
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        swElementOn.setOnClickListener {
            val isChecked = (it as SwitchMaterial).isChecked
            viewModel.updateElementStatus(elementIndex, isChecked)
            midiSession.send(
                MidiMessageUtility.getQS300BulkDump(
                    viewModel.preset.value!!.voices[viewModel.voice]
                )
            )
        }
    }

    override fun updateViews(preset: QS300Preset) {
        super.updateViews(preset)
        val voiceIndex = viewModel.voice
        val voice = preset.voices[voiceIndex]
        val element = preset.voices[voiceIndex].elements[elementIndex]
        val waveValue = decodeWave(element.waveHi, element.waveLo)
        isSpinnerUpdating = spWave.onItemSelectedListener != null
        spWave.setSelection(waveValues.indexOfFirst { it == waveValue })
        // TODO: Verify element switch values when more than two elements are allowed
        swElementOn.isChecked = elementIndex <= voice.elementSwitch
    }

    private fun decodeWave(waveHi: Byte, waveLo: Byte): Int =
        (waveLo.toInt() or (waveHi.toInt() shl 7))

    private fun findViews(v: View) {
        spWave = v.findViewById(R.id.spQsWave)
        swElementOn = v.findViewById(R.id.swElementOn)
        cvgElementMain = v.findViewById(R.id.cvgElementMain)
        tvElementNum = v.findViewById<TextView?>(R.id.tvPrimElementNum).apply {
            text = buildString {
                append("Element ")
                append(elementIndex + 1)
            }
        }
    }

    companion object {
        const val TAG = "QSElementPrimary"
    }

    private lateinit var spWave: Spinner
    private lateinit var swElementOn: SwitchMaterial
    private lateinit var cvgElementMain: ControlViewGroup
    private lateinit var tvElementNum: TextView
}