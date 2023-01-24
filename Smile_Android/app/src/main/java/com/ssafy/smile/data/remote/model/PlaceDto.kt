package com.ssafy.smile.data.remote.model

import com.ssafy.smile.domain.model.PlaceDomainDto

data class PlaceDto (
    val first : String,
    val second : String,
    val place : String
){
    fun toPlaceDto() = PlaceDomainDto(false, first, second, place)
}