package com.yamahaw.xgbuddy.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.yamahaw.xgbuddy.R
import com.yamahaw.xgbuddy.data.xg.drum.DrumVoice
import com.yamahaw.xgbuddy.util.DataFormatUtil

class DrumVoiceRecyclerAdapter(
    private var selectedIndex: Int,
    var drumVoices: MutableList<DrumVoice>,
    val listener: OnDrumClickListener
) :
    RecyclerView.Adapter<DrumVoiceRecyclerAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val container: ConstraintLayout = itemView.findViewById(R.id.clDrumContainer)
        private val button: Button = itemView.findViewById(R.id.bDrumHit)
        private val tvVolume: TextView = itemView.findViewById(R.id.tvDrumLevel)
        private val tvPan: TextView = itemView.findViewById(R.id.tvDrumPan)
        private val tvReverb: TextView = itemView.findViewById(R.id.tvDrumReverb)
        private val tvChorus: TextView = itemView.findViewById(R.id.tvDrumChorus)

        fun bind(
            position: Int,
            drumVoice: DrumVoice,
            isSelected: Boolean,
            listener: OnDrumClickListener
        ) {
            container.apply {
                if (isSelected) {
                    setBackgroundResource(R.drawable.container_bg_shape_select)
                } else {
                    setBackgroundResource(R.drawable.container_bg_shape)
                }
                setOnClickListener { listener.onDrumClicked(position, false) }
            }
            button.apply {
                setOnClickListener { listener.onDrumClicked(position, true) }
                text = drumVoice.name
            }
            tvVolume.text = buildString {
                append("V: ")
                append(drumVoice.level)
            }
            tvPan.text = buildString {
                append("P: ")
                append(DataFormatUtil.panFormatter.format(drumVoice.pan.toInt()))
            }
            tvChorus.text = buildString {
                append("Ch: ")
                append(drumVoice.chorusSend)
            }
            tvReverb.text = buildString {
                append("Rv: ")
                append(drumVoice.reverbSend)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.drum_voice_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = drumVoices.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position, drumVoices[position], position == selectedIndex, listener)
    }

    fun updateSelection(position: Int) {
        if (position != selectedIndex) {
            val previous = selectedIndex
            selectedIndex = position
            notifyItemChanged(previous)
            notifyItemChanged(selectedIndex)
        }
    }

    fun updateValues(position: Int, drumVoice: DrumVoice) {
        drumVoices[position] = drumVoice
        notifyItemChanged(position)
    }

    fun interface OnDrumClickListener {
        fun onDrumClicked(position: Int, fromButtonClick: Boolean)
    }
}