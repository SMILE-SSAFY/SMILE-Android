package com.ssafy.smile.data.remote.model

import com.ssafy.smile.common.util.CommonUtils
import com.ssafy.smile.domain.model.CustomPhotographerDomainDto

data class PhotographerRecommendResponseDto(
    val isHeartEmpty: Boolean = false,
    val photographerInfoList: ArrayList<RecommendPhotographer> = arrayListOf(),
){
    fun toCustomPhotographerDomainDto(idx: Int): CustomPhotographerDomainDto {
        val photographerInfo = photographerInfoList[idx]

        val categoryNames = arrayListOf<String>()
        val categoryPrices = arrayListOf<Int>()
        photographerInfo.categories.forEach { category ->
            categoryNames.add(category.name)
            categoryPrices.add(category.price.toInt())
        }

        val sPlaces = arrayListOf<String>()
        photographerInfo.places.forEach { place ->
            sPlaces.add(place.place)
        }

        return CustomPhotographerDomainDto(
            photographerInfo.photographerId,
            photographerInfo.profileImg,
            CommonUtils.getCategoryName(categoryNames),
            photographerInfo.name,
            CommonUtils.getPlace(sPlaces),
            CommonUtils.getCategoryPrice(categoryPrices),
            photographerInfo.hasHeart,
            photographerInfo.heart
        )
    }
}

data class RecommendPhotographer(
    val photographerId: Long = 0,
    val name: String = "",
    val profileImg: String = "",
    val places: ArrayList<Place> = arrayListOf(),
    val categories: ArrayList<CategoryNoDes> = arrayListOf(),
    val hasHeart: Boolean = false,
    val heart: Int = 0,
    val avgScore: Float = 0.0f,
    val reviewCount: Int = 0
)