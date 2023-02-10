package com.ssafy.smile.domain.repository

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

interface PortfolioRepository {
    suspend fun getPortfolio(photographerId: Long)
    suspend fun getPosts(photographerId: Long)
    suspend fun uploadPost(latitude: Double, longitude: Double,
                           detailAddress: String, category: String, images : List<MultipartBody.Part>)
    suspend fun modifyPost(articleId : Long, latitude: Double, longitude: Double, detailAddress: String,
                           category: String, images: List<MultipartBody.Part>)
}