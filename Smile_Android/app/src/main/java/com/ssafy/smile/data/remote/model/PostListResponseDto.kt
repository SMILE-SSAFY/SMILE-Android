package com.ssafy.smile.data.remote.model

data class PostListResponseDto (
    val articles: ArrayList<PostListItem> = arrayListOf(),
)

data class PostListItem (
    val articleId: Long = 0,
    val photoUrl: String = ""
)