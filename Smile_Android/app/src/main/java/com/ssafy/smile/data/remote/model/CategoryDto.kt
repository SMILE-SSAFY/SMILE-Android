package com.ssafy.smile.data.remote.model

import android.os.Parcelable
import com.ssafy.smile.domain.model.CategoryDomainDto
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryDto(
    val categoryId : Int,
    val name: String,
    val price: Int,
    val description: String,
) : Parcelable
{
    fun toCategoryDto() = CategoryDomainDto(false, name, price, description)
}
