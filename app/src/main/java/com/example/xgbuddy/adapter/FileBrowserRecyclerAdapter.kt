package com.example.xgbuddy.adapter

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.xgbuddy.data.FileType
import com.example.xgbuddy.R

class FileBrowserRecyclerAdapter(files: Array<String>, val listener: OnItemClickListener) :
    RecyclerView.Adapter<FileBrowserRecyclerAdapter.ViewHolder>() {

    var setupFiles: List<String> = filterFiles(files)
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

    private fun getFileType(file: String): FileType {
        if (!file.contains(".")) {
            return FileType.DIR
        }
        val ext = file.substringAfter(".")
        return if (ext == "xgb") FileType.XGB else FileType.DIR
    }

    private fun filterFiles(files: Array<String>): List<String> =
        files.filter {
            !it.contains(".") || with(it.substringAfter(".")) {
                this == "xgb"
            }
        }

    interface OnItemClickListener {
        fun onItemClicked(fileName: String, fileType: FileType)
        fun onItemLongClicked(fileName: String)
    }

    companion object {
        private val selectedColor = Color.parseColor("#8081C784")
        private val transparent = Color.parseColor("#00000000")
    }
}