package com.example.xgbuddy.ui.qs300

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.xgbuddy.R
import com.example.xgbuddy.data.ControlParameter
import com.example.xgbuddy.data.qs300.QS300ControlParameter
import com.example.xgbuddy.data.qs300.QS300ElementParameter
import com.example.xgbuddy.data.qs300.QS300Preset
import com.example.xgbuddy.ui.ControlBaseFragment
import com.example.xgbuddy.ui.custom.ControlViewGroup
import com.example.xgbuddy.ui.custom.ParameterControlView
import com.example.xgbuddy.ui.custom.SliderControlView
import com.example.xgbuddy.util.EnumFinder.findBy
import com.example.xgbuddy.viewmodel.QS300ViewModel

abstract class QS300ElementBaseFragment : ControlBaseFragment() {

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
                } else {
                    Log.e(TAG, "Element index was never saved or initialized")
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.preset.observe(viewLifecycleOwner) {
            it?.let {
                if (elementIndex < it.voices[viewModel.voice].elements.size) {
                    Log.d(TAG, "Preset observed in ${this.javaClass.name}")
                    updateViews(it)
                }
            }
        }
    }

    protected open fun updateViews(preset: QS300Preset) {
        val element = preset.voices[viewModel.voice].elements[elementIndex]
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
        extraChildren: ViewGroup?
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
            extraChildren
        )
    }

    override fun initParameter(paramId: Int): ControlParameter {
        val param = QS300ElementParameter::descriptionRes findBy paramId
        return QS300ControlParameter(
            param!!,
            viewModel.preset.value!!.voices[viewModel.voice].elements[elementIndex].getPropertyValue(
                param.reflectedField
            )
        )
    }

    override fun onParameterChanged(controlParameter: ControlParameter) {
        if (controlParameter.name != currentParam?.name) {
            currentParam = QS300ElementParameter::baseAddress findBy controlParameter.addr
        }
        viewModel.preset.value!!.voices[viewModel.voice].elements[elementIndex].setProperty(
            currentParam!!.reflectedField,
            controlParameter.value
        )
        midiSession.send(
            viewModel.preset.value!!.voices[viewModel.voice].getBulkDumpForParameterChange(
                controlParameter,
                currentParam!!
            )
        )
    }

    companion object {
        private const val TAG = "QS300ElementBaseFragment"
        private const val ARG_EL_INDEX = "arg_el_index"
        protected const val NOT_INITIALIZED = -1
    }

}