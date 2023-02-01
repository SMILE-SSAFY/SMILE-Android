package com.ssafy.smile.data.remote.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.data.remote.datasource.ReservationRemoteDateSource
import com.ssafy.smile.data.remote.model.*
import com.ssafy.smile.domain.repository.ReservationRepository
import com.ssafy.smile.presentation.base.BaseRepository

class ReservationRepositoryImpl(private val reservationRemoteDateSource: ReservationRemoteDateSource): ReservationRepository, BaseRepository() {
    private val _photographerReservationInfoLiveData = MutableLiveData<NetworkUtils.NetworkResponse<ReservationPhotographerDto>>()
    val photographerReservationInfoLiveData: LiveData<NetworkUtils.NetworkResponse<ReservationPhotographerDto>>
        get() = _photographerReservationInfoLiveData

    private val _postReservationLiveData = MutableLiveData<NetworkUtils.NetworkResponse<ReservationResponseDto>>()
    val postReservationLiveData: LiveData<NetworkUtils.NetworkResponse<ReservationResponseDto>>
        get() = _postReservationLiveData

    private val _photographerReservationListLiveData = MutableLiveData<NetworkUtils.NetworkResponse<ArrayList<ReservationListDto>>>()
    val photographerReservationListLiveData: LiveData<NetworkUtils.NetworkResponse<ArrayList<ReservationListDto>>>
        get() = _photographerReservationListLiveData

    private val _changeReservationStatusLiveData = MutableLiveData<NetworkUtils.NetworkResponse<Any>>()
    val changeReservationStatusLiveData: LiveData<NetworkUtils.NetworkResponse<Any>>
        get() = _changeReservationStatusLiveData

    private val _cancelReservationLiveData = MutableLiveData<NetworkUtils.NetworkResponse<Any>>()
    val cancelReservationLiveData: LiveData<NetworkUtils.NetworkResponse<Any>>
        get() = _cancelReservationLiveData

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
}