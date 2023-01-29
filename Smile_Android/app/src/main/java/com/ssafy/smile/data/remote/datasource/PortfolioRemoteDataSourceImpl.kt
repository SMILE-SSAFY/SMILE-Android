package com.ssafy.smile.data.remote.datasource

import com.ssafy.smile.data.remote.model.ArticleResponseDto
import com.ssafy.smile.data.remote.model.PortfolioResponseDto
import com.ssafy.smile.data.remote.service.PortfolioApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class PortfolioRemoteDataSourceImpl(private val portfolioApiService: PortfolioApiService): PortfolioRemoteDataSource {
    override suspend fun getPortfolio(photographerId: Long): Response<PortfolioResponseDto> {
        return portfolioApiService.getPortfolio(photographerId)
    }

    override suspend fun getArticles(photographerId: Long): Response<ArticleResponseDto> {
        return portfolioApiService.getArticles(photographerId)
    }
    override suspend fun uploadPost(latitude: Float, longitude: Float,
                                    detailAddress: String, category: String, images : List<MultipartBody.Part>): Response<Any>{
        return portfolioApiService.uploadPost(latitude, longitude, detailAddress, category, images)
    }

}