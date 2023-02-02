package com.ssafy.smile.domain.repository

interface HeartRepository {
    suspend fun photographerHeart(photographerId: Long)
    suspend fun postHeart(articleId: Long)
    suspend fun getArticleHeartList()
    suspend fun getPhotographerHeartList()
}