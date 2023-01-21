package com.ssafy.smile.data.remote.model

import com.ssafy.smile.domain.model.PortfolioDomainDto
import kotlin.collections.ArrayList

data class PortfolioResponseDto (
    val isMe: Boolean = false,
    val isLike: Boolean = false,
    val name: String = "",
    val place: ArrayList<Place> = arrayListOf(),
    val introduction: String = "",
    val articles: ArrayList<Article> = arrayListOf(),
    val category: ArrayList<Category> = arrayListOf(),
){
    fun toPortfolioDomainDto(): PortfolioDomainDto {
        return PortfolioDomainDto(
            isMe,
            isLike,
            getCategoryName(),
            name,
            place,
            getCategoryPrice(),
            introduction,
            articles
        )
    }

    private fun getCategoryName(): ArrayList<String> {
        val categoryName = arrayListOf<String>()
        category.forEach { category ->
            categoryName.add(category.categoryName)
        }
        return categoryName
    }

    private fun getCategoryPrice(): ArrayList<String> {
        val categoryPrice = arrayListOf<String>()
        category.forEach { category ->
            categoryPrice.add(category.categoryPrice)
        }
        return categoryPrice
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