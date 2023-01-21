package com.ssafy.smile.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.smile.Application
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class LikeViewModel: BaseViewModel() {
    private val likeRepository = Application.repositoryInstances.getLikeRepository()

    // 작가 좋아요 반영 결과를 관리하는 LiveData
    val photographerLikeResponse: LiveData<NetworkUtils.NetworkResponse<Any>>
        get() = likeRepository.photographerLikeResponseLiveData

    // 작가 좋아요를 수행하는 함수
    fun photographerLike(photographerId: Long) {
        viewModelScope.launch {
            likeRepository.photographerLike(photographerId)
        }
    }

    // 작가 좋아요 취소 결과를 관리하는 LiveData
    val photographerLikeCancelResponse: LiveData<NetworkUtils.NetworkResponse<Any>>
        get() = likeRepository.photographerLikeCancelResponseLiveData

    // 작가 좋아요 취소를 수행하는 함수
    fun photographerLikeCancel(photographerId: Long) {
        viewModelScope.launch {
            likeRepository.photographerLikeCancel(photographerId)
        }
    }
}