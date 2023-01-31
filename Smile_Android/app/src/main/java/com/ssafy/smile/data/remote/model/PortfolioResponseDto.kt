package com.ssafy.smile.data.remote.model

import com.ssafy.smile.common.util.CommonUtils
import com.ssafy.smile.domain.model.PortfolioDomainDto
import kotlin.collections.ArrayList

data class PortfolioResponseDto (
    val index: Long = 0,
    val isMe: Boolean = false,
    val isHeart: Boolean = false,
    val hearts: Int = 0,
    val photographerName: String = "",
    val profileImg: String = "",
    val introduction: String = "",
    val places: ArrayList<String> = arrayListOf(),
    val categories: ArrayList<String> = arrayListOf(),
){
    fun toPortfolioDomainDto(): PortfolioDomainDto {
        return PortfolioDomainDto(
            isMe,
            isHeart,
            hearts,
            photographerName,
            profileImg,
            introduction,
            CommonUtils.getPlace(places),
            CommonUtils.getCategoryName(categories)
        )
    }
}

data class Category (
    val categoryName: String = "",
    val categoryPrice: String = "",
    val description: String = ""
)
