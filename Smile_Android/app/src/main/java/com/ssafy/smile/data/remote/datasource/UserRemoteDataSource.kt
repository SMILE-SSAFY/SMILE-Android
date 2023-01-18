package com.ssafy.smile.data.remote.datasource

import retrofit2.Response

interface UserRemoteDataSource {
    suspend fun checkEmail(email: String): Response<Boolean>
    suspend fun checkNickname(nickname: String): Response<Boolean>
}