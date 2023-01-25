package com.ssafy.smile.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.smile.Application
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.data.remote.model.UserResponseDto
import com.ssafy.smile.domain.model.LoginDomainDto
import com.ssafy.smile.domain.model.SignUpDomainDto
import com.ssafy.smile.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class UserViewModel: BaseViewModel() {
    private val userRepository = Application.repositoryInstances.getUserRepository()

    // 이메일 중복 여부 결과를 관리하는 LiveData
    val emailCheckResponse: LiveData<NetworkUtils.NetworkResponse<String>>
        get() = userRepository.checkEmailResponseLiveData

    // 회원가입 결과를 관리하는 LiveData
    val signUpResponse: LiveData<NetworkUtils.NetworkResponse<UserResponseDto>>
        get() = userRepository.signUpResponseLiveData

    // 핸드폰 인증 번호를 관리하는 LiveData
    val phoneNumberCheckResponse: LiveData<NetworkUtils.NetworkResponse<Int>>
        get() = userRepository.checkPhoneNumberResponseLiveData

    // 로그인 결과를 관리하는 LiveData
    val loginResponse: LiveData<NetworkUtils.NetworkResponse<UserResponseDto>>
        get() = userRepository.loginResponseLiveData

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

    // 핸드폰 인증을 수행하는 함수
    fun checkPhoneNumber(phoneNumber: String) {
        viewModelScope.launch {
            userRepository.checkPhoneNumber(phoneNumber)
        }
    }

    // 로그인을 수행하는 함수
    fun login(loginDomainDto: LoginDomainDto) {
        viewModelScope.launch {
            userRepository.login(loginDomainDto)
        }
    }
}