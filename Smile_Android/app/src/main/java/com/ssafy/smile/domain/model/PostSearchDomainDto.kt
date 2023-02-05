package com.ssafy.smile.domain.model

import java.util.*

data class PostSearchDomainDto(
    val articleId : Long,
    val photographerName : String,
    val latitude : Double,
    val longitude : Double,
    val distance : Double,
    val hearts : Int,
    val detailAddress : String,
    val createdAt : Date,
    val category : String,
    val photoUrl : String
)