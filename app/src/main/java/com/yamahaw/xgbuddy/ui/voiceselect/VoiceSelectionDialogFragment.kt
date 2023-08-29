package com.yamahaw.xgbuddy.ui.voiceselect

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.yamahaw.xgbuddy.R
import com.yamahaw.xgbuddy.adapter.VoiceListAdapter
import com.yamahaw.xgbuddy.data.voiceselect.InstrumentGroup
import com.yamahaw.xgbuddy.data.voiceselect.VoiceListCategory
import com.yamahaw.xgbuddy.data.xg.SFXNormalVoice
import com.yamahaw.xgbuddy.data.xg.drum.XGDrumKit
import com.yamahaw.xgbuddy.data.xg.XGNormalVoice
import com.yamahaw.xgbuddy.databinding.FragmentVoiceSelectionDialogBinding
import com.yamahaw.xgbuddy.viewmodel.QS300ViewModel

class VoiceSelectionDialogFragment(var listener: OnVoiceItemSelectedListener) : DialogFragment() {

    private val qs300ViewModel: QS300ViewModel by activityViewModels()

    private val binding: FragmentVoiceSelectionDialogBinding by lazy {
        FragmentVoiceSelectionDialogBinding.inflate(layoutInflater)
    }

    private lateinit var voiceListAdapter: VoiceListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initAdapter()
        binding.rvVoiceSelect.apply {
            adapter = voiceListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        binding.tvCurrentVoice.text = arguments?.getString(ARG_START_VOICE) ?: ""
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCategoryButtons()
        binding.bVoiceSelClose.setOnClickListener { dismiss() }
    }

    override fun onResume() {
        super.onResume()
        val width = resources.getDimensionPixelSize(R.dimen.voice_select_dialog_width)
        val height = resources.getDimensionPixelSize(R.dimen.voice_select_dialog_height)
        dialog?.window?.let {
            it.setLayout(width, height)
            it.setBackgroundDrawableResource(R.drawable.popup_bg)
        }
        startAnimators()
    }

    private fun startAnimators() {
        // Y Rotation
        ValueAnimator.ofFloat(0f, 359f).apply {
            duration = 4000
            addUpdateListener {
                binding.ivVoiceIcon.rotationY = it.animatedValue as Float
            }
            repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
            start()
        }
    }

    private fun initAdapter() {
        voiceListAdapter = VoiceListAdapter(
            buildCompleteVoiceList()
        ) { index, category, voiceName ->
            binding.tvCurrentVoice.text = voiceName
            listener.onVoiceItemSelected(index, category)
        }
    }

    private fun buildCompleteVoiceList(): List<Any> = mutableListOf<Any>().apply {
        addAll(XGNormalVoice.values())
        addAll(XGDrumKit.values())
        addAll(SFXNormalVoice.values())
        addAll(qs300ViewModel.userPresets.values)
        addAll(qs300ViewModel.presets)
    }.toList()

