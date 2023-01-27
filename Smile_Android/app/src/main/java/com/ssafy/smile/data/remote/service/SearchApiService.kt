package com.ssafy.smile.data.remote.service

import com.ssafy.smile.data.remote.model.SearchPhotographerResponseDto
import com.ssafy.smile.data.remote.model.SearchPostResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApiService {
    @GET("/api/photographer/search")
    fun searchPhotographer(@Query("categoryId") categoryId:String): Response<ArrayList<SearchPhotographerResponseDto>>

    @GET("/api/article/search")
    fun searchPost(@Query("categoryId") categoryId:String): Response<ArrayList<SearchPostResponseDto>>
}