package com.ssafy.smile.data.remote.service

import com.ssafy.smile.common.util.Constants.BASE_URL_ARTICLE
import okhttp3.MultipartBody
import retrofit2.http.*
import com.ssafy.smile.data.remote.model.PortfolioResponseDto
import com.ssafy.smile.data.remote.model.PostListResponseDto
import retrofit2.Response

interface PortfolioApiService {
    @GET("/api/article/photographer/{photographerId}")
    suspend fun getPortfolio(@Path("photographerId") photographerId: Long): Response<PortfolioResponseDto>

    @GET("/api/article/list/{photographerId}")
    suspend fun getPosts(@Path("photographerId") photographerId: Long): Response<ArrayList<PostListResponseDto>>

    @Multipart
    @POST(BASE_URL_ARTICLE)
    suspend fun uploadPost(@Part("latitude") latitude : Double,
                           @Part("longitude") longitude : Double,
                           @Part("detailAddress") detailAddress : String,
                           @Part("category") category: String,
                           @Part imageList : List<MultipartBody.Part>
    ): Response<Any>

    @Multipart
    @PUT("$BASE_URL_ARTICLE/{articleId}")
    suspend fun modifyPost( @Path("articleId") articleId : Long,
                            @Part("latitude") latitude : Double,
                            @Part("longitude") longitude : Double,
                            @Part("detailAddress") detailAddress : String,
                            @Part("category") category: String,
                            @Part imageList : List<MultipartBody.Part>
    ): Response<Any>

}