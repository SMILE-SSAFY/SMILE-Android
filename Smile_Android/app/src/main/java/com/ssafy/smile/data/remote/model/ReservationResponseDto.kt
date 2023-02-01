package com.ssafy.smile.data.remote.model

import com.ssafy.smile.common.util.CommonUtils
import com.ssafy.smile.domain.model.CustomReservationDomainDto

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
