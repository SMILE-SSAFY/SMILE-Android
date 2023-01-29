package com.ssafy.smile.data.remote.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class PhotographerDto(
    val account: String,
    val introduction: String,
    val categories: List<CategoryDto>,
    val places: List<PlaceDto>,
    //val heart: Int=0
) : Parcelable {

}
