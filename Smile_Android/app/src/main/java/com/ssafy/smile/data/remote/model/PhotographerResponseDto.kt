package com.ssafy.smile.data.remote.model

import android.os.Parcelable
import com.ssafy.smile.common.util.CommonUtils
import com.ssafy.smile.domain.model.CustomPhotographerDomainDto
import com.ssafy.smile.domain.model.PortfolioDomainDto
import kotlinx.parcelize.Parcelize

@Parcelize
data class PhotographerResponseDto(
    val account: String,
    val deleted: String,
    val introduction: String,
    val profileImg: String,             // TODO : String????
    val category : CategoryDto,
    val places : List<AddressDto>
) : Parcelable

data class PhotographerByAddressResponseDto(
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

data class CategoryNoDes(
    val name: String = "",
    val price: String = ""
)