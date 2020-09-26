package com.mhmdawad.wallpaperaty.ui.recentImages

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mhmdawad.wallpaperaty.R
import kotlinx.android.synthetic.main.recent_load_states.view.*

class RecentLoadStatesAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<RecentLoadStatesAdapter.LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder =
        LoadStateViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recent_load_states, parent,false))


    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class LoadStateViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        init {
            itemView.retryLoading.setOnClickListener {
                retry.invoke()
            }
        }

        fun bind(loadState: LoadState) =with(itemView){
                progress_bar.isVisible = loadState is LoadState.Loading
                layoutError.isVisible = loadState !is LoadState.Loading
        }
    }
}