package com.ssafy.smile.data.remote.service

import com.ssafy.smile.data.remote.model.SignUpRequestDto
import com.ssafy.smile.data.remote.model.SignUpResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApiService {
    @GET("/api/user/check/email/{email}")
    suspend fun checkEmail(@Path("email") email: String): Response<String>

    @POST("/api/user/register")
    suspend fun signUp(@Body signUpDto: SignUpRequestDto): Response<SignUpResponseDto>

    @GET("/api/user/check/phone/{phoneNumber}")
    suspend fun checkPhoneNumber(@Path("phoneNumber") phoneNumber: String): Response<Int>
}