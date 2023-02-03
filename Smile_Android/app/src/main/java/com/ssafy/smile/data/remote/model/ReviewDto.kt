package com.ssafy.smile.data.remote.model

import com.ssafy.smile.domain.model.ReviewDomainDto
import java.io.File
import java.sql.Timestamp

data class ReviewDto(
    val score : Float,
    val content : String,
    val image : File?,
    val timestamp: Timestamp,
){
    fun makeToDomainDto() = ReviewDomainDto(score, content, image)
}

