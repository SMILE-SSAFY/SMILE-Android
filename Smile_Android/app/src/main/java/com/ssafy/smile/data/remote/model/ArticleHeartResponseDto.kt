package com.ssafy.smile.data.remote.model

import com.ssafy.smile.domain.model.CustomPostDomainDto


data class ArticleHeartResponseDto(
    val articleId: Long = 0,
    val category: String = "",
    val photoUrl: String = "",
    val photographerName: String = "",
    val photographerId: Long = 0,
    val createdAt: String,
    val detailAddress: String = "",
    val hearts: Int = 0,
    val isHeart: Boolean = false,
    val latitude: Float = 0.0f,
    val longitude: Float = 0.0f
){
    fun toCustomPostDomainDto(): CustomPostDomainDto {
        return CustomPostDomainDto(
            articleId,
            photographerId,
            photoUrl,
            category,
            photographerName,
            detailAddress,
            isHeart,
            hearts
        )
    }
}