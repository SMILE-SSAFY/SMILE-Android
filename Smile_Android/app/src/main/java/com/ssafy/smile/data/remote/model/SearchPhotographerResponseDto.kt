package com.ssafy.smile.data.remote.model

import com.ssafy.smile.domain.model.CustomPhotographerDomainDto

//TODO: API 문서에 맞춰서 바꾸기
data class SearchPhotographerResponseDto(
    val id: Int = 0
){
    fun toCustomPhotographerDomainDto(): CustomPhotographerDomainDto {
        return CustomPhotographerDomainDto(
            "aa",
            "aa",
            "aa",
            "aa",
            "aa",
            true,
            200
        )
    }
}