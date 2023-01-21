package com.ssafy.smile.presentation.viewmodel.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.smile.Application
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.data.remote.model.PhotographerRequestDto
import com.ssafy.smile.data.remote.model.PhotographerResponseDto
import com.ssafy.smile.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class MyPageViewModel() : BaseViewModel() {
    private val photographerRepository = Application.repositoryInstances.getPhotographerRepository()

    val registerPhotographerResponse: LiveData<NetworkUtils.NetworkResponse<Any>>
        get() = photographerRepository.registerPhotographerInfoResponseLiveData

    val getPhotographerResponse: LiveData<NetworkUtils.NetworkResponse<PhotographerResponseDto>>
        get() = photographerRepository.getPhotographerInfoResponseLiveData

    val modifyPhotographerResponse: LiveData<NetworkUtils.NetworkResponse<PhotographerResponseDto>>
        get() = photographerRepository.modifyPhotographerInfoResponseLiveData

    val deletePhotographerResponse: LiveData<NetworkUtils.NetworkResponse<String>>
        get() = photographerRepository.deletePhotographerInfoResponseLiveData


    fun registerPhotographerInfo(photographerRequestDto: PhotographerRequestDto) = viewModelScope.launch{
        photographerRepository.registerPhotographerInfo(photographerRequestDto)
    }

    fun getPhotographerInfo() = viewModelScope.launch{
        photographerRepository.getPhotographerInfo()
    }

    fun modifyPhotographerInfo(photographerRequestDto: PhotographerRequestDto) = viewModelScope.launch{
        photographerRepository.modifyPhotographerInfo(photographerRequestDto)
    }

    fun deletePhotographerInfo() = viewModelScope.launch{
        photographerRepository.deletePhotographerInfo()
    }

}