package com.mhmdawad.wallpaperaty.source

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.mhmdawad.wallpaperaty.source.network.WallpaperApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WallpaperRepository @Inject constructor(
    private val wallpaperApi: WallpaperApi
) {

    fun getSearchPhotos(query: String)=
        Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { WallpaperPagingSource(wallpaperApi, query) }
        ).liveData

}