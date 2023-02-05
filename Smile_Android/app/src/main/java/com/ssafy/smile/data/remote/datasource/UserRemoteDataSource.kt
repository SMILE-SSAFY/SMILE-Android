package com.ssafy.smile.data.remote.datasource

import com.ssafy.smile.data.remote.model.*
import retrofit2.Response
import retrofit2.http.Body

interface UserRemoteDataSource {
    suspend fun checkEmail(email: String): Response<String>
    suspend fun signUp(signUpDto: SignUpRequestDto): Response<UserResponseDto>
    suspend fun checkPhoneNumber(phoneNumber: String): Response<Int>
    suspend fun login(loginRequestDto: LoginRequestDto): Response<UserResponseDto>
    suspend fun kakaoLogin(token: KakaoLoginRequestDto): Response<UserResponseDto>
    suspend fun withDraw(): Response<String>
    suspend fun myPage(): Response<MyPageResponseDto>
}