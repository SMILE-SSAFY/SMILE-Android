package com.ssafy.smile.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.smile.Application
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class UserViewModel: BaseViewModel() {
    private val userRepository = Application.repositoryInstances.getUserRepository()

    // 이메일 중복 여부 결과를 관리하는 LiveData
    val emailCheckResponse: LiveData<NetworkUtils.NetworkResponse<Boolean>>
        get() = userRepository.checkEmailResponseLiveData

    // 닉네임 중복 여부 결과를 관리하는 LiveData
    val nicknameCheckResponse: LiveData<NetworkUtils.NetworkResponse<Boolean>>
        get() = userRepository.checkNicknameResponseLiveData

    // 이메일 중복 여부 확인을 수행하는 함수
    fun checkEmail(email: String) {
        viewModelScope.launch {
            userRepository.checkEmail(email)
        }
    }

    // 닉네임 중복 여부 확인을 수행하는 함수
    fun checkNickname(nickname: String) {
        viewModelScope.launch {
            userRepository.checkNickname(nickname)
        }
    }
}