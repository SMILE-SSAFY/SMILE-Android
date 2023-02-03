package com.ssafy.smile.domain.model

data class CustomPhotographerDomainDto(
    val photographerId: Long = 0,
    val img: String? = "",
    val category: String = "",
    val name: String = "",
    val location: String = "",
    val price: String = "",
    val isLike: Boolean = false,
    val like: Int = 0
)