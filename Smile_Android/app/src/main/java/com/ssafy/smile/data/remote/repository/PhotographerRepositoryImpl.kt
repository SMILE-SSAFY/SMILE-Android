package com.ssafy.smile.data.remote.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.data.remote.datasource.PhotographerRemoteDataSource
import com.ssafy.smile.data.remote.model.PhotographerRequestDto
import com.ssafy.smile.data.remote.model.PhotographerResponseDto
import com.ssafy.smile.domain.repository.PhotographerRepository
import com.ssafy.smile.presentation.base.BaseRepository

class PhotographerRepositoryImpl(private val photographerRemoteDataSource: PhotographerRemoteDataSource): BaseRepository(), PhotographerRepository {
    private val _registerPhotographerInfoResponseLiveData = MutableLiveData<NetworkUtils.NetworkResponse<Any>>()
    val registerPhotographerInfoResponseLiveData: LiveData<NetworkUtils.NetworkResponse<Any>>
        get() = _registerPhotographerInfoResponseLiveData

    private val _getPhotographerInfoResponseLiveData = MutableLiveData<NetworkUtils.NetworkResponse<PhotographerResponseDto>>()
    val getPhotographerInfoResponseLiveData: LiveData<NetworkUtils.NetworkResponse<PhotographerResponseDto>>
        get() = _getPhotographerInfoResponseLiveData

    private val _modifyPhotographerInfoResponseLiveData = MutableLiveData<NetworkUtils.NetworkResponse<PhotographerResponseDto>>()
    val modifyPhotographerInfoResponseLiveData: LiveData<NetworkUtils.NetworkResponse<PhotographerResponseDto>>
        get() = _modifyPhotographerInfoResponseLiveData

    private val _deletePhotographerInfoResponseLiveData = MutableLiveData<NetworkUtils.NetworkResponse<String>>()
    val deletePhotographerInfoResponseLiveData: LiveData<NetworkUtils.NetworkResponse<String>>
        get() = _deletePhotographerInfoResponseLiveData


    override suspend fun registerPhotographerInfo(photographerRequestDto: PhotographerRequestDto) {
        safeApiCall(_registerPhotographerInfoResponseLiveData){
            photographerRemoteDataSource.registerPhotographerInfo(photographerRequestDto)
        }
    }

    override suspend fun getPhotographerInfo() {
        safeApiCall(_getPhotographerInfoResponseLiveData){
            photographerRemoteDataSource.getPhotographerInfo()
        }
    }

    override suspend fun modifyPhotographerInfo(photographerRequestDto: PhotographerRequestDto) {
        safeApiCall(_modifyPhotographerInfoResponseLiveData){
            photographerRemoteDataSource.modifyPhotographerInfo(photographerRequestDto)
        }
    }

    override suspend fun deletePhotographerInfo() {
        safeApiCall(_deletePhotographerInfoResponseLiveData){
            photographerRemoteDataSource.deletePhotographerInfo()
        }
    }
}