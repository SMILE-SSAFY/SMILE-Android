package com.ssafy.smile.data.remote.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.data.remote.datasource.SearchRemoteDataSource
import com.ssafy.smile.data.remote.model.SearchPhotographerResponseDto
import com.ssafy.smile.domain.repository.SearchRepository
import com.ssafy.smile.presentation.base.BaseRepository

class SearchRepositoryImpl(private val searchRemoteDataSource: SearchRemoteDataSource): BaseRepository(), SearchRepository {
    private val _searchPhotographerLiveData = MutableLiveData<NetworkUtils.NetworkResponse<ArrayList<SearchPhotographerResponseDto>>>()
    val searchPhotographerLiveData: LiveData<NetworkUtils.NetworkResponse<ArrayList<SearchPhotographerResponseDto>>>
        get() = _searchPhotographerLiveData

    override suspend fun searchPhotographer(category: String) {
        safeApiCall(_searchPhotographerLiveData){
            searchRemoteDataSource.searchPhotographer(category)
        }
    }
}