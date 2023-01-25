package com.ssafy.smile.domain.repository

interface PostRepository {
    suspend fun getPostById(articleId: Long)
    suspend fun deletePostById(articleId: Long)
}