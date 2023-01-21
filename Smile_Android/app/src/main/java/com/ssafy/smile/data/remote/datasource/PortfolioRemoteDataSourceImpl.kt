package com.ssafy.smile.data.remote.datasource

import com.ssafy.smile.data.remote.model.PortfolioResponseDto
import com.ssafy.smile.data.remote.service.PortfolioApiService
import retrofit2.Response

class PortfolioRemoteDataSourceImpl(private val portfolioApiService: PortfolioApiService): PortfolioRemoteDataSource {
    override suspend fun getPortfolio(photographerId: Long): Response<PortfolioResponseDto> {
        return portfolioApiService.getPortfolio(photographerId)
    }
}