package com.ssafy.smile.data.remote.datasource

import com.ssafy.smile.data.remote.model.ArticleResponseDto
import com.ssafy.smile.data.remote.model.PortfolioResponseDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

interface PortfolioRemoteDataSource {
    suspend fun getPortfolio(photographerId: Long): Response<PortfolioResponseDto>
    suspend fun getArticles(photographerId: Long): Response<ArticleResponseDto>
    suspend fun uploadPost(images: MutableMap<String, RequestBody>) : Response<Any>
    suspend fun uploadPost(latitude: Float, longitude: Float,
                           detailAddress: String, category: String, images : List<MultipartBody.Part>): Response<Any>
    suspend fun uploadPost(latitude: Float, longitude: Float,
                           detailAddress: String, category: String, images : HashMap<String, List<MultipartBody.Part>>) : Response<Any>
}