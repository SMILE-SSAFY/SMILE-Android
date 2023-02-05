package com.ssafy.smile.data.remote.service

import com.ssafy.smile.data.remote.model.*
import okhttp3.MultipartBody
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


    @GET("/api/reservation/review/list/{photographerId}")
    suspend fun getPhotographerReviewList(@Path("photographerId") photographerId: Long) : Response<List<PhotographerReviewDto>>

    @Multipart
    @POST("/api/reservation/review/{reservationId}")
    suspend fun postReview(@Path("reservationId") reservationId: Long,
                           @Part("score") score : Float,
                           @Part("content") content : String,
                           @Part image : MultipartBody.Part) : Response<Any>

    @GET("/api/reservation/review/{reviewId}")
    suspend fun getReview(@Path("reviewId") reviewId: Long) : Response<ReviewResponseDto>

    @DELETE("/api/reservation/review/{reviewId}")
    suspend fun deleteReview(@Path("reviewId") reviewId: Long) : Response<Any>
}