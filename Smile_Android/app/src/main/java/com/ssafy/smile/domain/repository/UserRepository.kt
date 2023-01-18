package com.ssafy.smile.domain.repository

interface UserRepository {
    suspend fun checkEmail(email: String)
}