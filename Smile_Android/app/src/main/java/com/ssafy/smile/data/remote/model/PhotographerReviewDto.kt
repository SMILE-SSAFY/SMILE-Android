package com.ssafy.smile.data.remote.model

import com.ssafy.smile.domain.model.PhotographerReviewDomainDto

data class PhotographerReviewDto (
        val reviewId : Int,
        val userId : String,
        val score : Double,
        val userName : String,
        val content : String,
        val photoUrl : String?
        ){
        fun makeToDomainDto() = PhotographerReviewDomainDto(reviewId, userId, score, userName, content, photoUrl)
}
