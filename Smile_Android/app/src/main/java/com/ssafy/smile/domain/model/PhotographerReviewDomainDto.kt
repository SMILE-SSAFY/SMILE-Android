package com.ssafy.smile.domain.model

import com.ssafy.smile.data.remote.model.PhotographerReviewDto

data class PhotographerReviewDomainDto(
    val reviewId : Int,
    val userId : String,
    val score : Double,
    val userName : String,
    val content : String,
    val photoUrl : String?=null
){
    fun makeToDto() = PhotographerReviewDto(reviewId, userId, score, userName, content, photoUrl)
}
