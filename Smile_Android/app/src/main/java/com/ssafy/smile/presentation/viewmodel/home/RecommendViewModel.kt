package com.ssafy.smile.presentation.viewmodel.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.smile.Application
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.common.view.sources.SingleLiveData
import com.ssafy.smile.data.remote.model.PhotographerHeartDto
import com.ssafy.smile.data.remote.model.PhotographerRecommendResponseDto
import com.ssafy.smile.presentation.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecommendViewModel: BaseViewModel() {
    private val recommendRepository = Application.repositoryInstances.getRecommendRepository()
    private val heartRepository = Application.repositoryInstances.getHeartRepository()

    val getPhotographerRecommendResponse: SingleLiveData<NetworkUtils.NetworkResponse<ArrayList<PhotographerRecommendResponseDto>>>
        get() = recommendRepository.photographerRecommendInfoLiveData

    fun getPhotographerRecommendInfo(address: String) = viewModelScope.launch {
        recommendRepository.getPhotographerRecommendInfo(address)
    }

    val photographerHeartResponse: LiveData<NetworkUtils.NetworkResponse<PhotographerHeartDto>>
        get() = heartRepository.photographerHeartResponseLiveData

    fun photographerHeart(photographerId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            heartRepository.photographerHeart(photographerId)
        }
    }
}