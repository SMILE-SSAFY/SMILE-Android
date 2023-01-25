package com.ssafy.smile.data.remote.service

import com.ssafy.smile.data.remote.model.PostDetailResponseDto
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface PostApiService {
    @GET("/api/article/{articleId}")
    suspend fun getPostById(@Path("articleId") articleId: Long): Response<PostDetailResponseDto>

    @DELETE("/api/article/{articleId}")
    suspend fun deletePostById(@Path("articleId") articleId: Long): Response<Any>
}