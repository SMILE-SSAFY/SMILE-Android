package com.ssafy.smile.data.remote.model

import android.os.Parcelable
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