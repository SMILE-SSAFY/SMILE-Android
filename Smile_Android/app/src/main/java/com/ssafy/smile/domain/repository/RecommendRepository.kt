package com.ssafy.smile.domain.repository

interface RecommendRepository {
    suspend fun getPhotographerRecommendInfo(address:String)
}