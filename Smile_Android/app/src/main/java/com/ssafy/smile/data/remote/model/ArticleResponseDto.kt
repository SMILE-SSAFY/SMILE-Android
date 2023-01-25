package com.ssafy.smile.data.remote.model

data class ArticleResponseDto (
    val articles: ArrayList<Article> = arrayListOf(),
)

data class Article (
    val articleId: Long = 0,
    val photoUrl: String = ""
)