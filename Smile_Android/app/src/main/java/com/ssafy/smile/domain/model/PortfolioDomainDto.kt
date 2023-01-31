package com.ssafy.smile.domain.model

data class PortfolioDomainDto (
    val isMe: Boolean = false,
    val isHeart: Boolean = false,
    val hearts: Int = 0,
    val photographerName: String = "",
    val profileImg: String = "",
    val introduction: String = "",
    val place: String = "",
    val category: String = ""
)