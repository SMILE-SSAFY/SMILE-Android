package com.ssafy.smile.presentation.viewmodel.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.smile.Application
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.data.remote.model.MyPageResponseDto
import com.ssafy.smile.data.remote.model.PhotographerResponseDto
import com.ssafy.smile.domain.model.AddressDomainDto
import com.ssafy.smile.presentation.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyPageViewModel() : BaseViewModel() {
    private val photographerRepository = Application.repositoryInstances.getPhotographerRepository()
    private val userRepository = Application.repositoryInstances.getUserRepository()
    private val addressRepository = Application.repositoryInstances.getAddressRepository()

    val myPageResponse: LiveData<NetworkUtils.NetworkResponse<MyPageResponseDto>>
        get() = userRepository.myPageLiveData

    val getPhotographerResponse: LiveData<NetworkUtils.NetworkResponse<PhotographerResponseDto>>
        get() = photographerRepository.getPhotographerInfoResponseLiveData

    val deletePhotographerResponse: LiveData<NetworkUtils.NetworkResponse<String>>
        get() = photographerRepository.deletePhotographerInfoResponseLiveData

    val withDrawUserResponse : LiveData<NetworkUtils.NetworkResponse<String>>
        get() = userRepository.withDrawResponseLiveData

    fun getMyPageInfo() {
        viewModelScope.launch {
            userRepository.myPage()
        }
    }

    fun getPhotographerInfo() = viewModelScope.launch{
        photographerRepository.getPhotographerInfo()
    }

    fun deletePhotographerInfo() = viewModelScope.launch{
        photographerRepository.deletePhotographerInfo()
    }

    fun withDrawUser() = viewModelScope.launch {
        userRepository.withDrawUser()
    }

    suspend fun deleteAllAddress() = viewModelScope.launch(Dispatchers.IO) {
        addressRepository.deleteAllAddress()
    }

}