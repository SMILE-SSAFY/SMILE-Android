package com.ssafy.smile.domain.model

import com.ssafy.smile.data.remote.model.PhotographerDto
import com.ssafy.smile.data.remote.model.PhotographerRequestDto
import java.io.File

data class PhotographerRequestDomainDto(
    var profileImg: File?=null,
    var introduction: String?=null,
    var categories: List<CategoryDomainDto> = listOf(),
    var places: List<PlaceDomainDto> = listOf(),
    var account: AccountDomainDto?=null
){
    fun makeToPhotographerRequestDto() = PhotographerRequestDto(profileImg!!, PhotographerDto(account!!.accountBank!!, account!!.accountNum!!, introduction!!, categories.map { it.toCategoryDto() }, places.map { it.toPlaceDto() }))
}