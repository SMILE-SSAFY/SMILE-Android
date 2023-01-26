package com.ssafy.smile.data.remote.service

import com.ssafy.smile.data.remote.model.SearchPhotographerResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApiService {
    @GET("/api/photographer/search")
    fun searchPhotographer(@Query("category") category:String): Response<ArrayList<SearchPhotographerResponseDto>>
}