package com.mhmdawad.wallpaperaty.di

import com.mhmdawad.wallpaperaty.source.network.WallpaperApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(WallpaperApi.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideWallpaperApi(retrofit: Retrofit): WallpaperApi =
        retrofit.create(WallpaperApi::class.java)
}