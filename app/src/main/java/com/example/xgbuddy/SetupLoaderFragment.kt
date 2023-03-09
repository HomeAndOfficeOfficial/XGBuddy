package com.example.xgbuddy

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

// Displays options to load setup, start new XG or new QS300 setup

@AndroidEntryPoint
class SetupLoaderFragment : Fragment() {

    @Inject
    lateinit var midiSession: MidiSession

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = layoutInflater.inflate(R.layout.fragment_setup_loader, container, false)
        view.findViewById<Button>(R.id.bNewQSSetup).setOnClickListener(this::createQSSetup)
        view.findViewById<Button>(R.id.bNewXGSetup).setOnClickListener(this::createXGSetup)
        view.findViewById<Button>(R.id.bLoadExisting).setOnClickListener(this::startFileBrowser)
        return view
    }

    private fun createQSSetup(v: View) {

    }

    private fun createXGSetup(v: View) {

    }

    private fun startFileBrowser(v: View) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
        }
        val activityResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    // Figure out how to get data from result
                }
            }
        activityResult.launch(intent)
    }
}