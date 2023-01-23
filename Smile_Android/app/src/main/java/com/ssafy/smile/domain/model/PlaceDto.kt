package com.ssafy.smile.domain.model

import com.ssafy.smile.common.util.getString

data class PlaceDto (
    var isEmpty:Boolean = true,
    var first : String?=null,
    var second : String?=null,
    var place : String? = null
){
    fun getPlaceString() : String = first.getString() +  " " + second.getString()
}
