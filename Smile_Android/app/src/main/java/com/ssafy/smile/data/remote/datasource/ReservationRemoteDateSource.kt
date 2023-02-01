package com.ssafy.smile.data.remote.datasource

import com.ssafy.smile.data.remote.model.ReservationListDto
import com.ssafy.smile.data.remote.model.ReservationPhotographerDto
import com.ssafy.smile.data.remote.model.ReservationRequestDto
import com.ssafy.smile.data.remote.model.ReservationResponseDto
import retrofit2.Response

interface ReservationRemoteDateSource {
    suspend fun getPhotographerReservationInfo(photographerId: Long): Response<ReservationPhotographerDto>
    suspend fun postReservation(reservationRequestDto: ReservationRequestDto): Response<ReservationResponseDto>
    suspend fun getPhotographerReservationList(): Response<ArrayList<ReservationListDto>>
}