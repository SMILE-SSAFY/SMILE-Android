package com.ssafy.smile.data.remote.datasource

import com.ssafy.smile.data.remote.model.*
import com.ssafy.smile.data.remote.service.ReservationApiService
import okhttp3.MultipartBody
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

    override suspend fun getPhotographerReviewList(photographerId: Long): Response<List<PhotographerReviewDto>> {
        return reservationApiService.getPhotographerReviewList(photographerId)
    }

    override suspend fun postReview(reservationId: Long, score: Float, content: String, image: MultipartBody.Part): Response<Any> {
        return reservationApiService.postReview(reservationId, score, content, image)
    }

    override suspend fun getReview(reviewId: Long): Response<ReviewResponseDto> {
        return reservationApiService.getReview(reviewId)
    }

    override suspend fun deleteReview(reviewId: Long): Response<Any> {
        return reservationApiService.deleteReview(reviewId)
    }

}