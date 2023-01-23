package com.ssafy.smile.data.remote.model

import com.ssafy.smile.domain.model.PlaceDto

data class PlaceDto (
    val first : String,
    val second : String,
    val place : String
){
    fun toPlaceDto() = PlaceDto(false, first, second, place)
}