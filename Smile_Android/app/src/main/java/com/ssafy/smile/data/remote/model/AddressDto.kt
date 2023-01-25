package com.ssafy.smile.data.remote.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddressDto(val detailAddress : String, val latitude : Float, val longitude : Float) : Parcelable