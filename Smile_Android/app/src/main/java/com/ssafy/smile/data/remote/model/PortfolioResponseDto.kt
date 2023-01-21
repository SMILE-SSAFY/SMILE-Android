package com.ssafy.smile.data.remote.model

import com.ssafy.smile.common.util.CommonUtils
import com.ssafy.smile.domain.model.PortfolioDomainDto
import kotlin.collections.ArrayList

data class PortfolioResponseDto (
    val index: Long = 0,
    val isMe: Boolean = false,
    val isLike: Boolean = false,
    val photographerName: String = "",
    val places: ArrayList<Place> = arrayListOf(),
    val introduction: String = "",
    val articles: ArrayList<Article> = arrayListOf(),
    val category: ArrayList<Category> = arrayListOf(),
){
    fun toPortfolioDomainDto(): PortfolioDomainDto {
        return PortfolioDomainDto(
            isMe,
            isLike,
            getCategoryName(),
            photographerName,
            getCategoryPlace(),
            getCategoryPrice(category),
            introduction,
            articles
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

    private fun getCategoryPlace(): String {
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

data class Article (
    val articleId: Long = 0,
    val photoUrl: String = ""
)

data class Category (
    val categoryName: String = "",
    val categoryPrice: String = "",
    val description: String = ""
)