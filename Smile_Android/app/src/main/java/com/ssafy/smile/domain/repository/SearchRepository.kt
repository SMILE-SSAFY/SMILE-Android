package com.ssafy.smile.domain.repository

interface SearchRepository {
    suspend fun searchPhotographer(category:String)
}