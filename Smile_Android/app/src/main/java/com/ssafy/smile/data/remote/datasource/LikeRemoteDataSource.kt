package com.ssafy.smile.data.remote.datasource

import retrofit2.Response

interface LikeRemoteDataSource {
    suspend fun photographerLike(photographerId: Long): Response<Any>
    suspend fun photographerLikeCancel(photographerId: Long): Response<Any>
    suspend fun postLike(articleId: Long): Response<Any>
    suspend fun postLikeCancel(articleId: Long): Response<Any>
}