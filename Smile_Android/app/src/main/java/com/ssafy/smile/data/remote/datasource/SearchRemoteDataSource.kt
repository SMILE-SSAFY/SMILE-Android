package com.ssafy.smile.data.remote.datasource

import com.ssafy.smile.data.remote.model.SearchPhotographerResponseDto
import com.ssafy.smile.data.remote.model.SearchPostResponseDto
import retrofit2.Response
import retrofit2.http.Query

interface SearchRemoteDataSource {
    fun searchPhotographer(categoryId:String): Response<ArrayList<SearchPhotographerResponseDto>>
    fun searchPost(categoryId:String): Response<ArrayList<SearchPostResponseDto>>
}