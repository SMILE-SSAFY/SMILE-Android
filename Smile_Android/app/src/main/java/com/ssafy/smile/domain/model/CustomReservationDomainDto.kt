package com.ssafy.smile.domain.model

data class CustomReservationDomainDto(
    val curDate: String = "",
    val opposite: String = "",
    val name: String = "",
    val phoneNumber: String = "",
    val resDate: String = "",
    val startTime: String = "",
    val location: String = "",
    val category: String = "",
    val cost: String = ""
)