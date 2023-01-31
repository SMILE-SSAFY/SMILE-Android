package com.ssafy.smile.domain.model

data class PostDomainDto (
    val isMe: Boolean = false,
    val isHeart: Boolean = false,
    val hearts: Int = 0,
    val detailAddress: String = "",
    val createdAt: String = "",
    val category: String = "",
    val photographerName: String = "",
    val photoUrl: ArrayList<String> = arrayListOf(),
)