package com.ssafy.smile.data.remote.model

import com.ssafy.smile.common.util.CommonUtils
import com.ssafy.smile.domain.model.CustomReservationDomainDto
import java.util.*
import kotlin.collections.ArrayList

data class ReservationResponseDto(
    val id: Long = 0,
    val photographerName: String = "",
    val phoneNumber: String = "",
    val status: String = "",
    val price: Int = 0,
    val categoryName: String = "",
    val options: String = "",
    val email: String = "",
    val date: String = "",
    val time: String = "",
    val address: String = "",
    val createdAt: String = "",
    val reviewed: Boolean = false
){
    fun toCustomReservationDomainDto(): CustomReservationDomainDto {
        return CustomReservationDomainDto(
            id,
            "작가님",
            photographerName,
            phoneNumber,
            toDomainDate(date),
            toDomainTime(time),
            address,
            categoryName,
            "${CommonUtils.makeComma(price)} 원",
            status
        )
    }

    private fun toDomainDate(date: String): String {
        val arr = date.split("-")
        return "${arr[0]}년 ${arr[1]}월 ${arr[2]}일"
    }

    private fun toDomainTime(time: String): String {
        val arr = time.split(":")
        val sTime = "${arr[0].toInt() % 12}시 ${arr[1]}분"

        return if (arr[0].toInt() > 12) {
            "오후 $sTime"
        } else {
            "오전 $sTime"
        }
    }
}

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

data class ReservationListDto(
    val reservationId: Long = 0,
    val address: String = "",
    val categoryName: String = "",
    val options: String = "",
    val date: String = "",
    val phoneNumber: String = "",
    val price: Int = 0,
    val reviewed: Boolean = false,
    val status: String = "",
    val time: String = "",
    val name: String = "",
) {
    fun toCustomReservationDomainDto(): CustomReservationDomainDto {
        return CustomReservationDomainDto(
            reservationId,
            "고객님",
            name,
            phoneNumber,
            date,
            time,
            address,
            CommonUtils.combineCategoryAndOption(categoryName, options),
            "${CommonUtils.makeComma(price)} 원",
            status
        )
    }
}