package com.mhmdawad.wallpaperaty.utils

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.android.synthetic.main.fragment_details_image.*

fun ImageView.loadImage(imageUrl: String){
    Glide.with(context)
        .load(imageUrl)
        .centerCrop()
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

fun Fragment.addNoLimitFlag(){
    requireActivity().window.setFlags(
        android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
    )
}
fun Fragment.clearNoLimitFlag(){
    requireActivity().window.clearFlags(
        android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
    )
}
fun Fragment.downloadImage(url: String){
    val request = DownloadManager.Request(Uri.parse(url))
    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
    val nameOfFile = url.split('/')
    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, nameOfFile[nameOfFile.size-1])
    val manager =
        context!!.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    manager.enqueue(request)
}