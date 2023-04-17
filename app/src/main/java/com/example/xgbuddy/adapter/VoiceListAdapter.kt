package com.example.xgbuddy.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.xgbuddy.R

class VoiceListAdapter(
    var voiceList: List<String>,
    private val listener: OnVoiceItemClickListener
) :
    RecyclerView.Adapter<VoiceListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvVoice = itemView.findViewById<TextView>(R.id.tvVoiceItem)

        fun bind(position: Int, voiceName: String, listener: OnVoiceItemClickListener) {
            tvVoice.text = voiceName
            itemView.setOnClickListener {
                listener.onVoiceItemClicked(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.voice_list_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = voiceList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position, voiceList[position], listener)
    }

    fun interface OnVoiceItemClickListener {
        fun onVoiceItemClicked(position: Int)
    }
}