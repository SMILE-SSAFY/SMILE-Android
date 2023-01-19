package com.ssafy.smile.domain.model

import com.ssafy.smile.data.remote.model.SignUpRequestDto

data class SignUpDomainDto(
    val email: String = "",
    val password: String = "",
    val name: String = "",
    val phoneNumber: String = ""
){
    fun toSignUpRequestDto(): SignUpRequestDto = SignUpRequestDto(email, password, name, phoneNumber)
}