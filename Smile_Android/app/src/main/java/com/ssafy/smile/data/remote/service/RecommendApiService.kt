package com.ssafy.smile.data.remote.service

import com.ssafy.smile.data.remote.model.PhotographerRecommendResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RecommendApiService {
    @GET("api/recommend")
    suspend fun getPhotographerRecommendInfo(@Query("address") address:String): Response<ArrayList<PhotographerRecommendResponseDto>>
}