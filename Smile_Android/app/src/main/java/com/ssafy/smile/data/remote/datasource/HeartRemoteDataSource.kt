package com.ssafy.smile.data.remote.datasource

import com.ssafy.smile.data.remote.model.ArticleHeartResponseDto
import com.ssafy.smile.data.remote.model.PhotographerHeartDto
import com.ssafy.smile.data.remote.model.PhotographerHeartResponseDto
import com.ssafy.smile.data.remote.model.PostHeartDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path

interface HeartRemoteDataSource {
    suspend fun photographerHeart(photographerId: Long): Response<PhotographerHeartDto>
    suspend fun postHeart(articleId: Long): Response<PostHeartDto>
    suspend fun getArticleHeartList() : Response<List<ArticleHeartResponseDto>>
    suspend fun getPhotographerHeartList() : Response<List<PhotographerHeartResponseDto>>
}