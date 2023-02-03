package com.ssafy.smile.domain.repository

import com.ssafy.smile.data.remote.model.ReservationChangeRequestDto
import com.ssafy.smile.data.remote.model.ReservationRequestDto
import com.ssafy.smile.data.remote.model.ReviewDto
import okhttp3.MultipartBody
import retrofit2.Response

interface ReservationRepository {
    suspend fun getPhotographerReservationInfo(photographerId: Long)
    suspend fun postReservation(reservationRequestDto: ReservationRequestDto)
    suspend fun getPhotographerReservationList()
    suspend fun getCustomerReservationList()
    suspend fun changeReservationStatus(reservationId: Long, reservationChangeRequestDto: ReservationChangeRequestDto)
    suspend fun cancelReservation(reservationId: Long)
    suspend fun postReview(reservationId: Long, score: Float, content: String, image: MultipartBody.Part)
    suspend fun getPhotographerReviewList(photographerId: Long)
    suspend fun getReview(reviewId: Long)
    suspend fun deleteReview(reviewId: Long)
}
