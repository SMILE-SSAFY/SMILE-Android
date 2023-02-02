package com.ssafy.smile.data.remote.datasource

import com.ssafy.smile.data.remote.model.*
import com.ssafy.smile.data.remote.service.ReservationApiService
import retrofit2.Response

class ReservationRemoteDateSourceImpl(private val reservationApiService: ReservationApiService): ReservationRemoteDateSource {
    override suspend fun getPhotographerReservationInfo(photographerId: Long): Response<ReservationPhotographerDto> {
        return reservationApiService.getPhotographerReservationInfo(photographerId)
    }

    override suspend fun postReservation(reservationRequestDto: ReservationRequestDto): Response<ReservationResponseDto> {
        return reservationApiService.postReservation(reservationRequestDto)
    }

    override suspend fun getPhotographerReservationList(): Response<ArrayList<ReservationListDto>> {
        return reservationApiService.getPhotographerReservationList()
    }

    override suspend fun getCustomerReservationList(): Response<ArrayList<ReservationListDto>> {
        return reservationApiService.getCustomerReservationList()
    }

    override suspend fun changeReservationStatus(reservationId: Long, reservationChangeRequestDto: ReservationChangeRequestDto): Response<Any> {
        return reservationApiService.changeReservationStatus(reservationId, reservationChangeRequestDto)
    }
    override suspend fun cancelReservation(reservationId: Long): Response<Any> {
        return reservationApiService.cancelReservation(reservationId)
    }
}