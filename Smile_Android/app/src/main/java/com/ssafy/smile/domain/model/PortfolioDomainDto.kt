package com.ssafy.smile.domain.model

data class PortfolioDomainDto (
    val isMe: Boolean = false,
    val categoryName: String = "",
    val photographerName: String = "",
    val place: String = "",
    val categoryPrice: String = "",
    val introduction: String = "",
    val isHeart: Boolean = false,
    val hearts: Int = 0
)