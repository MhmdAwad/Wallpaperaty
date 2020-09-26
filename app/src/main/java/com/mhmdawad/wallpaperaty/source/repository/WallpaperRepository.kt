package com.mhmdawad.wallpaperaty.source.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.mhmdawad.wallpaperaty.models.UnsplashPhoto

interface WallpaperRepository {

    fun getSearchPhotos(query: String): LiveData<PagingData<UnsplashPhoto>>
}