package com.yamahaw.xgbuddy.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yamahaw.xgbuddy.data.FileType
import com.yamahaw.xgbuddy.R

class FileBrowserRecyclerAdapter(files: Array<String>, val listener: OnItemClickListener) :
    RecyclerView.Adapter<FileBrowserRecyclerAdapter.ViewHolder>() {

    var setupFiles: MutableList<String> = filterFiles(files)
    var selectedIndices = mutableListOf<Int>()
    var isMultiSelectOn = false

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvFileName: TextView = itemView.findViewById(R.id.tvFileName)
        private val ivFileIcon: ImageView = itemView.findViewById(R.id.ivFileIcon)

        fun bind(
            name: String,
            type: FileType,
            isSelected: Boolean,
            listener: OnItemClickListener
        ) {
            itemView.apply {
                this.isSelected = isSelected
                setOnClickListener { listener.onItemClicked(name, type) }
                setOnLongClickListener {
                    listener.onItemLongClicked(name)
                    true
                }
            }
            tvFileName.text = name
            ivFileIcon.setImageResource(type.iconRes)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.file_list_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = setupFiles.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fileName = setupFiles[position]
        val fileType = getFileType(fileName)
        holder.bind(
            fileName,
            fileType,
            selectedIndices.contains(position),
            listener
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setFiles(files: Array<String>) {
        setupFiles = filterFiles(files)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun cancelMultiSelect() {
        isMultiSelectOn = false
        selectedIndices.clear()
        notifyDataSetChanged()
    }

    fun selectFile(fileName: String) {
        val index = setupFiles.indexOf(fileName)
        if ((isMultiSelectOn || selectedIndices.isEmpty())) {
            if (selectedIndices.contains(index)) {
                selectedIndices.remove(index)
            } else {
                selectedIndices.add(index)
            }
        } else {
            val previousSelection = selectedIndices.first()
            selectedIndices.clear()
            selectedIndices.add(index)
            notifyItemChanged(previousSelection)
        }
        notifyItemChanged(index)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun onFilesDeleted() {
        selectedIndices.forEach {
            setupFiles.removeAt(it)
        }
        selectedIndices.clear()
        isMultiSelectOn = false
        notifyDataSetChanged()
    }

    private fun getFileType(file: String): FileType {
        if (!file.contains(".")) {
            return FileType.DIR
        }
        val ext = file.substringAfter(".")
        return if (ext == "xgb") FileType.XGB else FileType.DIR
    }

    private fun filterFiles(files: Array<String>): MutableList<String> =
        files.filter {
            !it.contains(".") || with(it.substringAfter(".")) {
                this == "xgb"
            }
        }.toMutableList()

    interface OnItemClickListener {
        fun onItemClicked(fileName: String, fileType: FileType)
        fun onItemLongClicked(fileName: String)
    }
}