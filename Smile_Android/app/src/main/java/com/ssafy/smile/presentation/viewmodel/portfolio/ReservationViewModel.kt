package com.ssafy.smile.presentation.viewmodel.portfolio

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.smile.Application
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.data.remote.model.ReservationPhotographerDto
import com.ssafy.smile.data.remote.model.ReservationRequestDto
import com.ssafy.smile.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class ReservationViewModel: BaseViewModel() {
    private val reservationRepository = Application.repositoryInstances.getReservationRepository()

    // 작가 예약 정보 조회 결과를 관리하는 LiveData
    val photographerReservationResponse: LiveData<NetworkUtils.NetworkResponse<ReservationPhotographerDto>>
        get() = reservationRepository.photographerReservationInfoLiveData

    // 작가 예약 정보 조회를 수행하는 함수
    fun getPhotographerReservationInfo(photographerId: Long) {
        viewModelScope.launch {
            reservationRepository.getPhotographerReservationInfo(photographerId)
        }
    }

    // 예약 결과를 관리하는 LiveData
    val postReservationResponse: LiveData<NetworkUtils.NetworkResponse<Any>>
        get() = reservationRepository.postReservationLiveData

    // 예약을 수행하는 함수
    fun postReservation(reservationRequestDto: ReservationRequestDto) {
        viewModelScope.launch {
            reservationRepository.postReservation(reservationRequestDto)
        }
    }
}