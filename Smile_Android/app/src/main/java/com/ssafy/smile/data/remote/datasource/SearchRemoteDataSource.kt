package com.ssafy.smile.data.remote.datasource

import com.ssafy.smile.data.remote.model.SearchPhotographerResponseDto
import com.ssafy.smile.data.remote.model.SearchPostResponseDto
import retrofit2.Response
import retrofit2.http.Query

interface SearchRemoteDataSource {
    suspend fun searchPhotographer(categoryName:String): Response<ArrayList<SearchPhotographerResponseDto>>
    suspend fun searchPost(categoryName:String): Response<ArrayList<SearchPostResponseDto>>
}