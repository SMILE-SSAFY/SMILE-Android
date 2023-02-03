package com.ssafy.smile.presentation.viewmodel.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.smile.Application
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.data.remote.model.PhotographerByAddressResponseDto
import com.ssafy.smile.data.remote.model.PhotographerHeartDto
import com.ssafy.smile.domain.model.AddressDomainDto
import com.ssafy.smile.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class HomeViewModel: BaseViewModel() {
    private val photographerRepository = Application.repositoryInstances.getPhotographerRepository()
    private val heartRepository = Application.repositoryInstances.getHeartRepository()
    private val addressRepository = Application.repositoryInstances.getAddressRepository()

    val getPhotographerInfoByAddressResponse: LiveData<NetworkUtils.NetworkResponse<ArrayList<PhotographerByAddressResponseDto>>>
        get() = photographerRepository.getPhotographerInfoByAddressResponseLiveData

    // 작가 좋아요 결과를 관리하는 LiveData
    val photographerHeartResponse: LiveData<NetworkUtils.NetworkResponse<PhotographerHeartDto>>
        get() = heartRepository.photographerHeartResponseLiveData

    val getCurrentAddressResponse: LiveData<AddressDomainDto>
        get() = addressRepository.getCurrentAddress()

    fun getPhotographerInfoByAddressInfo(address: String) = viewModelScope.launch{
        photographerRepository.getPhotographerInfoByAddress(address)
    }

    // 작가 좋아요를 수행하는 함수
    fun photographerHeart(photographerId: Long) {
        viewModelScope.launch {
            heartRepository.photographerHeart(photographerId)
        }
    }

    fun getCurrentAddressInfo(){
        viewModelScope.launch {
            addressRepository.getCurrentAddress()
        }
    }
}