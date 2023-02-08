package com.ssafy.smile.data.remote.repository

import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.common.view.sources.SingleLiveData
import com.ssafy.smile.data.remote.datasource.UserRemoteDataSource
import com.ssafy.smile.data.remote.model.KakaoLoginRequestDto
import com.ssafy.smile.data.remote.model.LogoutRequestDto
import com.ssafy.smile.data.remote.model.MyPageResponseDto
import com.ssafy.smile.data.remote.model.UserResponseDto
import com.ssafy.smile.domain.model.LoginDomainDto
import com.ssafy.smile.domain.model.SignUpDomainDto
import com.ssafy.smile.domain.repository.UserRepository
import com.ssafy.smile.presentation.base.BaseRepository

class UserRepositoryImpl(private val userRemoteDataSource: UserRemoteDataSource): BaseRepository(), UserRepository {
    private val _checkEmailResponseLiveData = SingleLiveData<NetworkUtils.NetworkResponse<String>>(null)
    val checkEmailResponseLiveData: SingleLiveData<NetworkUtils.NetworkResponse<String>>
        get() = _checkEmailResponseLiveData

    private val _signUpResponseLiveData = SingleLiveData<NetworkUtils.NetworkResponse<UserResponseDto>>(null)
    val signUpResponseLiveData: SingleLiveData<NetworkUtils.NetworkResponse<UserResponseDto>>
        get() = _signUpResponseLiveData

    private val _checkPhoneNumberResponseLiveData = SingleLiveData<NetworkUtils.NetworkResponse<Int>>(null)
    val checkPhoneNumberResponseLiveData: SingleLiveData<NetworkUtils.NetworkResponse<Int>>
        get() = _checkPhoneNumberResponseLiveData

    private val _loginResponseLiveData = SingleLiveData<NetworkUtils.NetworkResponse<UserResponseDto>>(null)
    val loginResponseLiveData: SingleLiveData<NetworkUtils.NetworkResponse<UserResponseDto>>
        get() = _loginResponseLiveData

    private val _kakaoLoginResponseLiveData = SingleLiveData<NetworkUtils.NetworkResponse<UserResponseDto>>(null)
    val kakaoLoginResponseLiveData: SingleLiveData<NetworkUtils.NetworkResponse<UserResponseDto>>
        get() = _kakaoLoginResponseLiveData

    private val _withDrawResponseLiveData = SingleLiveData<NetworkUtils.NetworkResponse<String>>(null)
    val withDrawResponseLiveData: SingleLiveData<NetworkUtils.NetworkResponse<String>>
        get() = _withDrawResponseLiveData

    private val _myPageLiveData = SingleLiveData<NetworkUtils.NetworkResponse<MyPageResponseDto>>(null)
    val myPageLiveData: SingleLiveData<NetworkUtils.NetworkResponse<MyPageResponseDto>>
        get() = _myPageLiveData

    private val _logoutLiveData = SingleLiveData<NetworkUtils.NetworkResponse<Any>>(null)
    val logoutLiveData: SingleLiveData<NetworkUtils.NetworkResponse<Any>>
        get() = _logoutLiveData

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

    override suspend fun withDrawUser() {
        safeApiCall(_withDrawResponseLiveData){
            userRemoteDataSource.withDraw()
        }
    }

    override suspend fun myPage() {
        safeApiCall(_myPageLiveData) {
            userRemoteDataSource.myPage()
        }
    }

    override suspend fun logout(logoutRequestDto: LogoutRequestDto) {
        safeApiCall(_logoutLiveData) {
            userRemoteDataSource.logout(logoutRequestDto)
        }
    }
}