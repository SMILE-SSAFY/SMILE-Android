package com.ssafy.smile.domain.model

import java.util.Locale.Category

data class PortfolioDomainDto (
    val isMe: Boolean = false,
    val isLike: Boolean = false,
    val name: String = "",
    val place: ArrayList<Place> = arrayListOf(),
    val introduction: String = "",
    val articles: ArrayList<Article> = arrayListOf(),
    val category: ArrayList<Category> = arrayListOf(),
)

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