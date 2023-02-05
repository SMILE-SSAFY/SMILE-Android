package com.ssafy.smile.data.remote.datasource

import com.ssafy.smile.data.remote.model.*
import okhttp3.MultipartBody
import retrofit2.Response

interface ReservationRemoteDateSource {
    suspend fun getPhotographerReservationInfo(photographerId: Long): Response<ReservationPhotographerDto>
    suspend fun postReservation(reservationRequestDto: ReservationRequestDto): Response<ReservationResponseDto>
    suspend fun getPhotographerReservationList(): Response<ArrayList<ReservationListDto>>
    suspend fun getCustomerReservationList(): Response<ArrayList<ReservationListDto>>
    suspend fun changeReservationStatus(reservationId: Long, reservationChangeRequestDto: ReservationChangeRequestDto): Response<Any>
    suspend fun cancelReservation(reservationId: Long): Response<Any>
    suspend fun postReview(reservationId: Long, score:Float, content:String, image:MultipartBody.Part): Response<Any>
    suspend fun getPhotographerReviewList(photographerId: Long) : Response<List<PhotographerReviewDto>>
    suspend fun getReview(reviewId: Long) : Response<ReviewResponseDto>
    suspend fun deleteReview(reviewId: Long) : Response<Any>
}