package com.ssafy.smile.presentation.viewmodel.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.smile.Application
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.data.remote.model.PhotographerByAddressResponseDto
import com.ssafy.smile.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class HomeViewModel: BaseViewModel() {
    private val photographerRepository = Application.repositoryInstances.getPhotographerRepository()

    val getPhotographerInfoByAddressResponse: LiveData<NetworkUtils.NetworkResponse<ArrayList<PhotographerByAddressResponseDto>>>
        get() = photographerRepository.getPhotographerInfoByAddressResponseLiveData

    fun getPhotographerInfoByAddressInfo(address: String) = viewModelScope.launch{
        photographerRepository.getPhotographerInfoByAddress(address)
    }
}