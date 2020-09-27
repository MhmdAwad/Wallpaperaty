package com.mhmdawad.wallpaperaty.ui.imageDetails

import android.Manifest
import android.app.DownloadManager
import android.app.WallpaperManager
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.mhmdawad.wallpaperaty.R
import com.mhmdawad.wallpaperaty.models.UnsplashPhoto
import com.mhmdawad.wallpaperaty.utils.addNoLimitFlag
import com.mhmdawad.wallpaperaty.utils.downloadImage
import com.mhmdawad.wallpaperaty.utils.loadImage
import com.mhmdawad.wallpaperaty.utils.onComplete
import kotlinx.android.synthetic.main.fragment_details_image.*
import java.io.IOException
import java.lang.Exception


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

    private fun checkRunTimePermission() {
        val writePermission = Manifest.permission.WRITE_EXTERNAL_STORAGE
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                writePermission
            ) != PackageManager.PERMISSION_GRANTED
        )
            requestPermissions(arrayOf(writePermission), 101)
        else
            downloadImage()
    }

    private fun downloadImage() {
        try {
            downloadImage(image.urls.regular)
            Toast.makeText(
                this.context,
                "Downloading..",
                Toast.LENGTH_SHORT
            ).show()
        } catch (e: Exception) {
            Toast.makeText(
                this.context,
                "Download Failed!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun downloadImageListener() {
        downloadFAB.setOnClickListener {
            checkRunTimePermission()
        }
    }

    private fun setImageWallpaper() {
        previewFAB.setOnClickListener {
            val bmpImg = (detailImage.drawable as TransitionDrawable).toBitmap()
            val wallManager =
                WallpaperManager.getInstance(requireActivity().applicationContext)
            try {
                wallManager.setBitmap(bmpImg)
                Toast.makeText(
                    this.context,
                    "Wallpaper Set Successfully!!",
                    Toast.LENGTH_SHORT
                ).show()
            } catch (e: IOException) {
                Toast.makeText(this.context, "Setting WallPaper Failed!!", Toast.LENGTH_SHORT)
                    .show()
            }

        }
    }

    private fun loadImage() {
        image = args.image
        detailImage.apply {
            transitionName = image.urls.small
            loadImage(image.urls.regular)
        }
    }

    private fun fabListener() {
        detailImageFAB.setOnClickListener {
            if (!downloadContainer.isVisible) {
                detailImageFAB.setImageResource(R.drawable.ic_close_24)
                downloadContainer.isVisible = true
                previewContainer.isVisible = true
            } else {
                detailImageFAB.setImageResource(R.drawable.ic_add_24)
                downloadContainer.isVisible = false
                previewContainer.isVisible = false
            }
        }
    }

    override fun onResume() {
        super.onResume()
        requireActivity().registerReceiver(onComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
    }

    override fun onStop() {
        super.onStop()
        requireActivity().unregisterReceiver(onComplete)
    }
}