    private fun setupCategoryButtons() {
        val isQSExclusive = arguments?.getBoolean(ARG_QS_EXCLUSIVE) ?: false
        if (isQSExclusive) {
            binding.bXGVoice.visibility = View.GONE
            binding.bXGDrum.visibility = View.GONE
            binding.bXGSfx.visibility = View.GONE
        }
        binding.bgVoiceCategory.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                updateAdapterCategory(checkedId)
                updateFilterButtons(checkedId)
            }
        }
        binding.bgXGFilter.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                val group = xgFilterMap[checkedId]
                if (group == null) {
                    voiceListAdapter.updateCategory(VoiceListCategory.XG_NORMAL)
                } else {
                    voiceListAdapter.filterByInstrumentGroup(group)
                }
            }
        }
        binding.bgQSFilter.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                val regex = qs300FilterMap[checkedId]
                if (regex == null) {
                    voiceListAdapter.updateCategory(VoiceListCategory.QS300)
                } else {
                    voiceListAdapter.filterByAlphabet(regex)
                }
            }
        }
        setInitialCategory()
    }

    private fun setInitialCategory() {
        val startCategoryId = arguments?.getInt(ARG_START_CATEGORY) ?: 0
        if (startCategoryId != 0 && startCategoryId != CATEGORY_ID_NORMAL) {
            binding.bgVoiceCategory.check(startCategoryId)
        } else {
            // Since it is checked by default, the onCheckChange listener may not be called, so
            //  set visibility of filter buttons explicitly
            if (binding.bgVoiceCategory.checkedButtonId == binding.bXGVoice.id) {
                binding.svQSFilter.visibility = View.GONE
                binding.svXGFilter.visibility = View.VISIBLE
                updateAdapterCategory(CATEGORY_ID_NORMAL)
            } else {
                binding.bgVoiceCategory.check(binding.bXGVoice.id)
            }
        }
    }

    private fun updateFilterButtons(categoryId: Int) {
        when (categoryId) {
            CATEGORY_ID_NORMAL -> {
                binding.svQSFilter.visibility = View.GONE
                binding.svXGFilter.visibility = View.VISIBLE
                binding.bgXGFilter.check(R.id.bSecAll)
            }
            CATEGORY_ID_QS300 -> {
                binding.svXGFilter.visibility = View.GONE
                binding.svQSFilter.visibility = View.VISIBLE
                binding.bgQSFilter.check(R.id.bQSAll)
            }
            else -> {
                binding.svQSFilter.visibility = View.GONE
                binding.svXGFilter.visibility = View.GONE
            }
        }
    }

    private fun updateAdapterCategory(categoryId: Int) {
        val category = when (categoryId) {
            CATEGORY_ID_NORMAL -> VoiceListCategory.XG_NORMAL
            CATEGORY_ID_XGDRUM -> VoiceListCategory.XG_DRUM
            CATEGORY_ID_SFX -> VoiceListCategory.SFX
            CATEGORY_ID_QS300 -> VoiceListCategory.QS300
            CATEGORY_ID_QS300_USER -> VoiceListCategory.QS300_USER
            else -> null
        }
        category?.let { voiceListAdapter.updateCategory(it) }
    }

    companion object {
        const val TAG = "VoiceSelectionDialogFragment"
        const val ARG_START_CATEGORY = "argStartCategoryId"
        const val ARG_START_VOICE = "argStartVoice"
        const val ARG_QS_EXCLUSIVE = "argQSExclusive"
        const val CATEGORY_ID_NORMAL = R.id.bXGVoice
        const val CATEGORY_ID_XGDRUM = R.id.bXGDrum
        const val CATEGORY_ID_SFX = R.id.bXGSfx
        const val CATEGORY_ID_QS300 = R.id.bQs300
        const val CATEGORY_ID_QS300_USER = R.id.bQs300User
        private val qs300FilterMap: Map<Int, String> = mapOf(
            R.id.bFiltSpec to "[^a-zA-Z0-9]",
            R.id.bFiltNum to "[0-9]",
            R.id.bFiltA to "[aA]",
            R.id.bFiltB to "[bB]",
            R.id.bFiltC to "[cC]",
            R.id.bFiltD to "[dD]",
            R.id.bFiltE to "[eE]",
            R.id.bFiltF to "[fF]",
            R.id.bFiltG to "[gG]",
            R.id.bFiltH to "[hH]",
            R.id.bFiltI to "[iI]",
            R.id.bFiltJ to "[jJ]",
            R.id.bFiltK to "[kK]",
            R.id.bFiltL to "[lL]",
            R.id.bFiltM to "[mM]",
            R.id.bFiltN to "[nN]",
            R.id.bFiltO to "[oO]",
            R.id.bFiltP to "[pP]",
            R.id.bFiltQ to "[qQ]",
            R.id.bFiltR to "[rR]",
            R.id.bFiltS to "[sS]",
            R.id.bFiltT to "[tT]",
            R.id.bFiltU to "[uU]",
            R.id.bFiltV to "[vV]",
            R.id.bFiltW to "[wW]",
            R.id.bFiltX to "[xX]",
            R.id.bFiltY to "[yY]",
            R.id.bFiltZ to "[zZ]"
        )
        private val xgFilterMap: Map<Int, InstrumentGroup> = mapOf(
            R.id.bFiltPiano to InstrumentGroup.PIANO,
            R.id.bFiltChromPerc to InstrumentGroup.CHROMATIC_PERC,
            R.id.bFiltOrgan to InstrumentGroup.ORGAN,
            R.id.bFiltGuitar to InstrumentGroup.GUITAR,
            R.id.bFiltBass to InstrumentGroup.BASS,
            R.id.bFiltStrings to InstrumentGroup.STRINGS,
            R.id.bFiltEnsemble to InstrumentGroup.ENSEMBLE,
            R.id.bFiltBrass to InstrumentGroup.BRASS,
            R.id.bFiltReed to InstrumentGroup.REED,
            R.id.bFiltPipe to InstrumentGroup.PIPE,
            R.id.bFiltSynLd to InstrumentGroup.SYN_LEAD,
            R.id.bFiltSynPad to InstrumentGroup.SYN_PAD,
            R.id.bFiltSynFX to InstrumentGroup.SYN_EFFECTS,
            R.id.bFiltEthnic to InstrumentGroup.ETHNIC,
            R.id.bFiltPerc to InstrumentGroup.PERCUSSIVE,
            R.id.bFiltSFX to InstrumentGroup.SFX
        )
    }
}