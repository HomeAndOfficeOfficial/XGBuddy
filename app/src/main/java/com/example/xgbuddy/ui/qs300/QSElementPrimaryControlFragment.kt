package com.example.xgbuddy.ui.qs300

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.example.xgbuddy.R
import com.example.xgbuddy.data.qs300.QS300Preset
import com.example.xgbuddy.databinding.FragmentQsElementPrimaryControlBinding
import com.example.xgbuddy.util.MidiMessageUtility
import com.google.android.material.switchmaterial.SwitchMaterial
import kotlin.experimental.and

class QSElementPrimaryControlFragment : QS300ElementBaseFragment() {

    private lateinit var waveValues: IntArray

    override val binding: FragmentQsElementPrimaryControlBinding by lazy {
        FragmentQsElementPrimaryControlBinding.inflate(layoutInflater)
    }
    override val elementAttrs: IntArray = R.styleable.ElementEditFragment_MembersInjector
    override val attrIndexElIndex: Int =
        R.styleable.QSElementPrimaryControlFragment_MembersInjector_elementIndex

    private var isSpinnerUpdating = false
    private var isSwitchUpdating = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.tvPrimElementNum.text = buildString {
            append("Element ")
            append(elementIndex + 1)
        }
        binding.root.backgroundTintList =
            ColorStateList.valueOf(resources.getIntArray(R.array.element_container_colors)[elementIndex])
        isSpinnerUpdating = true
        isSwitchUpdating = true
        initControlGroup(
            binding.cvgElementMain,
            isInteractive = false,
            shouldShowColoredHeader = false,
            shouldStartExpanded = true
        )
        viewModel.elementStatus.observe(viewLifecycleOwner) {
            binding.swElementOn.isChecked =
                (it and (elementIndex + 1).toByte() == (elementIndex + 1).toByte())
        }
        return super.onCreateView(inflater, container, savedInstanceState)
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
        binding.spQsWave.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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
                        midiSession.send(
                            MidiMessageUtility.getQS300BulkDump(
                                voice,
                                viewModel.voice
                            )
                        )
                    }
                }
                isSpinnerUpdating = false
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        binding.swElementOn.setOnClickListener {
            val isChecked = (it as SwitchMaterial).isChecked
            viewModel.updateElementStatus(elementIndex, isChecked)
            midiSession.send(
                MidiMessageUtility.getQS300BulkDump(
                    viewModel.preset.value!!.voices[viewModel.voice],
                    viewModel.voice
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
        isSpinnerUpdating = binding.spQsWave.onItemSelectedListener != null
        binding.spQsWave.setSelection(waveValues.indexOfFirst { it == waveValue })
        // TODO: Verify element switch values when more than two elements are allowed
        binding.swElementOn.isChecked = elementIndex <= voice.elementSwitch
    }

    private fun decodeWave(waveHi: Byte, waveLo: Byte): Int =
        (waveLo.toInt() or (waveHi.toInt() shl 7))

    companion object {
        const val TAG = "QSElementPrimary"
    }
}