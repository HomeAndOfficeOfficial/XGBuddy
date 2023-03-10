package com.example.xgbuddy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        backCallback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            val upDirectoryList = currentDir.split("/").dropLast(2)
            currentDir = if (upDirectoryList.isNotEmpty()) {
                upDirectoryList.joinToString("/", postfix = "/")
            } else {
                ""
            }
            isEnabled = currentDir.isNotEmpty()
            openOrNavigate("", FileType.DIR)
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
        when (fileType) {
            FileType.DIR -> {
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
                }
            }
            FileType.XBX -> {}
            FileType.XBQ -> {}
        }
    }

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