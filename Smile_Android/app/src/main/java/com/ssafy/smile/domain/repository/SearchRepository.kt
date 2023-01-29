package com.ssafy.smile.domain.repository

interface SearchRepository {
    suspend fun searchPhotographer(categoryName:String)
    suspend fun searchPost(categoryName: String)
}