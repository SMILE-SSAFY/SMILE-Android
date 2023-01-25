package com.ssafy.smile.domain.repository

import com.ssafy.smile.domain.model.LoginDomainDto
import com.ssafy.smile.domain.model.SignUpDomainDto

interface UserRepository {
    suspend fun checkEmail(email: String)
    suspend fun signUp(signUpDomainDto: SignUpDomainDto)
    suspend fun checkPhoneNumber(phoneNumber: String)
    suspend fun login(loginDomainDto: LoginDomainDto)
    suspend fun kakaoLogin(token: String)
}