package com.ssafy.smile.data.remote.model

data class SignUpRequestDto(
    val email: String = "",
    val password: String = "",
    val name: String = "",
    val phoneNumber: String = "",
    val fcmToken: String = ""
)

data class LoginRequestDto(
    val email: String = "",
    val password: String = "",
    val fcmToken: String = ""
)

data class KakaoLoginRequestDto(
    val token: String = "",
    val fcmToken: String = ""
)