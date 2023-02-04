package com.ssafy.smile.domain.model

data class PhotographerReviewDomainDto(
    val reviewId : Long,
    val userId : String,
    val score : Float,
    val userName : String,
    val content : String,
    val photoUrl : String,
    val createdAt : String,
    val isMe : Boolean
)