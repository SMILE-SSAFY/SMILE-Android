package com.ssafy.smile.presentation.viewmodel.mypage

import androidx.lifecycle.viewModelScope
import com.ssafy.smile.Application
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.common.view.sources.SingleLiveData
import com.ssafy.smile.data.remote.model.ReservationChangeRequestDto
import com.ssafy.smile.data.remote.model.ReservationListDto
import com.ssafy.smile.presentation.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PhotographerReservationListViewModel: BaseViewModel() {
    private val reservationRepository = Application.repositoryInstances.getReservationRepository()

    val getPhotographerReservationListResponse: SingleLiveData<NetworkUtils.NetworkResponse<ArrayList<ReservationListDto>>>
        get() = reservationRepository.photographerReservationListLiveData

    fun getPhotographerReservationList() = viewModelScope.launch{
        reservationRepository.getPhotographerReservationList()
    }

    val changeReservationStatusResponse: SingleLiveData<NetworkUtils.NetworkResponse<Any>>
        get() = reservationRepository.changeReservationStatusLiveData

    fun changeReservationStatus(reservationId: Long, reservationChangeRequestDto: ReservationChangeRequestDto) = viewModelScope.launch(Dispatchers.IO){
        reservationRepository.changeReservationStatus(reservationId, reservationChangeRequestDto)
    }

    val cancelReservationResponse: SingleLiveData<NetworkUtils.NetworkResponse<Any>>
        get() = reservationRepository.cancelReservationLiveData

    fun cancelReservation(reservationId: Long) = viewModelScope.launch(Dispatchers.IO){
        reservationRepository.cancelReservation(reservationId)
    }
}