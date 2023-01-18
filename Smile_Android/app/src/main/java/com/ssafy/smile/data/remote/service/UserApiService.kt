package com.ssafy.smile.data.remote.service

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApiService {
    @GET("/api/user/check/email/{email}")
    suspend fun checkEmail(@Path("email") email: String): Response<Boolean>

    @GET("/api/user/check/nickname/{nickname}")
    suspend fun checkNickname(@Path("nickname") nickname: String): Response<Boolean>
}