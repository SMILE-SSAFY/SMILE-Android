package com.ssafy.smile.presentation.viewmodel.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.smile.Application
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.data.remote.model.ReservationListDto
import com.ssafy.smile.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class PhotographerReservationListViewModel: BaseViewModel() {
    private val reservationRepository = Application.repositoryInstances.getReservationRepository()

    val getPhotographerReservationListResponse: LiveData<NetworkUtils.NetworkResponse<ArrayList<ReservationListDto>>>
        get() = reservationRepository.photographerReservationListLiveData

    fun getPhotographerReservationList() = viewModelScope.launch{
        reservationRepository.getPhotographerReservationList()
    }
}