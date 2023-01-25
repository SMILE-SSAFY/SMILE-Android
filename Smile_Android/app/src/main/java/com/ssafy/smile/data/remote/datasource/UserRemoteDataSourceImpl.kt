package com.ssafy.smile.data.remote.datasource

import com.ssafy.smile.data.remote.model.LoginRequestDto
import com.ssafy.smile.data.remote.model.SignUpRequestDto
import com.ssafy.smile.data.remote.model.UserResponseDto
import com.ssafy.smile.data.remote.service.UserApiService
import retrofit2.Response

class UserRemoteDataSourceImpl(private val userApiService: UserApiService): UserRemoteDataSource {
    override suspend fun checkEmail(email: String): Response<String> {
        return userApiService.checkEmail(email)
    }

    override suspend fun signUp(signUpDto: SignUpRequestDto): Response<UserResponseDto> {
        return userApiService.signUp(signUpDto)
    }

    override suspend fun checkPhoneNumber(phoneNumber: String): Response<Int> {
        return userApiService.checkPhoneNumber(phoneNumber)
    }

    override suspend fun login(loginRequestDto: LoginRequestDto): Response<UserResponseDto> {
        return userApiService.login(loginRequestDto)
    }

    override suspend fun kakaoLogin(token: String): Response<UserResponseDto> {
        return userApiService.kakaoLogin(token)
    }
}