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


    val getPhotographerResponse: LiveData<NetworkUtils.NetworkResponse<PhotographerResponseDto>>
        get() = photographerRepository.getPhotographerInfoResponseLiveData

    val deletePhotographerResponse: LiveData<NetworkUtils.NetworkResponse<String>>
        get() = photographerRepository.deletePhotographerInfoResponseLiveData


    fun getPhotographerInfo() = viewModelScope.launch{
        photographerRepository.getPhotographerInfo()
    }

    fun deletePhotographerInfo() = viewModelScope.launch{
        photographerRepository.deletePhotographerInfo()
    }

}