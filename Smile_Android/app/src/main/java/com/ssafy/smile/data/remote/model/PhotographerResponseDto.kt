package com.ssafy.smile.data.remote.model

import android.os.Parcelable
import com.ssafy.smile.common.util.CommonUtils
import com.ssafy.smile.domain.model.AccountDomainDto
import com.ssafy.smile.domain.model.CustomPhotographerDomainDto
import com.ssafy.smile.domain.model.PhotographerRequestDomainDto
import kotlinx.parcelize.Parcelize
import java.io.File


@Parcelize
data class PhotographerResponseDto(
    val photographerId: Long,
    val name: String,
    val profileImg: String,
    val introduction: String,
    val account: String,
    val categories: List<CategoryDto>,
    val places: List<PlaceDto>,
    val price : Int,
    val description : String
) : Parcelable {

}

data class PhotographerByAddressResponseDto(
    val photographerId: Long = 0,
    val name: String = "",
    val profileImg: String? = "",
    val places: ArrayList<Place> = arrayListOf(),
    val categories: ArrayList<CategoryNoDes> = arrayListOf(),
    val hasHeart: Boolean = false,
    val heart: Int = 0,
    val score: Double = 0.0,
    val reviews: Int = 0
){
    fun toCustomPhotographerDomainDto(): CustomPhotographerDomainDto {
        val categoryNames = arrayListOf<String>()
        val categoryPrices = arrayListOf<Int>()
        categories.forEach { data ->
            categoryNames.add(data.name)
            categoryPrices.add(data.price.toInt())
        }

        val sPlaces = arrayListOf<String>()
        places.forEach { place ->
            sPlaces.add(place.place)
        }

        return CustomPhotographerDomainDto(
            photographerId,
            profileImg,
            CommonUtils.getCategoryName(categoryNames),
            name,
            CommonUtils.getPlace(sPlaces),
            CommonUtils.getCategoryPrice(categoryPrices),
            hasHeart,
            heart
        )
    }
}

data class CategoryNoDes(
    val name: String = "",
    val price: String = ""
)

data class Place (
    val place: String = ""
)