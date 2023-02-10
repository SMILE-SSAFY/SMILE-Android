package com.ssafy.smile.data.remote.model

import android.os.Parcelable
import com.ssafy.smile.domain.model.PlaceDomainDto
import kotlinx.parcelize.Parcelize


@Parcelize
data class PlaceDto (
    val placeId : String="",
    val first : String,
    val second: String
):Parcelable{
    fun toPlaceDto() : PlaceDomainDto = PlaceDomainDto(false, first, second)
}