package com.ssafy.smile.data.remote.datasource

import com.ssafy.smile.data.remote.service.UserApiService
import retrofit2.Response

class UserRemoteDataSourceImpl(private val userApiService: UserApiService): UserRemoteDataSource {
    override suspend fun checkEmail(email: String): Response<Boolean> {
        return userApiService.checkEmail(email)
    }
}