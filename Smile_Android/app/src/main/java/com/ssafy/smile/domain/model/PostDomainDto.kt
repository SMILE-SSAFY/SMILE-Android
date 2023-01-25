package com.ssafy.smile.domain.model

data class PostDomainDto (
    val isMe: Boolean = false,
    val photographerId: Long = 0,
    val photographerName: String = "",
    val latitude: Float = 0f,
    val longitude: Float = 0f,
    val photoUrl: ArrayList<String> = arrayListOf(),
    val isLike: Boolean = false,
    val heart: String = "",
    val category: String = "",
    val createdAt: String = ""
)