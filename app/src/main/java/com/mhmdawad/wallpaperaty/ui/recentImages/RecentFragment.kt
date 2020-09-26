package com.mhmdawad.wallpaperaty.ui.recentImages

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.mhmdawad.wallpaperaty.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_recent.*


@AndroidEntryPoint
class RecentFragment : Fragment(R.layout.fragment_recent) {

    private val viewModel by viewModels<RecentViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val wallpaperAdapter = WallpaperAdapter()
        recentImagesRV.apply {
            setHasFixedSize(true)
            adapter = wallpaperAdapter
        }
        viewModel.wallpapers.observe(viewLifecycleOwner){
            wallpaperAdapter.submitData(viewLifecycleOwner.lifecycle, it )
        }
    }
}