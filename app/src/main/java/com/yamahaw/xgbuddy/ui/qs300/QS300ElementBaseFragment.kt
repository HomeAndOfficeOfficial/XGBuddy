package com.yamahaw.xgbuddy.ui.qs300

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.viewbinding.ViewBinding
import com.yamahaw.xgbuddy.R
import com.yamahaw.xgbuddy.data.ControlParameter
import com.yamahaw.xgbuddy.data.qs300.QS300ElementParameter
import com.yamahaw.xgbuddy.data.qs300.QS300Preset
import com.yamahaw.xgbuddy.ui.ControlBaseFragment
import com.yamahaw.xgbuddy.ui.custom.ControlViewGroup
import com.yamahaw.xgbuddy.util.EnumFinder.findBy
import com.yamahaw.xgbuddy.util.MidiMessageUtility
import com.yamahaw.xgbuddy.viewmodel.QS300ViewModel

abstract class QS300ElementBaseFragment<VB : ViewBinding> : ControlBaseFragment<VB>() {

    protected val viewModel: QS300ViewModel by activityViewModels()

    protected var elementIndex = NOT_INITIALIZED

    protected abstract val elementAttrs: IntArray
    protected abstract val attrIndexElIndex: Int

    private var currentParam: QS300ElementParameter? = null

    override fun onInflate(context: Context, attrs: AttributeSet, savedInstanceState: Bundle?) {
        super.onInflate(context, attrs, savedInstanceState)
        val typedArray = context.obtainStyledAttributes(attrs, elementAttrs, 0, 0)
        if (typedArray.hasValue(attrIndexElIndex)) {
            elementIndex = typedArray.getInt(attrIndexElIndex, NOT_INITIALIZED)
        }
        typedArray.recycle()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (elementIndex < 0) {
            with(savedInstanceState?.getInt(ARG_EL_INDEX)) {
                if (this != null) {
                    elementIndex = this
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.voice.observe(viewLifecycleOwner) {
            viewModel.preset.value?.let {
                updateViews(it)
            }
        }
        viewModel.preset.observe(viewLifecycleOwner) {
            updateViews(it!!)
        }
    }

    protected open fun updateViews(preset: QS300Preset) {
        val element = preset.voices[viewModel.voice.value!!].elements[elementIndex]
        controlGroups.forEach { controlGroup ->
            controlGroup.updateViews(element)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(ARG_EL_INDEX, elementIndex)
    }

    override fun initControlGroup(
        controlGroup: ControlViewGroup,
        isInteractive: Boolean,
        shouldShowColoredHeader: Boolean,
        shouldStartExpanded: Boolean,
        extraChildren: ViewGroup?,
        shouldReceiveAllTouchCallbacks: Boolean,
        isRealtime: Boolean
    ) {
        controlGroup.apply {
            if (shouldShowColoredHeader) {
                headerColor = resources.getIntArray(R.array.element_container_colors)[elementIndex]
            }
        }
        super.initControlGroup(
            controlGroup,
            isInteractive,
            shouldShowColoredHeader,
            shouldStartExpanded,
            extraChildren,
            shouldReceiveAllTouchCallbacks = false,
            isRealtime = false
        )
    }


    override fun initParameter(paramId: Int): ControlParameter {
        val param = QS300ElementParameter::nameRes findBy paramId
        val voice = viewModel.preset.value!!.voices[viewModel.voice.value!!]
        val element = voice.elements[elementIndex]
        return ControlParameter(
            getString(param!!.nameRes),
            param,
            element.getPropertyValue(param.reflectedField)
        )
    }

    override fun onParameterChanged(controlParameter: ControlParameter, isTouching: Boolean) {
        if (controlParameter.nameRes != currentParam?.nameRes) {
            currentParam = QS300ElementParameter::baseAddress findBy controlParameter.addr
        }
        val voiceIndex = viewModel.voice.value!!
        val voice = viewModel.preset.value!!.voices[voiceIndex]
        voice.elements[elementIndex].setProperty(
            currentParam!!.reflectedField, controlParameter.value.toByte()
        )

        midiSession.sendBulkMessage(
            MidiMessageUtility.getQS300BulkDump(
                voice,
                voiceIndex,
                midiViewModel.selectedChannel.value!!
            )
        )
    }

    companion object {
        private const val TAG = "QS300ElementBaseFragment"
        private const val ARG_EL_INDEX = "arg_el_index"
        protected const val NOT_INITIALIZED = -1
    }

}