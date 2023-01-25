package com.ssafy.smile.data.remote.datasource

import com.ssafy.smile.data.remote.model.ArticleResponseDto
import com.ssafy.smile.data.remote.model.PortfolioResponseDto
import retrofit2.Response

interface PortfolioRemoteDataSource {
    suspend fun getPortfolio(photographerId: Long): Response<PortfolioResponseDto>
    suspend fun getArticles(photographerId: Long): Response<ArticleResponseDto>
}