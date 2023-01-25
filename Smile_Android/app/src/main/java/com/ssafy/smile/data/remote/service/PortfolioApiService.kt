package com.ssafy.smile.data.remote.service

import com.ssafy.smile.data.remote.model.ArticleResponseDto
import com.ssafy.smile.data.remote.model.PortfolioResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface PortfolioApiService {
    @GET("/api/article/photographer/{photographerId}")
    suspend fun getPortfolio(
        @Path("photographerId") photographerId: Long): Response<PortfolioResponseDto>

    @GET("/api/article/list/{photographerId}")
    suspend fun getArticles(@Path("photographerId") photographerId: Long): Response<ArticleResponseDto>
}