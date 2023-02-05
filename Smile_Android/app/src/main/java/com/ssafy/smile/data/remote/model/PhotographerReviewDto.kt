package com.ssafy.smile.data.remote.model

import com.ssafy.smile.domain.model.PhotographerReviewDomainDto

data class PhotographerReviewDto (
        val reviewId : Long,
        val userId : String,
        val score : Double,
        val userName : String,
        val content : String,
        val photoUrl : String,
        val createdAt : String,
        val isMe : Boolean
        ){
        fun makeToDomainDto() = PhotographerReviewDomainDto(reviewId, userId, score.toFloat(), userName, content, photoUrl, createdAt, isMe)
}
