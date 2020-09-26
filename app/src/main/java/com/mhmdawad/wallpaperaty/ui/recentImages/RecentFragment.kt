package com.mhmdawad.wallpaperaty.ui.recentImages

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.paging.LoadState
import com.mhmdawad.wallpaperaty.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_recent.*


@AndroidEntryPoint
class RecentFragment : Fragment(R.layout.fragment_recent) {

    private val viewModel by viewModels<RecentViewModel>()
    private val wallpaperAdapter by lazy { WallpaperAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        refreshListener()
        observeObservers()
        loadStatesListener()

    }

    private fun loadStatesListener() {
        wallpaperAdapter.addLoadStateListener { state ->
            with(state.source.refresh) {
                recentSwipeRefresh.isRefreshing = this is LoadState.Loading
                recentImagesRV.isVisible = this is LoadState.NotLoading

                if (this is LoadState.NotLoading &&
                    state.append.endOfPaginationReached &&
                    wallpaperAdapter.itemCount < 1
                ) {
                    recentImagesRV.isVisible = false
                }
            }
        }
    }

    private fun refreshListener() {
        recentSwipeRefresh.setOnRefreshListener {
            viewModel.searchQuery()
        }
    }

    private fun initRecyclerView() {
        recentImagesRV.apply {
            itemAnimator = null
            setHasFixedSize(true)
            adapter = wallpaperAdapter.withLoadStateHeaderAndFooter(
                header = RecentLoadStatesAdapter { wallpaperAdapter.retry() },
                footer = RecentLoadStatesAdapter { wallpaperAdapter.retry() }
            )
        }
    }

    private fun observeObservers() {
        viewModel.wallpapers.observe(viewLifecycleOwner) {
            wallpaperAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }
}