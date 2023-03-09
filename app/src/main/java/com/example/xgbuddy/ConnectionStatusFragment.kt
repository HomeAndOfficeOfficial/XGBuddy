package com.example.xgbuddy

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ConnectionStatusFragment : DialogFragment() {

    @Inject
    lateinit var midiSession: MidiSession

    private lateinit var recyclerAdapter: ConnectedDeviceRecyclerAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var disconnectAlertContainer: ConstraintLayout
    private lateinit var deviceListContainer: ConstraintLayout
    private lateinit var ivDisconnect: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_connection_status, container, false)
        recyclerView = view.findViewById(R.id.rvConnectedDevices)
        disconnectAlertContainer = view.findViewById(R.id.disconnectAlertContainer)
        deviceListContainer = view.findViewById(R.id.deviceListContainer)
        ivDisconnect = view.findViewById(R.id.ivDisconnected)
        recyclerAdapter = ConnectedDeviceRecyclerAdapter(midiSession, listOf())
        recyclerView.apply {
            adapter = recyclerAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        midiSession.connectedDeviceList.observe(viewLifecycleOwner) { devices ->
            if (devices.isEmpty()) {
                disconnectAlertContainer.visibility = View.VISIBLE
                deviceListContainer.visibility = View.GONE
            } else {
                deviceListContainer.visibility = View.VISIBLE
                disconnectAlertContainer.visibility = View.GONE
                recyclerAdapter.updateDeviceList(devices.toList())
            }
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        val width = resources.getDimensionPixelSize(R.dimen.connection_dialog_width)
        val height = resources.getDimensionPixelSize(R.dimen.connection_dialog_height)
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
                ivDisconnect.rotationY = it.animatedValue as Float
            }
            repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
            start()
        }

        // Alpha Oscillation
        ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 1000
            addUpdateListener {
                ivDisconnect.alpha = it.animatedValue as Float
            }
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE
            interpolator = LinearInterpolator()
            start()
        }
    }

    companion object {
        const val TAG = "ConnectionStatusFragment"
    }
}