package com.ssafy.smile.data.remote.model

import com.ssafy.smile.domain.model.ReviewDomainDto
import java.io.File
import java.sql.Timestamp

data class ReviewResponseDto(
    val score : Float,
    val content : String,
    val photoUrl : String,
    val timestamp: Timestamp,
)

