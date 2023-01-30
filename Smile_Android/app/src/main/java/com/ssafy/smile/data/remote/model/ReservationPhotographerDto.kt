package com.ssafy.smile.data.remote.model

import java.util.Date

data class ReservationPhotographerDto(
    val days: ArrayList<Date> = arrayListOf(),
    val places: ArrayList<String> = arrayListOf(),
    val categories: ArrayList<ResCategory> = arrayListOf()
)

data class ResCategory(
    val categoryId: Long = 0,
    val categoryName: String = "",
    //TODO : 네이밍 바꾸기
    val categoryDetail: ArrayList<ResCategoryDetail> = arrayListOf()
)

data class ResCategoryDetail(
    val price: Int = 0,
    val description: String = ""
)