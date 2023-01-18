package com.ssafy.smile.data.remote.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.data.remote.datasource.UserRemoteDataSource
import com.ssafy.smile.domain.repository.UserRepository

class UserRepositoryImpl(private val userRemoteDataSource: UserRemoteDataSource): UserRepository {
    private val _checkEmailResponseLiveData = MutableLiveData<NetworkUtils.NetworkResponse<Boolean>>()
    val checkEmailResponseLiveData: LiveData<NetworkUtils.NetworkResponse<Boolean>>
        get() = _checkEmailResponseLiveData

    private val _checkNicknameResponseLiveData = MutableLiveData<NetworkUtils.NetworkResponse<Boolean>>()
    val checkNicknameResponseLiveData: LiveData<NetworkUtils.NetworkResponse<Boolean>>
        get() = _checkNicknameResponseLiveData

    override suspend fun checkEmail(email: String) {
        _checkEmailResponseLiveData.postValue(NetworkUtils.NetworkResponse.Loading())

        val response = userRemoteDataSource.checkEmail(email)
        if (response.isSuccessful && response.body() != null) {
            _checkEmailResponseLiveData.postValue(NetworkUtils.NetworkResponse.Success(response.body()!!))
        } else {
            _checkEmailResponseLiveData.postValue(NetworkUtils.NetworkResponse.Failure(response.errorBody()?.string()!!))
        }
    }

    override suspend fun checkNickname(nickname: String) {
        _checkNicknameResponseLiveData.postValue(NetworkUtils.NetworkResponse.Loading())

        val response = userRemoteDataSource.checkNickname(nickname)
        if (response.isSuccessful && response.body() != null) {
            _checkNicknameResponseLiveData.postValue(NetworkUtils.NetworkResponse.Success(response.body()!!))
        } else {
            _checkNicknameResponseLiveData.postValue(NetworkUtils.NetworkResponse.Failure(response.errorBody()?.string()!!))
        }
    }
}