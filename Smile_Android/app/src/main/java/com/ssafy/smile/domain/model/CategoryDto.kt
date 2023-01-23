package com.ssafy.smile.domain.model

data class CategoryDto(
    var isEmpty:Boolean = true,
    var categoryName: String?=null,
    var categoryPrice: String?=null,
    var description: String?=null,
)
