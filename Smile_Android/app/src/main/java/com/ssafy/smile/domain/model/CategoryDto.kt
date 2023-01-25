package com.ssafy.smile.domain.model

import com.ssafy.smile.data.remote.model.CategoryDto

data class CategoryDto(
    var isEmpty:Boolean = true,
    var categoryName: String?=null,
    var categoryPrice: String?=null,
    var description: String?=null,
){
    fun CategoryDto() = CategoryDto(categoryName!!, categoryPrice!!.toInt(), description!!)
}

