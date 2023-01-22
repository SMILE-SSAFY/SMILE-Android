package com.ssafy.smile.presentation.viewmodel.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.smile.Application
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.data.remote.model.PhotographerRequestDto
import com.ssafy.smile.data.remote.model.PhotographerResponseDto
import com.ssafy.smile.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class PhotographerWriteViewModel : BaseViewModel() {
    private val photographerRepository = Application.repositoryInstances.getPhotographerRepository()

    val registerPhotographerResponse: LiveData<NetworkUtils.NetworkResponse<Any>>
        get() = photographerRepository.registerPhotographerInfoResponseLiveData

    val modifyPhotographerResponse: LiveData<NetworkUtils.NetworkResponse<PhotographerResponseDto>>
        get() = photographerRepository.modifyPhotographerInfoResponseLiveData

    fun registerPhotographerInfo(photographerRequestDto: PhotographerRequestDto) = viewModelScope.launch{
        photographerRepository.registerPhotographerInfo(photographerRequestDto)
    }

    fun modifyPhotographerInfo(photographerRequestDto: PhotographerRequestDto) = viewModelScope.launch{
        photographerRepository.modifyPhotographerInfo(photographerRequestDto)
    }

}