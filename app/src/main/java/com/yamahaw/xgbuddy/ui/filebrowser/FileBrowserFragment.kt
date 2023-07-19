package com.yamahaw.xgbuddy.ui.filebrowser

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.yamahaw.xgbuddy.midi.MidiSession
import com.yamahaw.xgbuddy.adapter.FileBrowserRecyclerAdapter
import com.yamahaw.xgbuddy.data.buddy.FileType
import com.yamahaw.xgbuddy.R
import com.yamahaw.xgbuddy.databinding.FragmentFileBrowserBinding
import com.yamahaw.xgbuddy.viewmodel.MidiViewModel
import com.yamahaw.xgbuddy.util.MidiMessageUtility
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.*
import javax.inject.Inject

@AndroidEntryPoint
class FileBrowserFragment : DialogFragment(), FileBrowserRecyclerAdapter.OnItemClickListener {

    @Inject
    lateinit var midiSession: MidiSession
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
                    showUnsavedDialogOrLoad()
                } else {
                    showOverwriteDialogOrSave()
                }
            }
            bMultiCancel.setOnClickListener {
                fileAdapter.cancelMultiSelect()
                updateFileControlVisibility(false)
            }
            bDelete.setOnClickListener {
                deleteSelectedFiles()
            }
            bAddDir.setOnClickListener {
                showNewDirectoryDialog()
            }
            tvFileBrowserTitle.text = getString(
                if (mode == READ)
                    R.string.load_setup
                else
                    R.string.save_setup
            )
            ivSpinDisk.setImageResource(
                if (mode == READ)
                    R.drawable.baseline_folder_24_color
                else
                    R.drawable.baseline_save_24_color
            )
            etSetupName.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    bSaveSetup.isEnabled = s?.isNotEmpty() == true
                }

                override fun afterTextChanged(s: Editable?) {}
            })

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
        startAnimators()
    }

    private fun startAnimators() {
        // Y Rotation
        ValueAnimator.ofFloat(0f, 359f).apply {
            duration = 4000
            addUpdateListener {
                binding.ivSpinDisk.rotationY = it.animatedValue as Float
            }
            repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
            start()
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
        if (fileAdapter.isMultiSelectOn) {
            fileAdapter.selectFile(fileName)
            updateSelectedCountText()
        } else if (fileType == FileType.DIR) {
            val directoryFiles =
                File(getFilesDir() + fileName).list()
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
                binding.etSetupName.setText("")
                updateUpItem()
                updateBreadcrumb()
            }
        } else {
            fileAdapter.selectFile(fileName)
            binding.etSetupName.setText(fileName)
            binding.bSaveSetup.isEnabled = true
        }
    }

    private fun showUnsavedDialogOrLoad() {
        val shortName = binding.etSetupName.text.toString()
        val fileName = getFilesDir() + shortName
        AlertDialog.Builder(requireContext()).apply {
            setPositiveButton("Load Setup") { d, _ ->
                d.dismiss()
                loadSetup(fileName)
            }
            setNegativeButton("Cancel", null)
            setTitle(R.string.load_setup)
            setMessage("Load setup $shortName? Any unsaved work will be lost.")
            show()
        }
    }

    private fun loadSetup(fileName: String) {
        val jsonString = try {
            InputStreamReader(FileInputStream(File(fileName))).let {
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
        val setup = midiViewModel.readSetupJson(jsonString)
        if (setup != null) {
            // Trying this without using the buffer method as used in `sendBulkMessage`
            // If this doesn't work, first try using the buffer method, but will need to add
            // timestamps to that.
            // Also may need to run this in a coroutine since it will probably take a second or two
            // potentially causing UI thread to hang
            CoroutineScope(Dispatchers.IO).launch {
                midiSession.sendBulkMessages(MidiMessageUtility.getSetupSequence(setup))
            }
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

    private fun showOverwriteDialogOrSave() {
        var shortName = binding.etSetupName.text.toString()
        if (shortName.takeLast(4) != ".xgb") {
            shortName += ".xgb"
        }
        val fileName = getFilesDir() + shortName
        if (fileAdapter.setupFiles.contains(shortName)) {
            AlertDialog.Builder(requireContext()).apply {
                setNegativeButton("Cancel", null)
                setPositiveButton("Overwrite") { d, _ ->
                    d.dismiss()
                    saveSetup(fileName)
                }
                setTitle("Overwrite Existing Setup")
                setMessage("Are you sure you want to overwrite existing setup file $shortName?")
                show()
            }
        } else {
            saveSetup(fileName)
        }
    }

    private fun saveSetup(fileName: String) {
        try {
            OutputStreamWriter(
                FileOutputStream(File(fileName), false)
            )
                .apply {
                    write(setupJsonString)
                    close()
                }
        } catch (e: IOException) {
            Log.e(TAG, "Caught IOException: ${e.message}")
        }
        Toast.makeText(requireContext(), "Saved setup at $fileName", Toast.LENGTH_SHORT).show()
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
            val path = getFilesDir() + it
            File(path).deleteRecursively()
        }
        fileAdapter.onFilesDeleted()
        updateFileControlVisibility(false)
    }

    private fun showNewDirectoryDialog() {
        val addDirLayout = layoutInflater.inflate(R.layout.dialog_add_directory, null)
        val etDirName = addDirLayout.findViewById<EditText>(R.id.etDirName)
        AlertDialog.Builder(requireContext()).apply {
            setView(addDirLayout)
            setPositiveButton("Create") { _, _ ->
                addDirectory(etDirName.text.toString())
            }
            show()
        }
    }

    private fun addDirectory(dirName: String) {
        val directory = File(getFilesDir() + dirName)
        if (!directory.exists()) {
            directory.mkdir()
            File(getFilesDir()).list()?.let {
                fileAdapter.setFiles(it)
            }
        }
    }

    private fun getFilesDir(): String = requireContext().filesDir.path + "/" + currentDir
}