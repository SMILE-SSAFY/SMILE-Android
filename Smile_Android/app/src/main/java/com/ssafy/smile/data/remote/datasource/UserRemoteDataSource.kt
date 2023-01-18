package com.ssafy.smile.data.remote.datasource

import retrofit2.Response

interface UserRemoteDataSource {
    suspend fun checkEmail(email: String): Response<Boolean>
}