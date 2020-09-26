package com.mhmdawad.wallpaperaty.ui.recentImages

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.mhmdawad.wallpaperaty.source.repository.WallpaperRepositoryImpl

class RecentViewModel @ViewModelInject constructor(private val wallpaperRepositoryImpl: WallpaperRepositoryImpl): ViewModel(){

    private var lastQuery = "all"
    private val defaultQuery = MutableLiveData(lastQuery)

    val wallpapers = defaultQuery.switchMap {query->
        wallpaperRepositoryImpl.getSearchPhotos(query).cachedIn(viewModelScope)
    }

    fun searchQuery(query: String = lastQuery){
        lastQuery = query
        defaultQuery.value = lastQuery
    }


}