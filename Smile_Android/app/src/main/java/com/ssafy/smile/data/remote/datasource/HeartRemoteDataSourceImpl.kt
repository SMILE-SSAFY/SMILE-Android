package com.ssafy.smile.data.remote.datasource

import com.ssafy.smile.data.remote.model.PhotographerHeartDto
import com.ssafy.smile.data.remote.model.PostHeartDto
import com.ssafy.smile.data.remote.service.HeartApiService
import retrofit2.Response

class HeartRemoteDataSourceImpl(private val heartApiService: HeartApiService): HeartRemoteDataSource {
    override suspend fun photographerHeart(photographerId: Long): Response<PhotographerHeartDto> {
        return heartApiService.photographerHeart(photographerId)
    }

    override suspend fun postHeart(articleId: Long): Response<PostHeartDto> {
        return heartApiService.postHeart(articleId)
    }
}