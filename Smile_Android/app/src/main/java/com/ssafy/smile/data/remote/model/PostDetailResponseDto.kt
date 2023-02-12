package com.ssafy.smile.data.remote.model

import com.ssafy.smile.common.util.CommonUtils
import com.ssafy.smile.domain.model.PostDomainDto
import kotlin.collections.ArrayList

private const val TAG = "PostDetailResponseDto_스마일"
data class PostDetailResponseDto (
    val id: Long = 0,
    val photographerName: String = "",
    val isMe: Boolean = false,
    val isHeart: Boolean = false,
    val profileImg: String = "",
    val hearts: Int = 0,
    val detailAddress: String = "",
    val createdAt: String = "",
    val category: String = "",
    val photoUrls: String = ""
){
    fun toPostDomainDto(): PostDomainDto{
        return PostDomainDto(
            isMe,
            isHeart,
            profileImg,
            hearts,
            CommonUtils.deleteQuote(detailAddress),
            createdAt,
            "#${CommonUtils.deleteQuote(category)}",
            photographerName,
            photoUrlToDomain(photoUrls)
        )
    }
    private fun photoUrlToDomain(photoUrls: String): ArrayList<String> {
        val res = arrayListOf<String>()

        val photoUrlString = photoUrls.replace("[", "").replace("]", "")
        photoUrlString.split(", ").forEach { str ->
            res.add(str.trim { it <= ' ' })
        }

        return res
    }
}