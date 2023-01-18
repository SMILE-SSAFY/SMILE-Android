package com.ssafy.smile.domain.repository

interface UserRepository {
    suspend fun checkEmail(email: String)
    suspend fun checkNickname(nickname: String)
}