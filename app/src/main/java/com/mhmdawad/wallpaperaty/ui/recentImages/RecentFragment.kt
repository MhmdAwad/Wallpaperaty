package com.mhmdawad.wallpaperaty.ui.recentImages

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.mhmdawad.wallpaperaty.R
import com.mhmdawad.wallpaperaty.models.UnsplashPhoto
import com.mhmdawad.wallpaperaty.source.EMPTY_LIST
import com.mhmdawad.wallpaperaty.utils.OnItemClickListener
import com.mhmdawad.wallpaperaty.utils.changeMode
import com.mhmdawad.wallpaperaty.utils.clearNoLimitFlag
import com.mhmdawad.wallpaperaty.utils.getLastThemeMode
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_recent.*


@AndroidEntryPoint
class RecentFragment : Fragment(R.layout.fragment_recent), OnItemClickListener {

    private val viewModel by viewModels<RecentViewModel>()
    private val wallpaperAdapter by lazy { WallpaperAdapter(this) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clearNoLimitFlag()
        checkThemeMode()
        initRecyclerView()
        refreshListener()
        observeObservers()
        loadStatesListener()
        searchListener(view.context)

    }

    private fun checkThemeMode() {
        val isLight = requireActivity().getLastThemeMode()
        themeModeButton.setImageResource(
            if (isLight)
                R.drawable.ic_light_24
            else
                R.drawable.ic_dark_24
        )
        themeModeButton.setOnClickListener {
            changeMode(requireActivity().getPreferences(Context.MODE_PRIVATE))
            requireActivity().recreate()
        }
    }

    private fun searchListener(context: Context) {
        wallpaperSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH && wallpaperSearch.text.isNotEmpty()) {
                viewModel.searchQuery(wallpaperSearch.text.toString())
                closeSearch(context)
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    private fun closeSearch(context: Context) {
        wallpaperSearch.text.clear()
        wallpaperSearch.clearFocus()
        val input = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        input.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

    private fun loadStatesListener() {
        wallpaperAdapter.addLoadStateListener { state ->
            with(state.source.refresh) {
                recentSwipeRefresh.isRefreshing = this is LoadState.Loading
                recentImagesRV.isVisible = this is LoadState.NotLoading
                searchLayout.isVisible = this is LoadState.Error && this.error.message == EMPTY_LIST
                noInternetLayout.isVisible = this is LoadState.Error && this.error.message != EMPTY_LIST

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
            if(searchLayout.isVisible)
                viewModel.searchQuery("all")
            else
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

    override fun onItemClicked(photo: UnsplashPhoto, imageView: ImageView) {
        val extras = FragmentNavigatorExtras(
            imageView to photo.urls.small
        )
        val action = RecentFragmentDirections.actionRecentFragmentToDetailsImageFragment(photo)
        findNavController().navigate(action, extras)
    }
}