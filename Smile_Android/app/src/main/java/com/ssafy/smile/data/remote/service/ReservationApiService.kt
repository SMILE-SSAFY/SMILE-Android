package com.ssafy.smile.data.remote.service

import com.ssafy.smile.data.remote.model.*
import retrofit2.Response
import retrofit2.http.*

interface ReservationApiService {
    @GET("/api/reservation/{photographerId}")
    suspend fun getPhotographerReservationInfo(@Path("photographerId") photographerId: Long): Response<ReservationPhotographerDto>

    @POST("/api/reservation")
    suspend fun postReservation(@Body reservationRequestDto: ReservationRequestDto): Response<ReservationResponseDto>

    @GET("/api/reservation/photographer")
    suspend fun getPhotographerReservationList(): Response<ArrayList<ReservationListDto>>

    @GET("/api/reservation/user")
    suspend fun getCustomerReservationList(): Response<ArrayList<ReservationListDto>>

    @PUT("/api/reservation/status/{reservationId}")
    suspend fun changeReservationStatus(@Path("reservationId") reservationId: Long, @Body reservationChangeRequestDto: ReservationChangeRequestDto): Response<Any>

    @PUT("/api/reservation/cancel/{reservationId}")
    suspend fun cancelReservation(@Path("reservationId") reservationId: Long): Response<Any>
}