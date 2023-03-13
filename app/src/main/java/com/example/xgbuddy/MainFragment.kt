package com.example.xgbuddy

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.xgbuddy.databinding.FragmentMainBinding
import com.example.xgbuddy.util.MidiDataUtility
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {

    @Inject
    lateinit var dataUtility: MidiDataUtility

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater)
        binding.bNewQSSetup.setOnClickListener(this::createQSSetup)
        binding.bNewXGSetup.setOnClickListener(this::createXGSetup)
        binding.bLoadExisting.setOnClickListener(this::startFileBrowser)
        return binding.root
    }

    private fun createQSSetup(v: View) {
        dataUtility.getQS300Presets()
    }

    private fun createXGSetup(v: View) {

    }

    private fun startFileBrowser(v: View) {
        findNavController().navigate(MainFragmentDirections.actionMainFragmentToFileBrowserFragment())
    }

    companion object {
        private const val TAG = "MainFragment"
    }

}