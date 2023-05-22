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
import com.google.android.material.switchmaterial.SwitchMaterial

class VariationFragment : ControlBaseFragment() {

    private val midiViewModel: MidiViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val v = layoutInflater.inflate(R.layout.fragment_variation, container, false)
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
        spVariType = v.findViewById(R.id.spVariType)
        spVariPart = v.findViewById(R.id.spVariPart)
        swVariConnect = v.findViewById(R.id.swVariConnect)
        cvgVari = v.findViewById(R.id.cvgVari)
        llVariExtras = v.findViewById(R.id.llVariExtras)
        cvgVariCtrl = v.findViewById(R.id.cvgVariCtrl)
    }

    private lateinit var spVariType: Spinner
    private lateinit var spVariPart: Spinner
    private lateinit var swVariConnect: SwitchMaterial
    private lateinit var cvgVari: ControlViewGroup
    private lateinit var llVariExtras: LinearLayout
    private lateinit var cvgVariCtrl: ControlViewGroup
}