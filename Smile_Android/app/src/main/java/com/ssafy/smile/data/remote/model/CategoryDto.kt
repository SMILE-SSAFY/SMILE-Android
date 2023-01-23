package com.ssafy.smile.data.remote.model

import com.ssafy.smile.domain.model.CategoryDto
import com.ssafy.smile.domain.model.PlaceDto

data class CategoryDto(
    val categoryName: String,
    val categoryPrice: String,
    val description: String,
){
    fun CategoryDto() = CategoryDto(false, categoryName, categoryPrice, description)
}
