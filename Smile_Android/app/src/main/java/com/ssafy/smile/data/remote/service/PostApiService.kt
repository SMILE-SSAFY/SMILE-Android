package com.ssafy.smile.data.remote.service

import com.ssafy.smile.data.remote.model.PostDetailResponseDto
import com.ssafy.smile.data.remote.model.PostSearchRequestDto
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PostApiService {
    @GET("/api/article/{articleId}")
    suspend fun getPostById(@Path("articleId") articleId: Long): Response<PostDetailResponseDto>

    @DELETE("/api/article/{articleId}")
    suspend fun deletePostById(@Path("articleId") articleId: Long): Response<Any>

    @GET("/api/article/list/cluster/")
    suspend fun getPostById(@Query("clusterId") clusterId: Long,
                            @Query("condition") condition: String,
                            @Query("page") page: Int): Response<PostSearchRequestDto>

}