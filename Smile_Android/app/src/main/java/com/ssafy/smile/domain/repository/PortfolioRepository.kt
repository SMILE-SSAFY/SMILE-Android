package com.ssafy.smile.domain.repository

interface PortfolioRepository {
    suspend fun getPortfolio(photographerId: Long)
    suspend fun getArticles(photographerId: Long)
}