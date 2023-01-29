package com.ssafy.smile.domain.model

import com.ssafy.smile.common.util.getString
import com.ssafy.smile.data.remote.model.PlaceDto

data class PlaceDomainDto (
    var isEmpty:Boolean = true,
    var first : String? = null,
    var second : String? = null,
    var firstId : Int = 0,
    var secondId : Int = 0,
){
    fun toPlaceDto() : PlaceDto = PlaceDto(makePlaceId(), first!!, second!!)
    private fun makePlaceId() : String = firstId.toString() + secondId.toString()
}
