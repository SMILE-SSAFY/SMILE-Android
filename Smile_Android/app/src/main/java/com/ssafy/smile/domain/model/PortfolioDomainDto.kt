package com.ssafy.smile.domain.model

import com.ssafy.smile.data.remote.model.Article
import com.ssafy.smile.data.remote.model.Place

data class PortfolioDomainDto (
    val isMe: Boolean = false,
    val isLike: Boolean = false,
    val categoryName: ArrayList<String> = arrayListOf(),
    val name: String = "",
    val place: ArrayList<Place> = arrayListOf(),
    val categoryPrice: ArrayList<String> = arrayListOf(),
    val introduction: String = "",
    val articles: ArrayList<Article> = arrayListOf()
)