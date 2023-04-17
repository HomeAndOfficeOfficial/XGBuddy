package com.example.xgbuddy.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.xgbuddy.R

class VoiceListAdapter : RecyclerView.Adapter<VoiceListAdapter.ViewHolder>() {

    var voiceList: List<String>? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(position: Int) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.voice_list_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = voiceList?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }
}