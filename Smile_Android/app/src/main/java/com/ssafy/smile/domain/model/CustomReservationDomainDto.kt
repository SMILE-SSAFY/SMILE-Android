package com.ssafy.smile.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CustomReservationDomainDto(
    val opposite: String = "",
    val name: String = "",
    val phoneNumber: String = "",
    val resDate: String = "",
    val startTime: String = "",
    val location: String = "",
    val category: String = "",
    val cost: String = ""
): Parcelable