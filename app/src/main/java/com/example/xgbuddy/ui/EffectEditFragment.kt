package com.example.xgbuddy.ui

import android.annotation.SuppressLint
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Spinner
import androidx.viewbinding.ViewBinding
import com.example.xgbuddy.data.ControlParameter
import com.example.xgbuddy.data.xg.Effect
import com.example.xgbuddy.data.xg.EffectParameterData
import com.example.xgbuddy.util.EnumFinder.findBy
import com.example.xgbuddy.util.MidiMessageUtility

abstract class EffectEditFragment<VB: ViewBinding> : ControlBaseFragment<VB>(), OnItemSelectedListener {

    abstract val effectType: Int
    private var wasSpinnerTouched = false

    @SuppressLint("ClickableViewAccessibility")
    protected val spinnerTouchListener = OnTouchListener { v, event ->
        if (event.action == MotionEvent.ACTION_DOWN) {
            wasSpinnerTouched = true
        }
        false
    }

    override fun setupViews() {
        setupSpinner()
        initControlGroups()
    }

    override fun onResume() {
        super.onResume()
        updateViews(getViewModelEffect())
    }

    private fun updateViews(effect: Effect) {
        controlGroups.forEach {
            it.updateViews(effect)
        }
    }

    override fun initParameter(paramId: Int): ControlParameter? {
        val vmEffect = getViewModelEffect()
        val effectParamData = EffectParameterData::resId findBy paramId
        if (isBigParameter(effectParamData!!)) {
            val value = vmEffect.getPropertyValue(effectParamData.reflectedBigField)
            val reverbParam = vmEffect.parameterList?.get(effectParamData)
            val defaultValue = vmEffect.defaultValues!![effectParamData.paramIndex!!]
            return ControlParameter(
                getString(reverbParam?.nameRes ?: effectParamData.resId),
                effectParamData,
                reverbParam,
                value,
                defaultValue
            )
        } else {
            val value = vmEffect.getPropertyValue(effectParamData.reflectedField)
            return ControlParameter(
                getString(effectParamData.resId),
                effectParamData,
                null,
                value.toInt(),
                effectParamData.default.toInt()
            )
        }
    }

    override fun onParameterChanged(controlParameter: ControlParameter, isTouching: Boolean) {
        val param = EffectParameterData::addrLo findBy controlParameter.addr.toByte()
        val vmEffect = getViewModelEffect()
        if (isBigParameter(param!!)) {
            vmEffect.setProperty(
                param.reflectedBigField,
                controlParameter.value
            )
            Log.d("EffectEditFragment", "Viewmodel.reverb.pan = ${midiViewModel.reverb.revPan}")
            // Todo send message
        } else {
            vmEffect.setProperty(param.reflectedField, controlParameter.value.toByte())
            // Todo Send message
        }
        midiSession.send(MidiMessageUtility.getEffectParamChange(param, controlParameter.value))
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (wasSpinnerTouched) {
            val shouldUpdateViews = onEffectPresetSelected(position, parent as Spinner)
            if (shouldUpdateViews) {
                val effect = getViewModelEffect()
                updateViews(effect)
                midiSession.send(MidiMessageUtility.getEffectPresetChange(effect))
            }
        }
        wasSpinnerTouched = false
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        wasSpinnerTouched = false
    }

    abstract fun getViewModelEffect(): Effect
    abstract fun isBigParameter(effectParameterData: EffectParameterData): Boolean
    abstract fun setupSpinner()
    abstract fun initControlGroups()
    abstract fun onEffectPresetSelected(index: Int, spinner: Spinner): Boolean
    //           ^ returns if views should be updated

    companion object {
        const val REVERB = 0
        const val CHORUS = 1
        const val VARIATION = 2
    }
}