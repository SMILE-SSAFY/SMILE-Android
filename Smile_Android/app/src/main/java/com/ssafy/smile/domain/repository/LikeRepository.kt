package com.ssafy.smile.domain.repository

import retrofit2.Response

interface LikeRepository {
    suspend fun photographerLike(photographerId: Long)
    suspend fun photographerLikeCancel(photographerId: Long)
}