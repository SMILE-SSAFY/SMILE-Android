package com.ssafy.smile.data.remote.model

import com.ssafy.smile.domain.model.PostSearchDomainDto


data class PostSearchRequestDto(
    val isEndPage : Boolean,
    val articleRedisList : List<PostSearchDto>
)

data class PostSearchDto(
    val clusterId : Long,
    val id : Long,
    val photographerName : String,
    val photographerId: Long,
    val latitude : Double,
    val longitude : Double,
    val distance : Double,
    val isHeart : Boolean,
    val hearts : Int,
    val detailAddress : String,
    val createdAt : String,
    val category : String,
    val photoUrl : String
){
    fun makeToDomainDto() : PostSearchDomainDto = PostSearchDomainDto(clusterId, id, photographerName, photographerId, latitude, longitude, distance, isHeart, hearts, detailAddress, createdAt, category, photoUrl)
}


