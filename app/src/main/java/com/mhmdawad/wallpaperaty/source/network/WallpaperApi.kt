package com.mhmdawad.wallpaperaty.source.network

import com.mhmdawad.wallpaperaty.models.unsplashPhotos
import com.mhmdawad.wallpaperaty.utils.MyKey
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface WallpaperApi {

    companion object {
        const val BASE_URL = "https://api.unsplash.com/"
        const val CLIENT_ID = MyKey
    }

    @Headers("Accept-Version: v1", "Authorization: Client-ID $CLIENT_ID")
    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page:Int,
        @Query("per-page") perPage:Int
    ): unsplashPhotos
}