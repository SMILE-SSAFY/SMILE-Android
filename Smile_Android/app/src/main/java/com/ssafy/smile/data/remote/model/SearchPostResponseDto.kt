package com.ssafy.smile.data.remote.model

import com.ssafy.smile.domain.model.CustomPostDomainDto
import java.time.LocalDateTime

data class SearchPostResponseDto(
    val articleId: Long = 0,
    val photographerName: String = "",
    val latitude: Float = 0.0f,
    val longitude: Float = 0.0f,
    val isHeart: Boolean = false,
    val hearts: Int = 0,
    val detailAddress: String = "",
    val createdAt: String,
    val category: String = "",
    val photoUrl: String = ""
){
    fun toCustomPostDomainDto(): CustomPostDomainDto {
        return CustomPostDomainDto(
            articleId,
            photoUrl,
            category,
            photographerName,
            detailAddress,
            isHeart,
            hearts
        )
    }
}