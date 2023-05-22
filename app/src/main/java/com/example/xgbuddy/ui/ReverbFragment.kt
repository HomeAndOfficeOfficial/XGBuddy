package com.example.xgbuddy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Spinner
import androidx.fragment.app.activityViewModels
import com.example.xgbuddy.R
import com.example.xgbuddy.data.ControlParameter
import com.example.xgbuddy.ui.custom.ControlViewGroup
import com.example.xgbuddy.ui.custom.SliderControlView

class ReverbFragment : ControlBaseFragment() {

    private val midiViewModel: MidiViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val v = layoutInflater.inflate(R.layout.fragment_reverb, container, false)
        findViews(v)
        return v
    }

    override fun initParameter(paramId: Int): ControlParameter {
        TODO("Not yet implemented")
    }

    override fun onParameterChanged(controlParameter: ControlParameter, isTouching: Boolean) {
        TODO("Not yet implemented")
    }

    private fun findViews(v: View) {
        spReverbType = v.findViewById(R.id.spReverbType)
        cvgReverb = v.findViewById(R.id.cvgReverb)
        llReverbExtras = v.findViewById(R.id.llReverbExtras)
    }

    private lateinit var spReverbType: Spinner
    private lateinit var cvgReverb: ControlViewGroup
    private lateinit var llReverbExtras: LinearLayout
}