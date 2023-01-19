package com.ssafy.smile.data.remote.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.data.remote.datasource.UserRemoteDataSource
import com.ssafy.smile.data.remote.model.SignUpResponseDto
import com.ssafy.smile.domain.model.SignUpDomainDto
import com.ssafy.smile.domain.repository.UserRepository

private const val TAG = "UserRepositoryImpl_스마일"
class UserRepositoryImpl(private val userRemoteDataSource: UserRemoteDataSource): UserRepository {
    private val _checkEmailResponseLiveData = MutableLiveData<NetworkUtils.NetworkResponse<String>>()
    val checkEmailResponseLiveData: LiveData<NetworkUtils.NetworkResponse<String>>
        get() = _checkEmailResponseLiveData

    private val _signUpResponseLiveData = MutableLiveData<NetworkUtils.NetworkResponse<SignUpResponseDto>>()
    val signUpResponseLiveData: LiveData<NetworkUtils.NetworkResponse<SignUpResponseDto>>
        get() = _signUpResponseLiveData

    private val _checkPhoneNumberResponseLiveData = MutableLiveData<NetworkUtils.NetworkResponse<Int>>()
    val checkPhoneNumberResponseLiveData: LiveData<NetworkUtils.NetworkResponse<Int>>
        get() = _checkPhoneNumberResponseLiveData

    override suspend fun checkEmail(email: String) {
        _checkEmailResponseLiveData.postValue(NetworkUtils.NetworkResponse.Loading())

        val response = userRemoteDataSource.checkEmail(email)
        if (response.isSuccessful && response.body() != null) {
            _checkEmailResponseLiveData.postValue(NetworkUtils.NetworkResponse.Success(response.body()!!))
        } else {
            _checkEmailResponseLiveData.postValue(NetworkUtils.NetworkResponse.Failure(response.errorBody()?.string()!!))
            Log.d(TAG, "checkEmail Error: $response")
        }
    }

    override suspend fun signUp(signUpDomainDto: SignUpDomainDto) {
        _signUpResponseLiveData.postValue(NetworkUtils.NetworkResponse.Loading())

        val response = userRemoteDataSource.signUp(signUpDomainDto.toSignUpRequestDto())
        if (response.isSuccessful && response.body() != null) {
            _signUpResponseLiveData.postValue(NetworkUtils.NetworkResponse.Success(response.body()!!))
        } else {
            _signUpResponseLiveData.postValue(NetworkUtils.NetworkResponse.Failure(response.errorBody()?.string()!!))
            Log.d(TAG, "checkEmail Error: $response")
        }
    }

    override suspend fun checkPhoneNumber(phoneNumber: String) {
        _checkPhoneNumberResponseLiveData.postValue(NetworkUtils.NetworkResponse.Loading())

        val response = userRemoteDataSource.checkPhoneNumber(phoneNumber)
        if (response.isSuccessful && response.body() != null) {
            _checkPhoneNumberResponseLiveData.postValue(NetworkUtils.NetworkResponse.Success(response.body()!!))
        } else {
            _checkPhoneNumberResponseLiveData.postValue(NetworkUtils.NetworkResponse.Failure(response.errorBody()?.string()!!))
            Log.d(TAG, "checkPhoneNumber Error: $response")
        }
    }
}