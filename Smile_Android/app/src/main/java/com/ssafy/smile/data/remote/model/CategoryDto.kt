package com.ssafy.smile.data.remote.model

import android.os.Parcelable
import com.ssafy.smile.domain.model.CategoryDto
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryDto(
    val categoryName: String,
    val categoryPrice: Int,
    val description: String,
) : Parcelable
{
    fun CategoryDto() = CategoryDto(false, categoryName, categoryPrice.toString(), description)
}
