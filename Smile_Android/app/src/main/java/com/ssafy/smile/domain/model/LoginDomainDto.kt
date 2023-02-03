package com.ssafy.smile.domain.model

import com.ssafy.smile.data.remote.model.LoginRequestDto

data class LoginDomainDto(
    val email: String = "",
    val password: String = "",
    val fcmToken: String = ""
){
    fun toLoginRequestDto(): LoginRequestDto = LoginRequestDto(email, password, fcmToken)
}
