package com.ssafy.smile.data.remote.model

import com.ssafy.smile.domain.model.LoginDomainDto

data class LoginRequestDto(
    val email: String = "",
    val password: String = ""
){
    fun toLoginDomainDto(): LoginDomainDto = LoginDomainDto(email, password)
}