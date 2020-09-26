package com.mhmdawad.wallpaperaty.ui.recentImages

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.mhmdawad.wallpaperaty.source.WallpaperRepository

class RecentViewModel @ViewModelInject constructor(private val wallpaperRepository: WallpaperRepository): ViewModel(){

    companion object{
        private const val DEFAULT_QUERY = "cats"
    }
    private val defaultQuery = MutableLiveData(DEFAULT_QUERY)

    val wallpapers = defaultQuery.switchMap {query->
        wallpaperRepository.getSearchPhotos(query).cachedIn(viewModelScope)
    }

    fun searchQuery(query: String = DEFAULT_QUERY){
        defaultQuery.value = query
    }


}