package com.ssafy.smile.data.remote.model

import java.io.File


data class Post(
    val ArticlePostReq: ArticlePostReq,
    val image: List<File>
)

data class ArticlePostReq(
    val category: String,
    val detailAddress: String,
    val latitude: Float,
    val longitude: Float
)
