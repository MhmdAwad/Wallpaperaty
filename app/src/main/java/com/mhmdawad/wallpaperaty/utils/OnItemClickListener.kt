package com.mhmdawad.wallpaperaty.utils

import android.widget.ImageView
import com.mhmdawad.wallpaperaty.models.UnsplashPhoto

interface OnItemClickListener {
    fun onItemClicked(photo: UnsplashPhoto, imageView: ImageView)
}