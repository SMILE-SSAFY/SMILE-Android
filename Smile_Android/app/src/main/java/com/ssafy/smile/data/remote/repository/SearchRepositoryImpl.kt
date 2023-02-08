package com.ssafy.smile.data.remote.repository

import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.common.view.sources.SingleLiveData
import com.ssafy.smile.data.remote.datasource.SearchRemoteDataSource
import com.ssafy.smile.data.remote.model.SearchPhotographerResponseDto
import com.ssafy.smile.data.remote.model.SearchPostResponseDto
import com.ssafy.smile.domain.repository.SearchRepository
import com.ssafy.smile.presentation.base.BaseRepository

class SearchRepositoryImpl(private val searchRemoteDataSource: SearchRemoteDataSource): BaseRepository(), SearchRepository {
    private val _searchPhotographerLiveData = SingleLiveData<NetworkUtils.NetworkResponse<ArrayList<SearchPhotographerResponseDto>>>(null)
    val searchPhotographerLiveData: SingleLiveData<NetworkUtils.NetworkResponse<ArrayList<SearchPhotographerResponseDto>>>
        get() = _searchPhotographerLiveData

    private val _searchPostLiveData = SingleLiveData<NetworkUtils.NetworkResponse<ArrayList<SearchPostResponseDto>>>(null)
    val searchPostLiveData: SingleLiveData<NetworkUtils.NetworkResponse<ArrayList<SearchPostResponseDto>>>
        get() = _searchPostLiveData

    override suspend fun searchPhotographer(categoryName: String) {
        safeApiCall(_searchPhotographerLiveData){
            searchRemoteDataSource.searchPhotographer(categoryName)
        }
    }

    override suspend fun searchPost(categoryName: String) {
        safeApiCall(_searchPostLiveData){
            searchRemoteDataSource.searchPost(categoryName)
        }
    }
}