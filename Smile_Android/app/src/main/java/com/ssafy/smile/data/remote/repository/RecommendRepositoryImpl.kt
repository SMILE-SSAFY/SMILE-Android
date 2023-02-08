package com.ssafy.smile.data.remote.repository

import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.common.view.sources.SingleLiveData
import com.ssafy.smile.data.remote.datasource.RecommendRemoteDataSource
import com.ssafy.smile.data.remote.model.PhotographerRecommendResponseDto
import com.ssafy.smile.domain.repository.RecommendRepository
import com.ssafy.smile.presentation.base.BaseRepository

class RecommendRepositoryImpl(private val recommendRemoteDataSource: RecommendRemoteDataSource): RecommendRepository, BaseRepository() {
    private val _photographerRecommendInfoLiveData = SingleLiveData<NetworkUtils.NetworkResponse<PhotographerRecommendResponseDto>>(null)
    val photographerRecommendInfoLiveData: SingleLiveData<NetworkUtils.NetworkResponse<PhotographerRecommendResponseDto>>
        get() = _photographerRecommendInfoLiveData

    override suspend fun getPhotographerRecommendInfo(address: String) {
        safeApiCall(_photographerRecommendInfoLiveData){
            recommendRemoteDataSource.getPhotographerRecommendInfo(address)
        }
    }
}