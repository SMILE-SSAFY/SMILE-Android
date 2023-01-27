package com.ssafy.smile.data.remote.datasource

import com.ssafy.smile.data.remote.model.SearchPhotographerResponseDto
import com.ssafy.smile.data.remote.model.SearchPostResponseDto
import com.ssafy.smile.data.remote.service.SearchApiService
import retrofit2.Response

class SearchRemoteDataSourceImpl(private val searchApiService: SearchApiService): SearchRemoteDataSource {
    override fun searchPhotographer(categoryId: String): Response<ArrayList<SearchPhotographerResponseDto>> {
        return searchApiService.searchPhotographer(categoryId)
    }

    override fun searchPost(categoryId: String): Response<ArrayList<SearchPostResponseDto>> {
        return searchApiService.searchPost(categoryId)
    }
}