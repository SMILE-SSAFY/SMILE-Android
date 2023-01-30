package com.ssafy.smile.domain.repository

import com.ssafy.smile.data.remote.model.ReservationRequestDto

interface ReservationRepository {
    suspend fun getPhotographerReservationInfo(photographerId: Long)
    suspend fun postReservation(reservationRequestDto: ReservationRequestDto)
}