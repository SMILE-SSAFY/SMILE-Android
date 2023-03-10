package com.ssafy.smile.data.remote.service

import com.ssafy.smile.data.remote.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApiService {
    @GET("/api/user/check/email/{email}")
    suspend fun checkEmail(@Path("email") email: String): Response<String>

    @POST("/api/user/register")
    suspend fun signUp(@Body signUpDto: SignUpRequestDto): Response<UserResponseDto>

    @GET("/api/user/check/phone/{phoneNumber}")
    suspend fun checkPhoneNumber(@Path("phoneNumber") phoneNumber: String): Response<Int>

    @POST("/api/user/login")
    suspend fun login(@Body loginRequestDto: LoginRequestDto): Response<UserResponseDto>

    @POST("/api/user/sns")
    suspend fun kakaoLogin(@Body token: KakaoLoginRequestDto): Response<UserResponseDto>

    @DELETE("/api/user")
    suspend fun withDraw(): Response<String>

    @GET("/api/user")
    suspend fun myPage(): Response<MyPageResponseDto>

    @POST("/api/user/logout")
    suspend fun logout(@Body logoutRequestDto: LogoutRequestDto): Response<Any>

}