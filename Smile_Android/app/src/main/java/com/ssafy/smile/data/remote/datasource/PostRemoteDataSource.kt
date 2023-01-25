package com.ssafy.smile.data.remote.datasource

import com.ssafy.smile.data.remote.model.PostDetailResponseDto
import retrofit2.Response

interface PostRemoteDataSource {
    suspend fun getPostById(articleId: Long): Response<PostDetailResponseDto>
    suspend fun deletePostById(articleId: Long): Response<Any>
}