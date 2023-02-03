package com.ssafy.smile.domain.repository

import com.ssafy.smile.data.remote.model.KakaoLoginRequestDto
import com.ssafy.smile.data.remote.model.MyPageResponseDto
import com.ssafy.smile.domain.model.LoginDomainDto
import com.ssafy.smile.domain.model.SignUpDomainDto
import retrofit2.Response

interface UserRepository {
    suspend fun checkEmail(email: String)
    suspend fun signUp(signUpDomainDto: SignUpDomainDto)
    suspend fun checkPhoneNumber(phoneNumber: String)
    suspend fun login(loginDomainDto: LoginDomainDto)
    suspend fun kakaoLogin(token: KakaoLoginRequestDto)
    suspend fun withDrawUser()
    suspend fun myPage()
}