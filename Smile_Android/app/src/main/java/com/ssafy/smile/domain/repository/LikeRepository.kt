package com.ssafy.smile.domain.repository

interface LikeRepository {
    suspend fun photographerLike(photographerId: Long)
    suspend fun photographerLikeCancel(photographerId: Long)
    suspend fun postLike(articleId: Long)
    suspend fun postLikeCancel(articleId: Long)
}