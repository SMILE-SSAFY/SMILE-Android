package com.ssafy.smile.data.remote.datasource

import com.ssafy.smile.data.remote.model.PostDetailResponseDto
import com.ssafy.smile.data.remote.model.PostSearchRequestDto
import com.ssafy.smile.data.remote.service.PostApiService
import retrofit2.Response

class PostRemoteDataSourceImpl(private val postApiService: PostApiService): PostRemoteDataSource {
    override suspend fun getPostById(articleId: Long): Response<PostDetailResponseDto> {
        return postApiService.getPostById(articleId)
    }

    override suspend fun deletePostById(articleId: Long): Response<Any> {
        return postApiService.deletePostById(articleId)
    }

    override suspend fun getPostSearchListById(clusterId: Long, condition: String, page: Int): Response<PostSearchRequestDto> {
        return postApiService.getPostById(clusterId, condition, page)
    }

}