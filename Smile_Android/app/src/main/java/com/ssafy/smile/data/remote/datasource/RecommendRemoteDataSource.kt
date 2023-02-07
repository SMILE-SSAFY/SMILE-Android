package com.ssafy.smile.data.remote.datasource

import com.ssafy.smile.data.remote.model.PhotographerRecommendResponseDto
import retrofit2.Response

interface RecommendRemoteDataSource {
    suspend fun getPhotographerRecommendInfo(address:String): Response<ArrayList<PhotographerRecommendResponseDto>>
}