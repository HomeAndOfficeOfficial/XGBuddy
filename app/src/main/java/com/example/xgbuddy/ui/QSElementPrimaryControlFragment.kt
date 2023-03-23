package com.example.xgbuddy.ui

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Spinner
import com.example.xgbuddy.R
import com.example.xgbuddy.data.QS300Preset
import com.example.xgbuddy.ui.custom.ControlViewGroup
import com.google.android.material.switchmaterial.SwitchMaterial

class QSElementPrimaryControlFragment : QS300ElementBaseFragment() {

    private lateinit var waveValues: IntArray

    override val elementAttrs: IntArray = R.styleable.ElementEditFragment_MembersInjector
    override val attrIndexElIndex: Int =
        R.styleable.QSElementPrimaryControlFragment_MembersInjector_elementIndex

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v =
            layoutInflater.inflate(R.layout.fragment_qs_element_primary_control, container, false)
        v.backgroundTintList =
            ColorStateList.valueOf(resources.getIntArray(R.array.element_container_colors)[elementIndex])
        findViews(v)
        initControlGroup(cvgElementMain, isInteractive = false, shouldShowColoredHeader = false)
        initListeners()
        return v
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        waveValues = resources.getIntArray(R.array.qs300_wave_values)
    }

    private fun initListeners() {
        spWave.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val waveValue = waveValues[position]
                val element =
                    viewModel.preset.value!!.voices[viewModel.voice].elements[elementIndex]
                if (decodeWave(element.waveHi, element.waveLo) != waveValue) {
                    element.setWaveValue(waveValue)
                    // TODO: Call midi send method
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    override fun updateViews(preset: QS300Preset) {
        super.updateViews(preset)
        val voiceIndex = viewModel.voice
        val voice = preset.voices[voiceIndex]
        val element = preset.voices[voiceIndex].elements[elementIndex]
        val waveValue = decodeWave(element.waveHi, element.waveLo)
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
    }

    companion object {
        const val TAG = "QSElementPrimary"
    }

    private lateinit var spWave: Spinner
    private lateinit var swElementOn: SwitchMaterial
    private lateinit var cvgElementMain: ControlViewGroup
}