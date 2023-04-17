package com.example.xgbuddy.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.xgbuddy.R
import com.example.xgbuddy.data.xg.XGNormalVoice
import kotlin.reflect.full.isSubclassOf

class VoiceListAdapter(
    voiceEntries: List<Any>,
    private val listener: OnVoiceItemClickListener
) :
    RecyclerView.Adapter<VoiceListAdapter.ViewHolder>() {

    private val voiceList: List<VoiceListEntry>
    private var filteredList: List<VoiceListEntry>? = null

    init {
        voiceList = List(voiceEntries.size) {
            VoiceListEntry(getVoiceName(voiceEntries[it]), voiceEntries[it]::class.java.name)
        }
    }

    enum class VoiceListCategory(val enumName: String) {
        XG_NORMAL(XGNormalVoice::class.java.name),
        XG_DRUM("xgrum"),
        SFX("sfx"),
        QS300("qs300voice")
    }

    data class VoiceListEntry(val name: String, val typeName: String)

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
        filteredList?.let {
            holder.bind(position, it[position].name, listener)
        }
    }

    private fun getVoiceName(item: Any) = if (item.isEnum()) {
        (item as Enum<*>).name
    } else {
        "Invalid Item"
    }

    private fun Any?.isEnum(): Boolean {
        return this != null && this::class.isSubclassOf(Enum::class)
    }

    fun interface OnVoiceItemClickListener {
        fun onVoiceItemClicked(position: Int)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateCategory(category: VoiceListCategory) {
        filteredList = voiceList.filter {
            it.typeName == category.enumName
        }
        notifyDataSetChanged()
    }
}