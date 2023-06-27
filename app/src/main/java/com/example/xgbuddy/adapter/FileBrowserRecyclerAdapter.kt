package com.example.xgbuddy.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.xgbuddy.data.FileType
import com.example.xgbuddy.R

class FileBrowserRecyclerAdapter(files: Array<String>, val listener: OnItemClickListener) :
    RecyclerView.Adapter<FileBrowserRecyclerAdapter.ViewHolder>() {

    var setupFiles: List<String> = filterFiles(files)

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val button: Button = itemView.findViewById(R.id.bFileItem)

        fun bind(name: String, type: FileType, listener: OnItemClickListener) {
            button.apply {
                text = name
                setCompoundDrawablesWithIntrinsicBounds(type.iconRes, 0, 0, 0)
                setOnClickListener { listener.onItemClicked(name, type) }
            }
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
        holder.bind(fileName, fileType, listener)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setFiles(files: Array<String>) {
        setupFiles = filterFiles(files)
        notifyDataSetChanged()
    }

    private fun getFileType(file: String): FileType {
        if (!file.contains(".")) {
            return FileType.DIR
        }
        val ext = file.substringAfter(".")
        return if (ext == "xbx") FileType.XBX else FileType.XBQ
    }

    private fun filterFiles(files: Array<String>): List<String> =
        files.filter {
            !it.contains(".") || with(it.substringAfter(".")) {
                this == "xgb" || this == "xbq"
            }
        }

    fun interface OnItemClickListener {
        fun onItemClicked(fileName: String, fileType: FileType)
    }
}