package com.mhmdawad.wallpaperaty.ui.imageDetails

import android.app.DownloadManager
import android.app.WallpaperManager
import android.content.IntentFilter
import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.mhmdawad.wallpaperaty.R
import com.mhmdawad.wallpaperaty.models.UnsplashPhoto
import com.mhmdawad.wallpaperaty.utils.*
import kotlinx.android.synthetic.main.fragment_details_image.*


class DetailsImageFragment : Fragment(R.layout.fragment_details_image) {

    private val args by navArgs<DetailsImageFragmentArgs>()
    private lateinit var image: UnsplashPhoto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addNoLimitFlag()
        fabListener()
        loadImage()
        setImageWallpaper()
        downloadImageListener()
    }



    private fun downloadImageListener() {
        downloadFAB.setOnClickListener {
            checkPermissionAndDownload(image.urls.regular)
        }
    }

    private fun setImageWallpaper() {
        previewFAB.setOnClickListener {
            val bmpImg = (detailImage.drawable as TransitionDrawable).toBitmap()
            val wallManager =
                WallpaperManager.getInstance(requireActivity().applicationContext)
            showSetImageDialog(wallManager, bmpImg)
        }
    }

    private fun loadImage() {
        image = args.image
        detailImage.apply {
            transitionName = image.urls.small
            loadImage(image.urls.regular)
        }
    }

    private fun hideAndShowFABs(state: Boolean) {
        detailImageFAB.setImageResource(
            if (state)
                R.drawable.ic_add_24
            else
                R.drawable.ic_close_24
        )
        downloadContainer.isVisible = !state
        previewContainer.isVisible = !state
    }

    private fun fabListener() {
        detailImageFAB.setOnClickListener {
            hideAndShowFABs(downloadContainer.isVisible)
        }
    }

    override fun onResume() {
        super.onResume()
        requireActivity().registerReceiver(
            onComplete,
            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        )
    }

    override fun onStop() {
        super.onStop()
        requireActivity().unregisterReceiver(onComplete)
    }
}