package com.mhmdawad.wallpaperaty.ui.imageDetails

import android.Manifest
import android.app.AlertDialog
import android.app.DownloadManager
import android.app.WallpaperManager
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.TransitionDrawable
import android.os.Build
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.mhmdawad.wallpaperaty.R
import com.mhmdawad.wallpaperaty.models.UnsplashPhoto
import com.mhmdawad.wallpaperaty.utils.*
import kotlinx.android.synthetic.main.fragment_details_image.*
import kotlinx.android.synthetic.main.select_set_image_layout.view.*


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
            showCustomDialog(wallManager, bmpImg)
        }
    }

    private fun showCustomDialog(
        wallManager: WallpaperManager,
        bmpImg: Bitmap
    ) {
        val viewGroup: ViewGroup? = view?.findViewById(android.R.id.content)
        val dialogView: View =
            LayoutInflater.from(requireContext()).inflate(R.layout.select_set_image_layout, viewGroup, false)
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setView(dialogView)
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            dialogView.lockScreenContainer.setOnClickListener {
                tryRun {
                    wallManager.setBitmap(bmpImg, null, true, WallpaperManager.FLAG_LOCK)
                    alertDialog.dismiss()
                }
            }
            dialogView.isVisible = true
        }
        dialogView.wallpaperContainer.setOnClickListener {
            tryRun {
                wallManager.setBitmap(bmpImg)
                alertDialog.dismiss()
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