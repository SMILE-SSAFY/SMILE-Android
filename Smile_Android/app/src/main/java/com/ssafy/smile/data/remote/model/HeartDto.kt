package com.ssafy.smile.data.remote.model

data class PhotographerHeartDto(
    val photographerId: Long = 0,
    val isHeart: Boolean = false
)

data class PostHeartDto(
    val articleId: Long = 0,
    val isHeart: Boolean = false
)