package com.ssafy.smile.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.smile.Application
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.data.remote.model.SignUpResponseDto
import com.ssafy.smile.domain.model.SignUpDomainDto
import com.ssafy.smile.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class UserViewModel: BaseViewModel() {
    private val userRepository = Application.repositoryInstances.getUserRepository()

    // 이메일 중복 여부 결과를 관리하는 LiveData
    val emailCheckResponse: LiveData<NetworkUtils.NetworkResponse<String>>
        get() = userRepository.checkEmailResponseLiveData

    // 회원가입 결과를 관리하는 LiveData
    val signUpResponse: LiveData<NetworkUtils.NetworkResponse<SignUpResponseDto>>
        get() = userRepository.signUpResponseLiveData

    // 이메일 중복 여부 확인을 수행하는 함수
    fun checkEmail(email: String) {
        viewModelScope.launch {
            userRepository.checkEmail(email)
        }
    }

    // 회원가입을 수행하는 함수
    fun signUp(signUpDomainDto: SignUpDomainDto) {
        viewModelScope.launch {
            userRepository.signUp(signUpDomainDto)
        }
    }
}