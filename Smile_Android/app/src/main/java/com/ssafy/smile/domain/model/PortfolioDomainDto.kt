package com.ssafy.smile.domain.model

import com.ssafy.smile.data.remote.model.Article

data class PortfolioDomainDto (
    val isMe: Boolean = false,
    val isLike: Boolean = false,
    val categoryName: String = "",
    val photographerName: String = "",
    val place: String = "",
    val categoryPrice: String = "",
    val introduction: String = "",
    val articles: ArrayList<Article> = arrayListOf()
)