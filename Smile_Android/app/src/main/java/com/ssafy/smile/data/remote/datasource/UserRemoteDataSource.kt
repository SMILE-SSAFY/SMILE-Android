package com.ssafy.smile.data.remote.datasource

import com.ssafy.smile.data.remote.model.LoginRequestDto
import com.ssafy.smile.data.remote.model.SignUpRequestDto
import com.ssafy.smile.data.remote.model.UserResponseDto
import retrofit2.Response

interface UserRemoteDataSource {
    suspend fun checkEmail(email: String): Response<String>
    suspend fun signUp(signUpDto: SignUpRequestDto): Response<UserResponseDto>
    suspend fun checkPhoneNumber(phoneNumber: String): Response<Int>
    suspend fun login(loginRequestDto: LoginRequestDto): Response<UserResponseDto>
    suspend fun withDraw(): Response<String>
}