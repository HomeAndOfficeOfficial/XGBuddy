package com.example.xgbuddy.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.xgbuddy.R
import com.example.xgbuddy.data.InstrumentGroup
import com.example.xgbuddy.data.qs300.QS300Preset
import com.example.xgbuddy.data.xg.SFXNormalVoice
import com.example.xgbuddy.data.xg.XGDrumKit
import com.example.xgbuddy.data.xg.XGNormalVoice
import com.example.xgbuddy.util.EnumFinder.findBy
import kotlin.reflect.full.isSubclassOf

class VoiceListAdapter(
    voiceEntries: List<Any>,
    private val listener: OnVoiceItemClickListener
) :
    RecyclerView.Adapter<VoiceListAdapter.ViewHolder>() {

    private val voiceList: List<VoiceListEntry> = List(voiceEntries.size) {
        val group =
            if (voiceEntries[it] is XGNormalVoice)
                (voiceEntries[it] as XGNormalVoice).instrumentGroup
            else
                null
        VoiceListEntry(getVoiceName(voiceEntries[it]), voiceEntries[it]::class.java.name, group)
    }

    private var filteredList: List<VoiceListEntry>? = null
    private var selectedCategory: VoiceListCategory? = null

    enum class VoiceListCategory(val enumName: String) {
        XG_NORMAL(XGNormalVoice::class.java.name),
        XG_DRUM(XGDrumKit::class.java.name),
        SFX(SFXNormalVoice::class.java.name),
        QS300(QS300Preset::class.java.name)
    }

    data class VoiceListEntry(
        val name: String,
        val typeName: String,
        val instrumentGroup: InstrumentGroup? = null
    )

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvVoice = itemView.findViewById<TextView>(R.id.tvVoiceItem)

        fun bind(
            position: Int,
            voiceName: String,
            category: VoiceListCategory?,
            listener: OnVoiceItemClickListener
        ) {
            tvVoice.text = voiceName
            itemView.setOnClickListener {
                listener.onVoiceItemClicked(position, category!!)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.voice_list_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = filteredList?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        filteredList?.let {
            holder.bind(position, it[position].name, selectedCategory, listener)
        }
    }

    private fun getVoiceName(item: Any) = if (item.isEnum()) {
        (item as Enum<*>).name
    } else if (item is QS300Preset) {
        item.name
    } else {
        "Invalid Item"
    }

    private fun Any?.isEnum(): Boolean {
        return this != null && this::class.isSubclassOf(Enum::class)
    }

    fun interface OnVoiceItemClickListener {
        fun onVoiceItemClicked(position: Int, category: VoiceListCategory)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateCategory(category: VoiceListCategory) {
        selectedCategory = category
        filteredList = voiceList.filter {
            it.typeName == category.enumName
        }
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filterByInstrumentGroup(group: InstrumentGroup?) {
        filteredList = voiceList.filter {
            it.typeName == selectedCategory!!.enumName && it.instrumentGroup == group
        }
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filterByAlphabet(regex: String) {
        filteredList = voiceList.filter {
            it.typeName == selectedCategory!!.enumName && it.name.substring(0, 1)
                .matches(regex.toRegex())
        }
        notifyDataSetChanged()
    }
}