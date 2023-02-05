package com.ssafy.smile.data.remote.model

import com.ssafy.smile.domain.model.PostSearchDomainDto
import java.util.Date

data class ClusterPostDto(
    val clusterId : Long,
    val articleSearchDtoList : List<PostSearchDto>
)

data class PostSearchDto(
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
){
    fun makeToDomainDto() : PostSearchDomainDto = PostSearchDomainDto(articleId, photographerName, latitude, longitude, distance, hearts, detailAddress, createdAt, category, photoUrl)
}


