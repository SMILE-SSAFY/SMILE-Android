package com.ssafy.smile.data.remote.model

data class PhotographerRequestDto(
    val account: String,
    val deleted: String,
    val introduction: String,
    val profileImg: String,
    val category : CategoryDto,
    val places : List<AddressDto>
)