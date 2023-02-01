package com.ssafy.smile.data.remote.service

import com.ssafy.smile.data.remote.model.ReservationListDto
import com.ssafy.smile.data.remote.model.ReservationPhotographerDto
import com.ssafy.smile.data.remote.model.ReservationRequestDto
import com.ssafy.smile.data.remote.model.ReservationResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ReservationApiService {
    @GET("/api/reservation/{photographerId}")
    suspend fun getPhotographerReservationInfo(@Path("photographerId") photographerId: Long): Response<ReservationPhotographerDto>

    @POST("/api/reservation")
    suspend fun postReservation(@Body reservationRequestDto: ReservationRequestDto): Response<ReservationResponseDto>

    @GET("/api/reservation/photographer")
    suspend fun getPhotographerReservationList(): Response<ArrayList<ReservationListDto>>
}