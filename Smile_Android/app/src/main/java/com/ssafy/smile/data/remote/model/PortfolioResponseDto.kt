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
        return PortfolioDomainDto(
            isMe,
            getCategoryName(),
            photographerName,
            getPlace(),
            getCategoryPrice(category),
            introduction,
            isHeart,
            hearts
        )
    }

    private fun getCategoryName(): String {
        return when (category.size) {
            1 -> {
                "#${category[0].categoryName}}"
            }
            2 -> {
                "#${category[0].categoryName}  #${category[1].categoryName}"
            }
            else -> {
                "#${category[0].categoryName}  #${category[1].categoryName}..."
            }
        }
    }

    private fun getPlace(): String {
        return when (places.size) {
            1 -> {
                "${places[0].place}}"
            }
            2 -> {
                "${places[0].place}, ${places[1].place}"
            }
            else -> {
                "${places[0].place}, ${places[1].place}, ..."
            }
        }
    }

    private fun getCategoryPrice(pCategory: ArrayList<Category>): String {
        val sCategory = pCategory.sortedBy { it.categoryPrice }
        return "${CommonUtils.makeComma(sCategory[0].categoryPrice.toInt())}원 부터~"
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
