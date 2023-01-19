package com.ssafy.smile.data.remote.model

import com.ssafy.smile.domain.model.SignUpDomainDto

data class SignUpRequestDto(
    val email: String = "",
    val password: String = "",
    val name: String = "",
    val phoneNumber: String = ""
){
    fun toSignUpDomainDto(): SignUpDomainDto = SignUpDomainDto(email, password, name, phoneNumber)
}