package com.example.xgbuddy.ui

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.xgbuddy.R
import com.example.xgbuddy.ui.custom.ControlViewGroup
import com.example.xgbuddy.viewmodel.QS300ViewModel
import com.google.android.material.switchmaterial.SwitchMaterial
import dagger.hilt.android.AndroidEntryPoint
import kotlin.experimental.or

@AndroidEntryPoint
class QSElementPrimaryControlFragment : Fragment() {

    private var elementIndex = NOT_INITIALIZED

    private val viewModel: QS300ViewModel by activityViewModels()

    private lateinit var waveValues: IntArray

    /**
     * TODO: This is pretty much duplicated from ElementEditFragment. These fragments do
     *   basically the same thing, except this one has slightly different view logic and layout.
     *   Should look into creating a fragment class that they could inherit from.
     */
    override fun onInflate(context: Context, attrs: AttributeSet, savedInstanceState: Bundle?) {
        super.onInflate(context, attrs, savedInstanceState)
        if (elementIndex == NOT_INITIALIZED) {
            val typedArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.QSElementPrimaryControlFragment_MembersInjector,
                0,
                0
            )
            if (typedArray.hasValue(R.styleable.QSElementPrimaryControlFragment_MembersInjector_elementIndex)) {
                elementIndex = typedArray.getInt(
                    R.styleable.QSElementPrimaryControlFragment_MembersInjector_elementIndex,
                    NOT_INITIALIZED
                )
            }
            typedArray.recycle()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (elementIndex < 0) {
            with(savedInstanceState?.getInt(ARG_EL_INDEX)) {
                if (this != null) {
                    elementIndex = this
                } else {
                    Log.e(TAG, "Element index was never saved or initialized")
                }
            }
        }
        waveValues = resources.getIntArray(R.array.qs300_wave_values)
        val v =
            layoutInflater.inflate(R.layout.fragment_qs_element_primary_control, container, false)
        findViews(v)
        initObservers()
        initListeners()
        return v
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

    private fun initObservers() {
        viewModel.preset.observe(viewLifecycleOwner) { preset ->
            val voice = viewModel.voice
            if (elementIndex < preset.voices[viewModel.voice].elements.size) {
                val element = preset.voices[voice].elements[elementIndex]
                val waveValue = decodeWave(element.waveHi, element.waveLo)
                spWave.setSelection(waveValues.indexOfFirst { it == waveValue })
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(ARG_EL_INDEX, elementIndex)
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
        private const val ARG_EL_INDEX = "arg_el_index"
        private const val NOT_INITIALIZED = -1
    }

    private lateinit var spWave: Spinner
    private lateinit var swElementOn: SwitchMaterial
    private lateinit var cvgElementMain: ControlViewGroup
}