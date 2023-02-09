package com.ssafy.smile.data.remote.model

import com.ssafy.smile.domain.model.AccountDomainDto
import com.ssafy.smile.domain.model.PhotographerRequestDomainDto
import java.io.File

data class PhotographerRequestDto(
    val profileImg: File,
    val photographerDto : PhotographerDto
){
    fun makeToPhotographerRequestDomainDto() {
        val accountInfoArr = photographerDto.account.split(" ")
        PhotographerRequestDomainDto(profileImg, photographerDto.introduction, photographerDto.categories.map { it.toCategoryDto() }, photographerDto.places.map { it.toPlaceDto() },
            AccountDomainDto(false, accountInfoArr[0], accountInfoArr[1]))
    }
}

