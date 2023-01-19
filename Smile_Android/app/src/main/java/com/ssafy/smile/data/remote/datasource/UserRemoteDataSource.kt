package com.ssafy.smile.data.remote.datasource

import com.ssafy.smile.data.remote.model.SignUpRequestDto
import com.ssafy.smile.data.remote.model.SignUpResponseDto
import retrofit2.Response
import retrofit2.http.Path

interface UserRemoteDataSource {
    suspend fun checkEmail(email: String): Response<String>
    suspend fun signUp(signUpDto: SignUpRequestDto): Response<SignUpResponseDto>
    suspend fun checkPhoneNumber(phoneNumber: String): Response<Int>
}