package com.yamahaw.xgbuddy.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yamahaw.xgbuddy.R
import com.yamahaw.xgbuddy.data.InstrumentGroup
import com.yamahaw.xgbuddy.data.qs300.QS300Preset
import com.yamahaw.xgbuddy.data.xg.XGNormalVoice
import com.yamahaw.xgbuddy.data.voiceselect.VoiceListCategory
import kotlin.reflect.full.isSubclassOf

class VoiceListAdapter(
    voiceEntries: List<Any>,
    private val listener: OnVoiceItemSelectedListener
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

    private var typedList: List<VoiceListEntry>? = null
    private var filteredList: List<VoiceListEntry>? = null
    private var selectedCategory: VoiceListCategory? = null

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
            listener: OnVoiceItemSelectedListener
        ) {
            tvVoice.text = voiceName
            itemView.setOnClickListener {
                listener.onVoiceItemSelected(position, category!!, voiceName)
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
            holder.bind(
                typedList!!.indexOf(it[position]),
                it[position].name,
                selectedCategory,
                listener
            )
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

    @SuppressLint("NotifyDataSetChanged")
    fun updateCategory(category: VoiceListCategory) {
        selectedCategory = category
        typedList = voiceList.filter {
            it.typeName == category.enumName
        }
        filteredList = typedList!!.toList()
        notifyDataSetChanged()

    }

    @SuppressLint("NotifyDataSetChanged")
    fun filterByInstrumentGroup(group: InstrumentGroup?) {
        filteredList = typedList!!.filter {
            it.instrumentGroup == group
        }
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filterByAlphabet(regex: String) {
        filteredList = typedList!!.filter {
            it.name.substring(0, 1).matches(regex.toRegex())
        }
        notifyDataSetChanged()
    }

    fun interface OnVoiceItemSelectedListener {
        fun onVoiceItemSelected(index: Int, category: VoiceListCategory, voiceName: String)
    }
}