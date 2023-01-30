package com.ssafy.smile.data.remote.model

data class ReservationRequestDto(
    val photographerId: Long = 0,
    val price: Int = 0,
    val email: String = "",
    val categoryName: String = "",
    val options: String = "",
    val address: String = "",
    val detailAddress: String = "",
    val date: String = "",
    val time: String = ""
)
