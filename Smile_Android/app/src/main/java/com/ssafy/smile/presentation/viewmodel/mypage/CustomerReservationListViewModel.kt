package com.ssafy.smile.presentation.viewmodel.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.smile.Application
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.data.remote.model.ReservationChangeRequestDto
import com.ssafy.smile.data.remote.model.ReservationListDto
import com.ssafy.smile.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class CustomerReservationListViewModel: BaseViewModel() {
    private val reservationRepository = Application.repositoryInstances.getReservationRepository()

    val getCustomerReservationListResponse: LiveData<NetworkUtils.NetworkResponse<ArrayList<ReservationListDto>>>
        get() = reservationRepository.customerReservationListLiveData

    fun getCustomerReservationList() = viewModelScope.launch{
        reservationRepository.getCustomerReservationList()
    }

    val changeReservationStatusResponse: LiveData<NetworkUtils.NetworkResponse<Any>>
        get() = reservationRepository.changeReservationStatusLiveData

    fun changeReservationStatus(reservationId: Long, reservationChangeRequestDto: ReservationChangeRequestDto) = viewModelScope.launch{
        reservationRepository.changeReservationStatus(reservationId, reservationChangeRequestDto)
    }

    val cancelReservationResponse: LiveData<NetworkUtils.NetworkResponse<Any>>
        get() = reservationRepository.cancelReservationLiveData

    fun cancelReservation(reservationId: Long) = viewModelScope.launch{
        reservationRepository.cancelReservation(reservationId)
    }
}