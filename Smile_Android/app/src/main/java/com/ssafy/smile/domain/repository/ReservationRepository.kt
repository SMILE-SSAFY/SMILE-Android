package com.ssafy.smile.domain.repository

import com.ssafy.smile.data.remote.model.ReservationChangeRequestDto
import com.ssafy.smile.data.remote.model.ReservationListDto
import com.ssafy.smile.data.remote.model.ReservationRequestDto
import retrofit2.Response

interface ReservationRepository {
    suspend fun getPhotographerReservationInfo(photographerId: Long)
    suspend fun postReservation(reservationRequestDto: ReservationRequestDto)
    suspend fun getPhotographerReservationList()
    suspend fun getCustomerReservationList()
    suspend fun changeReservationStatus(reservationId: Long, reservationChangeRequestDto: ReservationChangeRequestDto)
    suspend fun cancelReservation(reservationId: Long)
}