package com.example.cryptolist.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptolist.R

class ProgressAdapter : LoadStateAdapter<ProgressAdapter.ProgressViewHolder>() {

    class ProgressViewHolder(itemView : ViewGroup) : RecyclerView.ViewHolder(itemView) {

        val progress : ProgressBar = itemView.findViewById(R.id.progress_circular)
        fun bind(loadState: LoadState) {
            progress.isVisible = loadState is LoadState.Loading
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ProgressViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.progress_item, parent, false)
        return ProgressViewHolder(itemView as ViewGroup)
    }

    override fun onBindViewHolder(holder: ProgressViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }
}
