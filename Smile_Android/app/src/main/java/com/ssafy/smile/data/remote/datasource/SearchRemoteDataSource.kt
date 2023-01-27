package com.ssafy.smile.data.remote.datasource

import com.ssafy.smile.data.remote.model.SearchPhotographerResponseDto
import retrofit2.Response

interface SearchRemoteDataSource {
    fun searchPhotographer(category:String): Response<ArrayList<SearchPhotographerResponseDto>>
}