package com.mhmdawad.wallpaperaty.ui.recentImages

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.mhmdawad.wallpaperaty.R
import com.mhmdawad.wallpaperaty.models.UnsplashPhoto
import kotlinx.android.synthetic.main.main_image_layout_rv.view.*


class WallpaperAdapter :
    PagingDataAdapter<UnsplashPhoto, WallpaperAdapter.PhotoViewHolder>(WALLPAPER_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder =
        PhotoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.main_image_layout_rv, parent, false))


    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    class PhotoViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(photo: UnsplashPhoto)  =with(itemView) {
                Glide.with(this)
                    .load(photo.urls.small)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_broken_image_24)
                    .into(itemImage)

        }
    }

    companion object {
        private val WALLPAPER_COMPARATOR = object : DiffUtil.ItemCallback<UnsplashPhoto>() {
            override fun areItemsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto) =
                oldItem == newItem
        }
    }
}