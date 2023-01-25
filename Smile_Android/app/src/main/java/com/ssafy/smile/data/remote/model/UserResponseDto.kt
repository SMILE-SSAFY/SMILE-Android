package com.ssafy.smile.data.remote.model

import com.ssafy.smile.domain.model.Types

data class UserResponseDto(
    val token: String = "",
    val role: Types.Role = Types.Role.USER
)