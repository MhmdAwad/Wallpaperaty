package com.mhmdawad.wallpaperaty.utils

import android.content.Context
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