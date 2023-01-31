package com.ssafy.smile.data.remote.datasource

import com.ssafy.smile.data.remote.model.PortfolioResponseDto
import com.ssafy.smile.data.remote.model.PostListResponseDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

interface PortfolioRemoteDataSource {
    suspend fun getPortfolio(photographerId: Long): Response<PortfolioResponseDto>
    suspend fun getPosts(photographerId: Long): Response<ArrayList<PostListResponseDto>>
    suspend fun uploadPost(latitude: Double, longitude: Double,
                           detailAddress: String, category: String, images : List<MultipartBody.Part>): Response<Any>

}