package com.ssafy.smile.data.remote.model

import com.ssafy.smile.common.util.CommonUtils
import com.ssafy.smile.domain.model.CustomPhotographerDomainDto

data class SearchPhotographerResponseDto(
    val photographerId: Long = 0,
    val name: String = "",
    val profileImg: String = "",
    val places: ArrayList<Place> = arrayListOf(),
    val categories: ArrayList<CategoryNoDes> = arrayListOf(),
    val hasHeart: Boolean = false,
    val heart: Int = 0
){
    fun toCustomPhotographerDomainDto(): CustomPhotographerDomainDto {
        val categoryNames = arrayListOf<String>()
        val categoryPrices = arrayListOf<Int>()
        categories.forEach { data ->
            categoryNames.add(data.name)
            categoryPrices.add(data.price.toInt())
        }

        return CustomPhotographerDomainDto(
            photographerId,
            profileImg,
            CommonUtils.getCategoryName(categoryNames),
            name,
            CommonUtils.getPlace(places),
            CommonUtils.getCategoryPrice(categoryPrices),
            hasHeart,
            heart
        )
    }
}