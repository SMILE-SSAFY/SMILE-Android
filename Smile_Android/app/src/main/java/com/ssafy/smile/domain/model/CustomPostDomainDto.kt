package com.ssafy.smile.domain.model

data class CustomPostDomainDto(
    val img: String = "",
    val category: String = "",
    val name: String = "",
    val location: String = "",
    val isLike: Boolean = false,
    val like: Int = 0
)