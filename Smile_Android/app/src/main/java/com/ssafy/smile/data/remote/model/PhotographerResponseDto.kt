package com.ssafy.smile.data.remote.model

import android.os.Parcelable
import com.ssafy.smile.common.util.CommonUtils
import com.ssafy.smile.domain.model.CustomPhotographerDomainDto
import kotlinx.parcelize.Parcelize
import java.io.File

@Parcelize
data class PhotographerResponseDto(
    val profileImg: File,
    val photographerDto : PhotographerDto
) : Parcelable


data class PhotographerByAddressResponseDto(
    val photoUrl: String = "",
    val photographerList: ArrayList<PhotographerListByAddress> = arrayListOf()
){
    fun toCustomPhotographerDomainDto(idx: Int): CustomPhotographerDomainDto {
        val photographerInfo = photographerList[idx]

        val categoryNames = arrayListOf<String>()
        val categoryPrices = arrayListOf<Int>()
        photographerInfo.categories.forEach { data ->
            if (data.name !in categoryNames) {
                categoryNames.add(data.name)
            }
            categoryPrices.add(data.price.toInt())
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

data class PhotographerListByAddress(
    val photographerId: Long = 0,
    val name: String = "",
    val profileImg: String? = "",
    val places: ArrayList<Place> = arrayListOf(),
    val categories: ArrayList<CategoryNoDes> = arrayListOf(),
    val hasHeart: Boolean = false,
    val heart: Int = 0,
    val score: Double = 0.0,
    val reviews: Int = 0
)

data class CategoryNoDes(
    val name: String = "",
    val price: String = ""
)

data class Place (
    val place: String = ""
)