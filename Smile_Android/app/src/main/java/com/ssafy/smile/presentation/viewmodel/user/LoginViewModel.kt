package com.ssafy.smile.presentation.viewmodel.user

import androidx.lifecycle.viewModelScope
import com.ssafy.smile.Application
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.common.view.sources.SingleLiveData
import com.ssafy.smile.data.remote.model.KakaoLoginRequestDto
import com.ssafy.smile.data.remote.model.UserResponseDto
import com.ssafy.smile.domain.model.LoginDomainDto
import com.ssafy.smile.presentation.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel: BaseViewModel() {
    private val userRepository = Application.repositoryInstances.getUserRepository()

    // 로그인 결과를 관리하는 LiveData
    val loginResponse: SingleLiveData<NetworkUtils.NetworkResponse<UserResponseDto>>
        get() = userRepository.loginResponseLiveData

    // 카카오 로그인 결과를 관리하는 LiveData
    val kakaoLoginResponse: SingleLiveData<NetworkUtils.NetworkResponse<UserResponseDto>>
        get() = userRepository.kakaoLoginResponseLiveData

    // 로그인을 수행하는 함수
    fun login(loginDomainDto: LoginDomainDto) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.login(loginDomainDto)
        }
    }

    // 카카오 로그인을 수행하는 함수
    fun kakaoLogin(token: KakaoLoginRequestDto) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.kakaoLogin(token)
        }
    }
}