package com.ssafy.smile.data.remote.datasource

import com.ssafy.smile.data.remote.model.PostDetailResponseDto
import com.ssafy.smile.data.remote.model.PostSearchRequestDto
import retrofit2.Response

interface PostRemoteDataSource {
    suspend fun getPostById(articleId: Long): Response<PostDetailResponseDto>
    suspend fun deletePostById(articleId: Long): Response<Any>
    suspend fun getPostSearchListById(clusterId : Long, condition:String, page:Int) : Response<PostSearchRequestDto>

}