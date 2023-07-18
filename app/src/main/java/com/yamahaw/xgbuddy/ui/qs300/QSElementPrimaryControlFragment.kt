package com.yamahaw.xgbuddy.ui.qs300

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.yamahaw.xgbuddy.R
import com.yamahaw.xgbuddy.data.qs300.QS300Preset
import com.yamahaw.xgbuddy.databinding.FragmentQsElementPrimaryControlBinding
import com.yamahaw.xgbuddy.util.MidiMessageUtility
import com.google.android.material.switchmaterial.SwitchMaterial
import kotlin.experimental.and

class QSElementPrimaryControlFragment :
    QS300ElementBaseFragment<FragmentQsElementPrimaryControlBinding>() {

    private lateinit var waveValues: IntArray

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentQsElementPrimaryControlBinding =
        FragmentQsElementPrimaryControlBinding::inflate
    override val elementAttrs: IntArray = R.styleable.ElementEditFragment_MembersInjector
    override val attrIndexElIndex: Int =
        R.styleable.QSElementPrimaryControlFragment_MembersInjector_elementIndex

    private var isSpinnerUpdating = false
    private var isSwitchUpdating = false

    override fun setupViews() {
        binding!!.apply {
            tvPrimElementNum.text = buildString {
                append("Element ")
                append(elementIndex + 1)
            }
            root.backgroundTintList =
                ColorStateList.valueOf(resources.getIntArray(R.array.element_container_colors)[elementIndex])
            isSpinnerUpdating = true
            isSwitchUpdating = true
            viewModel.elementStatus.observe(viewLifecycleOwner) {
                swElementOn.isChecked =
                    (it and (elementIndex + 1).toByte() == (elementIndex + 1).toByte())
            }
        }
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
        binding!!.apply {
            spQsWave.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (!isSpinnerUpdating) {
                        val waveValue = waveValues[position]
                        val voiceIndex = viewModel.voice.value!!
                        val voice = viewModel.preset.value!!.voices[voiceIndex]
                        val element = voice.elements[elementIndex]
                        val decodedWave = decodeWave(element.waveHi, element.waveLo)
                        if (decodedWave != waveValue) {
                            element.setWaveValue(waveValue)
                            midiSession.send(
                                MidiMessageUtility.getQS300BulkDump(
                                    voice,
                                    voiceIndex,
                                    midiViewModel.selectedChannel.value!!
                                )
                            )
                        }
                    }
                    isSpinnerUpdating = false
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
            swElementOn.setOnClickListener {
                val voiceIndex = viewModel.voice.value!!
                viewModel.updateElementStatus(elementIndex)
                midiSession.send(
                    MidiMessageUtility.getQS300BulkDump(
                        viewModel.preset.value!!.voices[voiceIndex],
                        voiceIndex,
                        midiViewModel.selectedChannel.value!!
                    )
                )
            }
        }
    }

    override fun updateViews(preset: QS300Preset) {
        super.updateViews(preset)
        val voiceIndex = viewModel.voice.value!!
        val voice = preset.voices[voiceIndex]
        val element = preset.voices[voiceIndex].elements[elementIndex]
        val waveValue = decodeWave(element.waveHi, element.waveLo)
        isSpinnerUpdating = binding!!.spQsWave.onItemSelectedListener != null
        binding!!.spQsWave.setSelection(waveValues.indexOfFirst { it == waveValue })
        // TODO: Verify element switch values when more than two elements are allowed
        val elSwitchBit = (voice.elementSwitch.toInt() shr elementIndex) and 1
        binding!!.swElementOn.isChecked = elSwitchBit == 1
    }

    private fun decodeWave(waveHi: Byte, waveLo: Byte): Int =
        (waveLo.toInt() or (waveHi.toInt() shl 7))

    companion object {
        const val TAG = "QSElementPrimary"
    }
}