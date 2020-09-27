package com.mhmdawad.wallpaperaty.utils

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.DownloadManager
import android.app.WallpaperManager
import android.content.*
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.mhmdawad.wallpaperaty.R
import com.mhmdawad.wallpaperaty.ui.NIGHT_MODE
import kotlinx.android.synthetic.main.select_set_image_layout.view.*


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
private fun Fragment.downloadImage(url: String){
    try {
        val request = DownloadManager.Request(Uri.parse(url))
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
        val nameOfFile = url.split('/')
        request.setDestinationInExternalPublicDir(
            Environment.DIRECTORY_DOWNLOADS,
            nameOfFile[nameOfFile.size - 1]
        )
        val manager =
            context!!.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        manager.enqueue(request)
        Toast.makeText(
            this.context,
            "Downloading..",
            Toast.LENGTH_SHORT
        ).show()
    }catch (e: Exception) {
        Toast.makeText(
            this.context,
            "Download Failed!",
            Toast.LENGTH_SHORT
        ).show()
    }
}

fun Fragment.checkPermissionAndDownload(imageUrl: String) {
    val writePermission = Manifest.permission.WRITE_EXTERNAL_STORAGE
    if (ContextCompat.checkSelfPermission(
            requireContext(),
            writePermission
        ) != PackageManager.PERMISSION_GRANTED
    )
        requestPermissions(arrayOf(writePermission), 101)
    else
        downloadImage(imageUrl)
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

fun Fragment.showSetImageDialog(
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
var onComplete = object : BroadcastReceiver() {
    override fun onReceive(ctxt: Context, intent: Intent) {
        Toast.makeText(ctxt, "Downloaded", Toast.LENGTH_SHORT).show()
    }
}

