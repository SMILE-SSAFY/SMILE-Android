package com.ssafy.smile.domain.model

data class PostSearchDomainDto(
    val clusterId : Long,
    val articleId: Long,
    val photographerName : String,
    val photographerId: Long,
    val latitude : Double,
    val longitude : Double,
    val distance : Double,
    var isHeart : Boolean,
    var hearts : Int,
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