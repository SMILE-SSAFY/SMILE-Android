package com.ssafy.smile.data.remote.service

import com.ssafy.smile.data.remote.model.PhotographerHeartDto
import com.ssafy.smile.data.remote.model.ArticleHeartResponseDto
import com.ssafy.smile.data.remote.model.PhotographerHeartResponseDto
import com.ssafy.smile.data.remote.model.PostHeartDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface HeartApiService {
    @PUT("/api/photographer/heart/{photographerId}")
    suspend fun photographerHeart(@Path("photographerId") photographerId: Long): Response<PhotographerHeartDto>

    @PUT("/api/article/heart/{articleId}")
    suspend fun postHeart(@Path("articleId") articleId: Long): Response<PostHeartDto>

    @GET("/api/article/heart/list")
    suspend fun getArticleHeartList() : Response<List<ArticleHeartResponseDto>>

    @GET("/api/photographer/heart/list")
    suspend fun getPhotographerHeartList() : Response<List<PhotographerHeartResponseDto>>
}