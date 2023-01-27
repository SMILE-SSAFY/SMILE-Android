package com.ssafy.smile.domain.repository

interface SearchRepository {
    suspend fun searchPhotographer(categoryId:String)
    suspend fun searchPost(categoryId: String)
}