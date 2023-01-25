package com.ssafy.smile.domain.repository

import com.ssafy.smile.data.remote.model.ArticleResponseDto
import retrofit2.Response

interface PortfolioRepository {
    suspend fun getPortfolio(photographerId: Long)
    suspend fun getArticles(photographerId: Long)
}