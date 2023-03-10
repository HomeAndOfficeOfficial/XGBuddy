package com.example.xgbuddy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class FileBrowserFragment : Fragment() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val files = requireContext().fileList()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    companion object {
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