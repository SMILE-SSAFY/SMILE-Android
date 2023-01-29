package com.ssafy.smile.domain.model

import com.ssafy.smile.data.remote.model.CategoryDto

data class CategoryDomainDto(
    var isEmpty:Boolean = true,
    var name: String?=null,
    var price: Int = 0,
    var description: String?=null,
    var categoryId : Int = 0
){
    fun toCategoryDto() = CategoryDto(categoryId, name!!, price, description!!)
}

