package com.example.xgbuddy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.xgbuddy.databinding.FragmentFileBrowserBinding
import java.io.File

class FileBrowserFragment : Fragment() {

    private lateinit var fileAdapter: FileBrowserRecyclerAdapter
    private lateinit var binding: FragmentFileBrowserBinding
    private lateinit var backCallback: OnBackPressedCallback
    private var currentDir = ""
    private var mode = READ

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        backCallback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            navigateUp()
        }
        backCallback.isEnabled = false
        fileAdapter = FileBrowserRecyclerAdapter(requireContext().fileList(), this::openOrNavigate)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFileBrowserBinding.inflate(layoutInflater)
        binding.upListItem.bFileItem.apply {
            text = "..."
            setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_folder, 0, 0, 0)
            setOnClickListener { navigateUp() }
        }
        setupRecyclerView()
        return binding.root
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
            if (mode == READ) {
                loadSession(fileName)
            } else {
                overwriteSession(fileName)
            }
        }
    }

    private fun loadSession(setupName: String) {

    }

    private fun overwriteSession(setupName: String) {

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
        const val READ = 0
        const val WRITE = 1
        fun newInstance(mode: Int): FileBrowserFragment {
            val argMode = if (mode == READ || mode == WRITE) {
                mode
            } else {
                READ
            }
            val args = Bundle().apply {
                putInt(ARG_MODE, argMode)
            }
            return FileBrowserFragment().apply {
                arguments = args
            }
        }
    }
}