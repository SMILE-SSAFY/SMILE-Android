package com.ssafy.smile.data.remote.datasource

import com.ssafy.smile.data.remote.model.SignUpRequestDto
import com.ssafy.smile.data.remote.model.SignUpResponseDto
import retrofit2.Response

interface UserRemoteDataSource {
    suspend fun checkEmail(email: String): Response<Boolean>
    suspend fun checkNickname(nickname: String): Response<Boolean>
    suspend fun signUp(signUpDto: SignUpRequestDto): Response<SignUpResponseDto>
}