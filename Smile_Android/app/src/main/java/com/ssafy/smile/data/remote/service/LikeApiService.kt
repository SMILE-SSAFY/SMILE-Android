package com.ssafy.smile.data.remote.service

import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path

interface LikeApiService {
    @POST("/api/photographer/heart/{photographerId}")
    suspend fun photographerLike(@Path("photographerId") photographerId: Long): Response<Any>

    @DELETE("/api/photographer/heart/{photographerId}")
    suspend fun photographerLikeCancel(@Path("photographerId") photographerId: Long): Response<Any>

    @POST("/api/article/heart/{articleId}")
    suspend fun postLike(@Path("articleId") articleId: Long): Response<Any>

    @DELETE("/api/article/heart/{articleId}")
    suspend fun postLikeCancel(@Path("articleId") articleId: Long): Response<Any>
}