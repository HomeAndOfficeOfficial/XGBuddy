package com.example.xgbuddy

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class FileBrowserRecyclerAdapter(val fileList: Array<String>): RecyclerView.Adapter<FileBrowserRecyclerAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int = fileList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}