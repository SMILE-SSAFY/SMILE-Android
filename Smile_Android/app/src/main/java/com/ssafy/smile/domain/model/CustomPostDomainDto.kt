package com.ssafy.smile.domain.model

data class CustomPostDomainDto(
    val articleId: Long = 0,
    val photographerId: Long = 0,
    val img: String = "",
    val category: String = "",
    val name: String = "",
    val location: String = "",
    val isLike: Boolean = false,
    val like: Int = 0
)