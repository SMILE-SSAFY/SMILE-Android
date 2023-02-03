package com.ssafy.smile.data.remote.datasource

import com.ssafy.smile.data.remote.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Path

interface ReservationRemoteDateSource {
    suspend fun getPhotographerReservationInfo(photographerId: Long): Response<ReservationPhotographerDto>
    suspend fun postReservation(reservationRequestDto: ReservationRequestDto): Response<ReservationResponseDto>
    suspend fun getPhotographerReservationList(): Response<ArrayList<ReservationListDto>>
    suspend fun getCustomerReservationList(): Response<ArrayList<ReservationListDto>>
    suspend fun changeReservationStatus(reservationId: Long, reservationChangeRequestDto: ReservationChangeRequestDto): Response<Any>
    suspend fun cancelReservation(reservationId: Long): Response<Any>
}