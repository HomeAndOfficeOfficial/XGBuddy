package com.example.xgbuddy.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.xgbuddy.adapter.FileBrowserRecyclerAdapter
import com.example.xgbuddy.data.FileType
import com.example.xgbuddy.R
import com.example.xgbuddy.databinding.FragmentFileBrowserBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter

@AndroidEntryPoint
class FileBrowserFragment : DialogFragment(), FileBrowserRecyclerAdapter.OnItemClickListener {

    private val midiViewModel: MidiViewModel by activityViewModels()

    private lateinit var fileAdapter: FileBrowserRecyclerAdapter
    private lateinit var binding: FragmentFileBrowserBinding
    private lateinit var backCallback: OnBackPressedCallback
    private var currentDir = ""
    private var mode = READ
    private var setupJsonString = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        backCallback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            navigateUp()
        }
        backCallback.isEnabled = false
        fileAdapter = FileBrowserRecyclerAdapter(requireContext().fileList(), this)
        arguments?.let {
            mode = it.getInt(ARG_MODE)
            setupJsonString = it.getString(ARG_JSON) ?: ""
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFileBrowserBinding.inflate(layoutInflater)
        binding.apply {
            upListItem.apply {
                tvFileName.text = "..."
                ivFileIcon.setImageResource(R.mipmap.ic_folder)
                root.setOnClickListener { navigateUp() }
            }
            if (mode == READ) {
                bSaveSetup.text = "Open"
                etSetupName.apply {
                    isFocusable = false
                    setOnTouchListener { _, _ -> true }
                }
            }
            bSaveSetup.setOnClickListener {
                if (mode == READ) {
                    loadSetup()
                } else {
                    saveSetup()
                }
            }
            bMultiCancel.setOnClickListener {
                fileAdapter.cancelMultiSelect()
                updateFileControlVisibility(false)
            }
            bDelete.setOnClickListener {
                deleteSelectedFiles()
            }
        }
        setupRecyclerView()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val width = resources.getDimensionPixelSize(R.dimen.file_browser_dialog_width)
        val height = resources.getDimensionPixelSize(R.dimen.file_browser_dialog_height)
        dialog?.window?.let {
            it.setLayout(width, height)
            it.setBackgroundDrawableResource(R.drawable.popup_bg)
        }
    }

    private fun setupRecyclerView() {
        val dividerItemDecoration =
            DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        binding.rvFiles.apply {
            adapter = fileAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(dividerItemDecoration)
        }
    }

    private fun openOrNavigate(fileName: String, fileType: FileType) {
        if (fileType == FileType.DIR) {
            val directoryFiles =
                File(requireContext().filesDir.path + "/" + currentDir + fileName).list()
            if (directoryFiles == null) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.file_browser_invalid_dir, fileName),
                    Toast.LENGTH_LONG
                ).show()
            } else {
                fileAdapter.setFiles(directoryFiles)
                if (fileName.isNotEmpty()) {
                    backCallback.isEnabled = true
                    currentDir += "$fileName/"
                }
                updateUpItem()
                updateBreadcrumb()
            }
        } else {
            if (fileAdapter.isMultiSelectOn) {
                fileAdapter.selectFile(fileName)
                updateSelectedCountText()
            } else {
                fileAdapter.selectFile(fileName)
                binding.etSetupName.setText(fileName)
                binding.bSaveSetup.isEnabled = true
            }
        }
    }

    private fun loadSetup() {
        val fileName = currentDir + binding.etSetupName.text.toString()
        val jsonString = try {
            InputStreamReader(requireContext().openFileInput(fileName)).let {
                val bufferedReader = BufferedReader(it)
                val stringBuilder = StringBuilder()
                var line = bufferedReader.readLine()
                while (line != null) {
                    stringBuilder.append(line)
                    line = bufferedReader.readLine()
                }
                bufferedReader.close()
                stringBuilder.toString()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Caught some exception: ${e.message}")
            ""
        }
        if (jsonString.isNotEmpty() && midiViewModel.readSetupJson(jsonString)) {
            dismiss()
        } else {
            AlertDialog.Builder(requireContext()).apply {
                setMessage("Couldn't read setup from file.")
                setTitle("JSON Error")
                setIcon(R.drawable.baseline_error_24)
                setNeutralButton("Close") { d, _ -> d.dismiss() }
                show()
            }
        }
    }

    private fun saveSetup() {
        try {
            var fileName = currentDir + binding.etSetupName.text.toString()
            if (fileName.takeLast(4) != ".xgb") {
                fileName += ".xgb"
            }
            OutputStreamWriter(
                requireContext()
                    .openFileOutput(fileName, Context.MODE_PRIVATE)
            )
                .apply {
                    write(setupJsonString)
                    close()
                }
        } catch (e: IOException) {
            Log.e(TAG, "Caught IOException: ${e.message}")
        }
        dismiss()
    }

    private fun updateBreadcrumb() {
        val openDirectories = currentDir.split("/").dropLast(1) // Last element is ""
        val newCrumbChildCount = openDirectories.size * 2 + 2
        if (newCrumbChildCount > binding.llBreadCrumb.childCount) {
            binding.llBreadCrumb.apply {
                addView(getBreadcrumbArrow())
                addView(getBreadcrumbTextView(openDirectories.last()))
            }
        } else {
            binding.llBreadCrumb.apply {
                removeViewAt(childCount - 1)
                removeViewAt(childCount - 1)
            }
        }
    }

    private fun getBreadcrumbArrow(): ImageView = ImageView(requireContext()).apply {
        setImageResource(R.drawable.baseline_keyboard_arrow_right_24)
    }

    private fun getBreadcrumbTextView(crumbText: String): TextView =
        TextView(requireContext()).apply {
            text = crumbText
        }

    private fun navigateUp() {
        val upDirectoryList = currentDir.split("/").dropLast(2)
        currentDir = if (upDirectoryList.isNotEmpty()) {
            upDirectoryList.joinToString("/", postfix = "/")
        } else {
            ""
        }
        backCallback.isEnabled = currentDir.isNotEmpty()
        openOrNavigate("", FileType.DIR)
    }

    private fun updateUpItem() {
        binding.upListItem.root.visibility = if (currentDir.isEmpty()) View.GONE else View.VISIBLE
    }

    // TODO: Remove in favor of navigation args
    companion object {
        const val TAG = "FileBrowserFragment"
        const val ARG_MODE = "arg_mode"
        const val ARG_JSON = "arg_json"
        const val READ = 0
        const val WRITE = 1
        fun newInstance(mode: Int, setupJson: String = ""): FileBrowserFragment {
            val argMode = if (mode == READ || mode == WRITE) {
                mode
            } else {
                READ
            }
            val args = Bundle().apply {
                putInt(ARG_MODE, argMode)
                putString(ARG_JSON, setupJson)
            }
            return FileBrowserFragment().apply {
                arguments = args
            }
        }
    }

    override fun onItemClicked(fileName: String, fileType: FileType) {
        openOrNavigate(fileName, fileType)
    }

    override fun onItemLongClicked(fileName: String) {
        fileAdapter.isMultiSelectOn = true
        fileAdapter.selectFile(fileName)
        updateSelectedCountText()
        updateFileControlVisibility(true)
    }

    private fun updateFileControlVisibility(isMultiSelectOn: Boolean) {
        val visibilityMulti = if (isMultiSelectOn) View.VISIBLE else View.GONE
        val visibilitySingle = if (isMultiSelectOn) View.GONE else View.VISIBLE
        val backgroundColor =
            if (isMultiSelectOn) Color.parseColor("#388E3C") else Color.TRANSPARENT
        binding.apply {
            bAddDir.visibility = visibilitySingle
            bDelete.visibility = visibilityMulti
            bMove.visibility = visibilityMulti
            tvMultiCount.visibility = visibilityMulti
            bMultiCancel.visibility = visibilityMulti
            clFileControls.setBackgroundColor(backgroundColor)
            etSetupName.isEnabled = !isMultiSelectOn
            bSaveSetup.isEnabled = !isMultiSelectOn
        }
    }

    private fun updateSelectedCountText() {
        binding.tvMultiCount.text = buildString {
            append(fileAdapter.selectedIndices.size)
            append(" Selected")
        }
    }

    private fun deleteSelectedFiles() {
        val fileNames = mutableListOf<String>()
        fileAdapter.selectedIndices.forEach {
            fileNames.add(fileAdapter.setupFiles[it])
        }
        fileNames.forEach {
            val path = currentDir + it
            requireContext().deleteFile(path)
        }
        fileAdapter.onFilesDeleted()
        updateFileControlVisibility(false)
    }
}