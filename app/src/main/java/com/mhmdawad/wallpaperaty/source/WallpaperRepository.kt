package com.mhmdawad.wallpaperaty.source

import com.mhmdawad.wallpaperaty.source.network.WallpaperApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WallpaperRepository @Inject constructor(
    private val wallpaperApi: WallpaperApi
) {
}