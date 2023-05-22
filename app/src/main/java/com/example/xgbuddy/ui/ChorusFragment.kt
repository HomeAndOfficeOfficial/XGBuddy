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

class ChorusFragment : ControlBaseFragment() {

    private val midiViewModel: MidiViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val v = layoutInflater.inflate(R.layout.fragment_chorus, container, false)
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
        spChorusType = v.findViewById(R.id.spChorusType)
        cvgChorus = v.findViewById(R.id.cvgChorus)
        llChorusExtras = v.findViewById(R.id.llChorusExtras)
    }

    private lateinit var spChorusType: Spinner
    private lateinit var cvgChorus: ControlViewGroup
    private lateinit var llChorusExtras: LinearLayout
}