package com.mhmdawad.wallpaperaty.source

import androidx.paging.PagingSource
import com.mhmdawad.wallpaperaty.models.UnsplashPhoto
import com.mhmdawad.wallpaperaty.models.UnsplashResponse
import com.mhmdawad.wallpaperaty.source.network.WallpaperApi

const val FIRST_WALLPAPER_INDEX = 1

class WallpaperPagingSource(
    private val wallpaperApi: WallpaperApi,
    private val query: String
): PagingSource<Int, UnsplashPhoto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashPhoto> {
        val pos = params.key ?: FIRST_WALLPAPER_INDEX

        return try{
            val response = wallpaperApi.searchPhotos(query, pos, params.loadSize)
            val wallpapers = response.results
            LoadResult.Page(
                data = wallpapers,
                prevKey = if(pos == FIRST_WALLPAPER_INDEX) null else pos-1,
                nextKey = if(wallpapers.isEmpty()) null else pos+1
            )
        }catch (exception: Exception){
            LoadResult.Error(exception)
        }
    }
}