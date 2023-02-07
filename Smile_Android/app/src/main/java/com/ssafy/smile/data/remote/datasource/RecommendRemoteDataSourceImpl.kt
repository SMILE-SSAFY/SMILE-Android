package com.ssafy.smile.data.remote.datasource

import com.ssafy.smile.data.remote.model.PhotographerRecommendResponseDto
import com.ssafy.smile.data.remote.service.RecommendApiService
import retrofit2.Response

class RecommendRemoteDataSourceImpl(private val recommendApiService: RecommendApiService): RecommendRemoteDataSource {
    override suspend fun getPhotographerRecommendInfo(address: String): Response<ArrayList<PhotographerRecommendResponseDto>> {
        return recommendApiService.getPhotographerRecommendInfo(address)
    }
}