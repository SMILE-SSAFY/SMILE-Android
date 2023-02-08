package com.ssafy.smile.data.remote.repository

import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.common.view.sources.SingleLiveData
import com.ssafy.smile.data.remote.datasource.ReservationRemoteDateSource
import com.ssafy.smile.data.remote.model.*
import com.ssafy.smile.domain.repository.ReservationRepository
import com.ssafy.smile.presentation.base.BaseRepository
import okhttp3.MultipartBody

class ReservationRepositoryImpl(private val reservationRemoteDateSource: ReservationRemoteDateSource): ReservationRepository, BaseRepository() {
    private val _photographerReservationInfoLiveData = SingleLiveData<NetworkUtils.NetworkResponse<ReservationPhotographerDto>>(null)
    val photographerReservationInfoLiveData: SingleLiveData<NetworkUtils.NetworkResponse<ReservationPhotographerDto>>
        get() = _photographerReservationInfoLiveData

    private val _postReservationLiveData = SingleLiveData<NetworkUtils.NetworkResponse<ReservationResponseDto>>(null)
    val postReservationLiveData: SingleLiveData<NetworkUtils.NetworkResponse<ReservationResponseDto>>
        get() = _postReservationLiveData

    private val _photographerReservationListLiveData = SingleLiveData<NetworkUtils.NetworkResponse<ArrayList<ReservationListDto>>>(null)
    val photographerReservationListLiveData: SingleLiveData<NetworkUtils.NetworkResponse<ArrayList<ReservationListDto>>>
        get() = _photographerReservationListLiveData

    private val _customerReservationListLiveData = SingleLiveData<NetworkUtils.NetworkResponse<ArrayList<ReservationListDto>>>(null)
    val customerReservationListLiveData: SingleLiveData<NetworkUtils.NetworkResponse<ArrayList<ReservationListDto>>>
        get() = _customerReservationListLiveData

    private val _changeReservationStatusLiveData = SingleLiveData<NetworkUtils.NetworkResponse<Any>>(null)
    val changeReservationStatusLiveData: SingleLiveData<NetworkUtils.NetworkResponse<Any>>
        get() = _changeReservationStatusLiveData

    private val _cancelReservationLiveData = SingleLiveData<NetworkUtils.NetworkResponse<Any>>(null)
    val cancelReservationLiveData: SingleLiveData<NetworkUtils.NetworkResponse<Any>>
        get() = _cancelReservationLiveData

    private val _getPhotographerReviewLiveData = SingleLiveData<NetworkUtils.NetworkResponse<List<PhotographerReviewDto>>>(null)
    val getPhotographerReviewLiveData: SingleLiveData<NetworkUtils.NetworkResponse<List<PhotographerReviewDto>>>
        get() = _getPhotographerReviewLiveData

    private val _postReviewLiveData = SingleLiveData<NetworkUtils.NetworkResponse<Any>>(null)
    val postReviewLiveData: SingleLiveData<NetworkUtils.NetworkResponse<Any>>
        get() = _postReviewLiveData

    private val _getReviewLiveData = SingleLiveData<NetworkUtils.NetworkResponse<ReviewResponseDto>>(null)
    val getReviewLiveData: SingleLiveData<NetworkUtils.NetworkResponse<ReviewResponseDto>>
        get() = _getReviewLiveData

    private val _deleteReviewLiveData = SingleLiveData<NetworkUtils.NetworkResponse<Any>>(null)
    val deleteReviewLiveData: SingleLiveData<NetworkUtils.NetworkResponse<Any>>
        get() = _deleteReviewLiveData


    override suspend fun getPhotographerReservationInfo(photographerId: Long) {
        safeApiCall(_photographerReservationInfoLiveData) {
            reservationRemoteDateSource.getPhotographerReservationInfo(photographerId)
        }
    }

    override suspend fun postReservation(reservationRequestDto: ReservationRequestDto) {
        safeApiCall(_postReservationLiveData) {
            reservationRemoteDateSource.postReservation(reservationRequestDto)
        }
    }

    override suspend fun getPhotographerReservationList() {
        safeApiCall(_photographerReservationListLiveData) {
            reservationRemoteDateSource.getPhotographerReservationList()
        }
    }

    override suspend fun getCustomerReservationList() {
        safeApiCall(_customerReservationListLiveData) {
            reservationRemoteDateSource.getCustomerReservationList()
        }
    }

    override suspend fun changeReservationStatus(reservationId: Long, reservationChangeRequestDto: ReservationChangeRequestDto) {
        safeApiCall(_changeReservationStatusLiveData) {
            reservationRemoteDateSource.changeReservationStatus(reservationId, reservationChangeRequestDto)
        }
    }

    override suspend fun cancelReservation(reservationId: Long) {
        safeApiCall(_cancelReservationLiveData) {
            reservationRemoteDateSource.cancelReservation(reservationId)
        }
    }

    override suspend fun postReview(reservationId: Long, score: Float, content: String, image: MultipartBody.Part){
        safeApiCall(_postReviewLiveData){
            reservationRemoteDateSource.postReview(reservationId, score, content, image)
        }
    }

    override suspend fun getPhotographerReviewList(photographerId: Long) {
        safeApiCall(_getPhotographerReviewLiveData){
            reservationRemoteDateSource.getPhotographerReviewList(photographerId)
        }
    }

    override suspend fun getReview(reviewId: Long) {
        safeApiCall(_getReviewLiveData){
            reservationRemoteDateSource.getReview(reviewId)
        }
    }

    override suspend fun deleteReview(reviewId: Long) {
        safeApiCall(_deleteReviewLiveData){
            reservationRemoteDateSource.deleteReview(reviewId)
        }
    }
}