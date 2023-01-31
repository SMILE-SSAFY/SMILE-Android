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
    val places: ArrayList<Place> = arrayListOf(),
    val introduction: String = "",
    val category: ArrayList<Category> = arrayListOf(),
){
    fun toPortfolioDomainDto(): PortfolioDomainDto {
        val categoryNames = arrayListOf<String>()
        val categoryPrices = arrayListOf<Int>()
        category.forEach { data ->
            categoryNames.add(data.categoryName)
            categoryPrices.add(data.categoryPrice.toInt())
        }

        return PortfolioDomainDto(
            isMe,
            CommonUtils.getCategoryName(categoryNames),
            photographerName,
            CommonUtils.getPlace(places),
            CommonUtils.getCategoryPrice(categoryPrices),
            introduction,
            isHeart,
            hearts
        )
    }
}

data class Place (
    val place: String = ""
)

data class Category (
    val categoryName: String = "",
    val categoryPrice: String = "",
    val description: String = ""
)
