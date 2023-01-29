package com.ssafy.smile.data.remote.datasource

import com.ssafy.smile.data.remote.model.SearchPhotographerResponseDto
import com.ssafy.smile.data.remote.model.SearchPostResponseDto
import com.ssafy.smile.data.remote.service.SearchApiService
import retrofit2.Response

class SearchRemoteDataSourceImpl(private val searchApiService: SearchApiService): SearchRemoteDataSource {
    override fun searchPhotographer(categoryName: String): Response<ArrayList<SearchPhotographerResponseDto>> {
        return searchApiService.searchPhotographer(categoryName)
    }

    override fun searchPost(categoryName: String): Response<ArrayList<SearchPostResponseDto>> {
        return searchApiService.searchPost(categoryName)
    }
}