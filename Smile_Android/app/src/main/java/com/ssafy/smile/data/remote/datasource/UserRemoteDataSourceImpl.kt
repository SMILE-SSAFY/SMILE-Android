package com.ssafy.smile.data.remote.datasource

import com.ssafy.smile.data.remote.model.SignUpRequestDto
import com.ssafy.smile.data.remote.model.SignUpResponseDto
import com.ssafy.smile.data.remote.service.UserApiService
import retrofit2.Response

class UserRemoteDataSourceImpl(private val userApiService: UserApiService): UserRemoteDataSource {
    override suspend fun checkEmail(email: String): Response<String> {
        return userApiService.checkEmail(email)
    }

    override suspend fun signUp(signUpDto: SignUpRequestDto): Response<SignUpResponseDto> {
        return userApiService.signUp(signUpDto)
    }
}