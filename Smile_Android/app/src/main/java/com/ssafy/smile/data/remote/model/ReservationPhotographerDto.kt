package com.ssafy.smile.data.remote.model

import java.util.Date

data class ReservationPhotographerDto(
    val days: ArrayList<Date> = arrayListOf(),
    val places: ArrayList<Place> = arrayListOf(),
    val categories: ArrayList<ResCategory> = arrayListOf()
)

data class ResCategory(
    val categoryId: Long = 0,
    val categoryName: String = "",
    val details: ArrayList<ResCategoryDetail> = arrayListOf()
)

data class ResCategoryDetail(
    val price: Int = 0,
    val options: String = ""
)