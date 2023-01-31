package com.ssafy.smile.data.remote.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.data.remote.datasource.PhotographerRemoteDataSource

import com.ssafy.smile.data.remote.model.PhotographerByAddressResponseDto
import com.ssafy.smile.data.remote.model.PhotographerDto
import com.ssafy.smile.data.remote.model.PhotographerResponseDto
import com.ssafy.smile.domain.repository.PhotographerRepository
import com.ssafy.smile.presentation.base.BaseRepository
import okhttp3.MultipartBody

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

    private val _getPhotographerInfoByAddressResponseLiveData = MutableLiveData<NetworkUtils.NetworkResponse<ArrayList<PhotographerByAddressResponseDto>>>()
    val getPhotographerInfoByAddressResponseLiveData: LiveData<NetworkUtils.NetworkResponse<ArrayList<PhotographerByAddressResponseDto>>>
        get() = _getPhotographerInfoByAddressResponseLiveData


    override suspend fun registerPhotographerInfo(photographerDto: PhotographerDto, image: MultipartBody.Part) {
        safeApiCall(_registerPhotographerInfoResponseLiveData){
            photographerRemoteDataSource.registerPhotographerInfo(photographerDto, image)
        }
    }

    override suspend fun getPhotographerInfo() {
        safeApiCall(_getPhotographerInfoResponseLiveData){
            photographerRemoteDataSource.getPhotographerInfo()
        }
    }

    override suspend fun modifyPhotographerInfo(photographerDto: PhotographerDto, image: MultipartBody.Part) {
        safeApiCall(_modifyPhotographerInfoResponseLiveData){
            photographerRemoteDataSource.modifyPhotographerInfo(photographerDto, image)
        }
    }

    override suspend fun deletePhotographerInfo() {
        safeApiCall(_deletePhotographerInfoResponseLiveData){
            photographerRemoteDataSource.deletePhotographerInfo()
        }
    }

    override suspend fun getPhotographerInfoByAddress(address:String){
        safeApiCall(_getPhotographerInfoByAddressResponseLiveData){
            photographerRemoteDataSource.getPhotographerInfoByAddress(address)
        }
    }
}