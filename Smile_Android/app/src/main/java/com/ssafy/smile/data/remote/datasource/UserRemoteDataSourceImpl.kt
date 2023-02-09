package com.ssafy.smile.data.remote.datasource

import com.ssafy.smile.data.remote.model.*
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

    override suspend fun kakaoLogin(token: KakaoLoginRequestDto): Response<UserResponseDto> {
        return userApiService.kakaoLogin(token)
    }

    override suspend fun withDraw(): Response<String> {
        return userApiService.withDraw()
    }

    override suspend fun myPage(): Response<MyPageResponseDto> {
        return userApiService.myPage()
    }

    override suspend fun logout(logoutRequestDto: LogoutRequestDto): Response<Any> {
        return userApiService.logout(logoutRequestDto)
    }

}