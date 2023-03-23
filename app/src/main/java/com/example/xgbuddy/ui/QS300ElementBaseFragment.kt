package com.example.xgbuddy.ui

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.xgbuddy.MidiSession
import com.example.xgbuddy.R
import com.example.xgbuddy.data.*
import com.example.xgbuddy.ui.custom.ControlViewGroup
import com.example.xgbuddy.ui.custom.ParameterControlView
import com.example.xgbuddy.ui.custom.SliderControlView
import com.example.xgbuddy.util.EnumFinder.findBy
import com.example.xgbuddy.viewmodel.QS300ViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
abstract class QS300ElementBaseFragment : Fragment(),
    ParameterControlView.OnParameterChangedListener {

    @Inject
    lateinit var midiSession: MidiSession

    protected val viewModel: QS300ViewModel by activityViewModels()

    protected var currentParam: QS300ElementParameter? = null
    protected var elementIndex = NOT_INITIALIZED

    protected abstract val elementAttrs: IntArray
    protected abstract val attrIndexElIndex: Int

    private val controlGroups: MutableList<ControlViewGroup> = mutableListOf()

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
        Log.d(TAG, "Element: ${preset.voices[viewModel.voice].elements[elementIndex]}")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(ARG_EL_INDEX, elementIndex)
    }

    protected fun initControlGroup(
        controlGroup: ControlViewGroup,
        isInteractive: Boolean,
        shouldShowColoredHeader: Boolean,
        extraChildren: ViewGroup? = null
    ) {
        controlGroup.apply {
            this.isInteractive = isInteractive
            if (shouldShowColoredHeader) {
                headerColor = resources.getIntArray(R.array.element_container_colors)[elementIndex]
            }
            controlItemIds.forEach {
                addControlView(SliderControlView(requireContext()).apply {
                    controlParameter = initElementParam(it)
                    listener = this@QS300ElementBaseFragment
                })
            }
        }
        extraChildren?.apply {
            for (i in 0 until childCount) {
                (getChildAt(i) as ParameterControlView).apply {
                    controlParameter = initElementParam(paramId)
                    listener = this@QS300ElementBaseFragment
                    controlGroup.mapUngroupedView(this)
                }
            }
        }
        controlGroups.add(controlGroup)
    }

    private fun initElementParam(descriptionRes: Int): QS300ControlParameter {
        val param = QS300ElementParameter::descriptionRes findBy descriptionRes
        return QS300ControlParameter(
            param!!,
            viewModel.preset.value!!.voices[viewModel.voice].elements[elementIndex].getPropertyValue(
                param.reflectedField
            )
        )
    }

    /**
     * TODO: I think this logic might be able to be applied universally. In other words,
     * I think the bulk of the logic for updating the viewmodel and sending messages could
     * probably be consolidated in a higher level fragment (like QS300Session perhaps). I figure
     * there will be some difference in logic between different instrument modes.
     */
    override fun onParameterChanged(controlParameter: ControlParameter) {
        if (controlParameter.name != currentParam?.name) {
            currentParam = QS300ElementParameter::baseAddress findBy controlParameter.addr
        }
        viewModel.preset.value!!.voices[viewModel.voice].elements[elementIndex].setProperty(
            currentParam!!.reflectedField,
            controlParameter.value
        )
        midiSession.send(viewModel.preset.value!!.voices[viewModel.voice].generateBulkDump())
    }

    companion object {
        private const val TAG = "QS300ElementBaseFragment"
        private const val ARG_EL_INDEX = "arg_el_index"
        protected const val NOT_INITIALIZED = -1
    }

}