package com.ssafy.smile.domain.repository

import com.ssafy.smile.data.remote.model.ReservationChangeRequestDto
import com.ssafy.smile.data.remote.model.ReservationRequestDto

interface ReservationRepository {
    suspend fun getPhotographerReservationInfo(photographerId: Long)
    suspend fun postReservation(reservationRequestDto: ReservationRequestDto)
    suspend fun getPhotographerReservationList()
    suspend fun changeReservationStatus(reservationId: Long, reservationChangeRequestDto: ReservationChangeRequestDto)
    suspend fun cancelReservation(reservationId: Long)
}