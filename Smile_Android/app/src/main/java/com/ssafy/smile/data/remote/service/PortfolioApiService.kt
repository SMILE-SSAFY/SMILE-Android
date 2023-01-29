package com.ssafy.smile.data.remote.service

import com.ssafy.smile.common.util.Constants.BASE_URL_PORTFOLIO
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

import com.ssafy.smile.data.remote.model.ArticleResponseDto
import com.ssafy.smile.data.remote.model.PortfolioResponseDto
import retrofit2.Response

interface PortfolioApiService {
    @GET("$BASE_URL_PORTFOLIO/photographer/{photographerId}")
    suspend fun getPortfolio(
        @Path("photographerId") photographerId: Long
    ): Response<PortfolioResponseDto>

    @GET("$BASE_URL_PORTFOLIO/list/{photographerId}")
    suspend fun getArticles(@Path("photographerId") photographerId: Long): Response<ArticleResponseDto>

    @Multipart
    @POST(BASE_URL_PORTFOLIO)
    suspend fun uploadPost(@Part("latitude") latitude : Float,
                           @Part("longitude") longitude : Float,
                           @Part("detailAddress") detailAddress : String,
                           @Part("category") category: String,
                           @Part imageList : List<MultipartBody.Part>
    ): Response<Any>
}