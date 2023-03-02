package com.example.xgbuddy

import android.media.midi.MidiDevice
import android.media.midi.MidiDeviceInfo
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton.OnCheckedChangeListener
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ConnectedDeviceRecyclerAdapter(
    private val midiSession: MidiSession, private val connectedDevices: List<MidiDeviceInfo>
) : RecyclerView.Adapter<ConnectedDeviceRecyclerAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cbInput: CheckBox = itemView.findViewById(R.id.ivInput)
        private val cbOutput: CheckBox = itemView.findViewById(R.id.ivOutput)
        private val tvDeviceName: TextView = itemView.findViewById(R.id.tvDeviceName)

        fun bind(
            name: String,
            isOutput: Boolean,
            isInput: Boolean,
            checkListener: OnCheckedChangeListener
        ) {
            tvDeviceName.text = name
            cbInput.apply {
                isChecked = isInput
                setOnCheckedChangeListener(checkListener)
            }
            cbOutput.apply {
                isChecked = isOutput
                setOnCheckedChangeListener(checkListener)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.connected_device_list_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = connectedDevices.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val name = connectedDevices[position].properties.getString(MidiDeviceInfo.PROPERTY_NAME)
            ?: "Unknown Device" // TODO: Replace with string res
        val isOutputChecked = midiSession.getOutputDevices().containsKey(name)
        val isInputChecked = midiSession.getInputDevices().containsKey(name)
        val listener =
            OnCheckedChangeListener { buttonView, isChecked ->
                if (buttonView.id == R.id.ivInput) {
                    if (isChecked) {
                        midiSession.openInputDevice(connectedDevices[position])
                    } else {
                        midiSession.removeInputDevice(connectedDevices[position])
                    }
                } else {
                    if (isChecked) {
                        midiSession.openOutputDevice(connectedDevices[position])
                    } else {
                        midiSession.removeOutputDevice(connectedDevices[position])
                    }
                }
            }
        holder.bind(name, isOutputChecked, isInputChecked, listener)
    }

}