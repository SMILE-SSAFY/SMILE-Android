package com.ssafy.smile.data.remote.model

import com.ssafy.smile.domain.model.CustomPostDomainDto

//TODO: API 문서에 맞춰서 바꾸기
data class SearchPostResponseDto(
    val id: Int = 0
){
    fun toCustomPostDomainDto(): CustomPostDomainDto {
        return CustomPostDomainDto(
            "aa",
            "aa",
            "aa",
            "aa",
            true,
            200
        )
    }
}