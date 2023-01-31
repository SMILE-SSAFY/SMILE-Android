package com.ssafy.smile.domain.repository

import okhttp3.MultipartBody
import okhttp3.RequestBody

interface PortfolioRepository {
    suspend fun getPortfolio(photographerId: Long)
    suspend fun getPosts(photographerId: Long)
    suspend fun uploadPost(latitude: Float, longitude: Float,
                           detailAddress: String, category: String, images : List<MultipartBody.Part>)
}