package com.ssafy.smile.data.remote.datasource

import com.ssafy.smile.data.remote.service.LikeApiService
import retrofit2.Response

class LikeRemoteDataSourceImpl(private val likeApiService: LikeApiService): LikeRemoteDataSource {
    override suspend fun photographerLike(photographerId: Long): Response<Any> {
        return likeApiService.photographerLike(photographerId)
    }

    override suspend fun photographerLikeCancel(photographerId: Long): Response<Any> {
        return likeApiService.photographerLikeCancel(photographerId)
    }
}