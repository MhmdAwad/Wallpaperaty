package com.mhmdawad.wallpaperaty.utils

import android.app.Activity
import android.app.DownloadManager
import android.content.*
import android.net.Uri
import android.os.Environment
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.mhmdawad.wallpaperaty.ui.NIGHT_MODE


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

fun Fragment.changeMode(mSharedPref: SharedPreferences) {
    val state = mSharedPref.getBoolean(NIGHT_MODE, true)
    val mEditor = mSharedPref.edit()
    mEditor.putBoolean(NIGHT_MODE, !state)
    mEditor.apply()
}

fun Activity.getLastThemeMode():Boolean{
    val mSharedPref = getPreferences(Context.MODE_PRIVATE)
    return mSharedPref?.getBoolean(NIGHT_MODE, true)?:true
}


fun Fragment.tryRun(func: () -> Unit){
    try {
        func()
        Toast.makeText(
            this.context,
            "Image Set Successfully.",
            Toast.LENGTH_SHORT
        ).show()
    }catch (e: Exception){
        Toast.makeText(
            this.context,
            "Setting Image Failed!!",
            Toast.LENGTH_SHORT
        ).show()
    }
}

var onComplete = object : BroadcastReceiver() {
    override fun onReceive(ctxt: Context, intent: Intent) {
        Toast.makeText(ctxt, "Downloaded", Toast.LENGTH_SHORT).show()
    }
}

