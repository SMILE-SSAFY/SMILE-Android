package com.ssafy.smile.presentation.viewmodel.home

import androidx.lifecycle.viewModelScope
import com.ssafy.smile.Application
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.common.view.sources.SingleLiveData
import com.ssafy.smile.data.remote.model.MyPageResponseDto
import com.ssafy.smile.data.remote.model.PhotographerByAddressResponseDto
import com.ssafy.smile.data.remote.model.PhotographerHeartDto
import com.ssafy.smile.data.remote.model.PhotographerProfile
import com.ssafy.smile.domain.model.AddressDomainDto
import com.ssafy.smile.presentation.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel: BaseViewModel() {
    private val photographerRepository = Application.repositoryInstances.getPhotographerRepository()
    private val heartRepository = Application.repositoryInstances.getHeartRepository()
    private val addressRepository = Application.repositoryInstances.getAddressRepository()

    val getPhotographerInfoByAddressResponse: SingleLiveData<NetworkUtils.NetworkResponse<PhotographerByAddressResponseDto>>
        get() = photographerRepository.getPhotographerInfoByAddressResponseLiveData

    // 작가 좋아요 결과를 관리하는 LiveData
    val photographerHeartResponse: SingleLiveData<NetworkUtils.NetworkResponse<PhotographerHeartDto>>
        get() = heartRepository.photographerHeartResponseLiveData

    // 작가 프로필 사진 관리하는 LiveData
    val getPhotographerProfileResponse: SingleLiveData<NetworkUtils.NetworkResponse<PhotographerProfile>>
        get() = photographerRepository.getPhotographerProfileResponseLiveData

    private val _getCurrentAddressResponse: SingleLiveData<AddressDomainDto> = SingleLiveData(null)
    val getCurrentAddressResponse = _getCurrentAddressResponse

    fun getPhotographerInfoByAddressInfo(address: String, criteria: String) = viewModelScope.launch{
        photographerRepository.getPhotographerInfoByAddress(address, criteria)
    }

    fun photographerHeart(photographerId: Long) = viewModelScope.launch(Dispatchers.IO) {
        heartRepository.photographerHeart(photographerId)
    }

    fun getCurrentAddress() = viewModelScope.launch(Dispatchers.IO) {
        _getCurrentAddressResponse.postValue(addressRepository.getSelectedAddress())
    }

    fun getPhotographerProfile() = viewModelScope.launch {
        photographerRepository.getPhotographerProfileImg()
    }
}