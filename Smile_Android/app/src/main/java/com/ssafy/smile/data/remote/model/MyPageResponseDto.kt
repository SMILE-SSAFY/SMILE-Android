package com.ssafy.smile.data.remote.model

import com.ssafy.smile.domain.model.Types

data class MyPageResponseDto(
    val id: Long = 0,
    val name: String = "",
    val role: Types.Role = Types.Role.USER,
    val photoUrl: String = ""
)