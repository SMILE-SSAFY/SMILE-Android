package com.ssafy.smile.domain.model

data class PostSearchDomainDto(
    val clusterId : Long,
    val id : Long,
    val photographerName : String,
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
    fun makeToRVDto() = PostSearchRVDomainDto(Types.PagingRVType.CONTENT, this)
}

data class PostSearchRVDomainDto(
    val type : Types.PagingRVType,
    val postSearchDto: PostSearchDomainDto?=null
)