package com.ssafy.smile.data.remote.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.data.remote.datasource.ReservationRemoteDateSource
import com.ssafy.smile.data.remote.model.ReservationPhotographerDto
import com.ssafy.smile.data.remote.model.ReservationRequestDto
import com.ssafy.smile.domain.repository.ReservationRepository
import com.ssafy.smile.presentation.base.BaseRepository

class ReservationRepositoryImpl(private val reservationRemoteDateSource: ReservationRemoteDateSource): ReservationRepository, BaseRepository() {
    private val _photographerReservationInfoLiveData = MutableLiveData<NetworkUtils.NetworkResponse<ReservationPhotographerDto>>()
    val photographerReservationInfoLiveData: LiveData<NetworkUtils.NetworkResponse<ReservationPhotographerDto>>
        get() = _photographerReservationInfoLiveData

    private val _postReservationLiveData = MutableLiveData<NetworkUtils.NetworkResponse<Any>>()
    val postReservationLiveData: LiveData<NetworkUtils.NetworkResponse<Any>>
        get() = _postReservationLiveData

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
}