package com.ssafy.smile.data.remote.model

data class ReservationRequestDto(
    var photographerId: Long = 0,
    var price: Int = 0,
    var email: String = "",
    var categoryName: String = "",
    var options: String = "",
    var address: String = "",
    var detailAddress: String = "",
    var date: String = "",
    var time: String = ""
)
