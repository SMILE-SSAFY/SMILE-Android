package com.ssafy.smile.data.remote.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.data.remote.datasource.UserRemoteDataSource
import com.ssafy.smile.data.remote.model.KakaoLoginRequestDto
import com.ssafy.smile.data.remote.model.UserResponseDto
import com.ssafy.smile.domain.model.LoginDomainDto
import com.ssafy.smile.domain.model.SignUpDomainDto
import com.ssafy.smile.domain.repository.UserRepository
import com.ssafy.smile.presentation.base.BaseRepository
import retrofit2.Response

class UserRepositoryImpl(private val userRemoteDataSource: UserRemoteDataSource): BaseRepository(), UserRepository {
    private val _checkEmailResponseLiveData = MutableLiveData<NetworkUtils.NetworkResponse<String>>()
    val checkEmailResponseLiveData: LiveData<NetworkUtils.NetworkResponse<String>>
        get() = _checkEmailResponseLiveData

    private val _signUpResponseLiveData = MutableLiveData<NetworkUtils.NetworkResponse<UserResponseDto>>()
    val signUpResponseLiveData: LiveData<NetworkUtils.NetworkResponse<UserResponseDto>>
        get() = _signUpResponseLiveData

    private val _checkPhoneNumberResponseLiveData = MutableLiveData<NetworkUtils.NetworkResponse<Int>>()
    val checkPhoneNumberResponseLiveData: LiveData<NetworkUtils.NetworkResponse<Int>>
        get() = _checkPhoneNumberResponseLiveData

    private val _loginResponseLiveData = MutableLiveData<NetworkUtils.NetworkResponse<UserResponseDto>>()
    val loginResponseLiveData: LiveData<NetworkUtils.NetworkResponse<UserResponseDto>>
        get() = _loginResponseLiveData

    private val _kakaoLoginResponseLiveData = MutableLiveData<NetworkUtils.NetworkResponse<UserResponseDto>>()
    val kakaoLoginResponseLiveData: LiveData<NetworkUtils.NetworkResponse<UserResponseDto>>
        get() = _kakaoLoginResponseLiveData

    override suspend fun checkEmail(email: String) {
        safeApiCall(_checkEmailResponseLiveData) {
            userRemoteDataSource.checkEmail(email)
        }
    }

    override suspend fun signUp(signUpDomainDto: SignUpDomainDto) {
        safeApiCall(_signUpResponseLiveData) {
            userRemoteDataSource.signUp(signUpDomainDto.toSignUpRequestDto())
        }
    }

    override suspend fun checkPhoneNumber(phoneNumber: String) {
        safeApiCall(_checkPhoneNumberResponseLiveData) {
            userRemoteDataSource.checkPhoneNumber(phoneNumber)
        }
    }

    override suspend fun login(loginDomainDto: LoginDomainDto) {
        safeApiCall(_loginResponseLiveData) {
            userRemoteDataSource.login(loginDomainDto.toLoginRequestDto())
        }
    }

    override suspend fun kakaoLogin(token: KakaoLoginRequestDto) {
        safeApiCall(_kakaoLoginResponseLiveData) {
            userRemoteDataSource.kakaoLogin(token)
        }
    }
}