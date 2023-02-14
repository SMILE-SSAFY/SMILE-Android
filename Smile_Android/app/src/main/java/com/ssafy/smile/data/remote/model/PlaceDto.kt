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
    fun toPlaceDomainDto() : PlaceDomainDto {
        val (firstId, secondId) = makePlaceId()
        return PlaceDomainDto(false, first, second, firstId, secondId)
    }
    private fun makePlaceId() : Pair<Int,Int>{
        return if (placeId.length==4) Pair(placeId.substring(0 until 2).toInt(), placeId.substring(2).toInt())
        else Pair(placeId.substring(0 until 1).toInt(), placeId.substring(1).toInt())
    }
}