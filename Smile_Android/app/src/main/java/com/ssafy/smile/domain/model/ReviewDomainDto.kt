package com.ssafy.smile.domain.model

import com.ssafy.smile.data.remote.model.ReviewRequestDto
import java.io.File
import java.sql.Timestamp

data class ReviewDomainDto(
    var score : Float?=null,
    var content : String?=null,
    var image : File?=null,
    var timeStamp : Timestamp?=null
){
    fun makeToDto() = ReviewRequestDto(score!!, content!!, image!!, timeStamp!!)
}