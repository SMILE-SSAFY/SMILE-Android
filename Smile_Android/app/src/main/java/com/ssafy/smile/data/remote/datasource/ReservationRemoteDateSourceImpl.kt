package com.ssafy.smile.data.remote.datasource

import com.ssafy.smile.data.remote.model.ReservationListDto
import com.ssafy.smile.data.remote.model.ReservationPhotographerDto
import com.ssafy.smile.data.remote.model.ReservationRequestDto
import com.ssafy.smile.data.remote.model.ReservationResponseDto
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
}