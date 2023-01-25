package com.ssafy.smile.data.remote.model

import com.ssafy.smile.common.util.CommonUtils
import com.ssafy.smile.domain.model.PostDomainDto
import java.util.Date

data class PostDetailResponseDto (
    val index: Long = 0,
    val photographerId: Long = 0,
    val photoUrl: ArrayList<String> = arrayListOf(),
    val latitude: Float = 0f,
    val longitude: Float = 0f,
    val heart: Int = 0,
    val category: String = "",
    val createdAt: Date = Date()
){
    fun toPostDomainDto(): PostDomainDto{
        // TODO : 서버에 변경 요청 후 바꾸기
        return PostDomainDto(
            isMe = true,
            photographerId = photographerId,
            photographerName = "홍길동",
            latitude = latitude,
            longitude = longitude,
            photoUrl = photoUrl,
            isLike = false,
            heart = heart.toString(),
            category = "#${category}",
            createdAt = CommonUtils.dateToString(createdAt)
        )
    }
}