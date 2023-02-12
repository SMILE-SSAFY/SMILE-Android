package com.ssafy.smile.data.remote.repository


import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.common.view.sources.SingleLiveData
import com.ssafy.smile.data.remote.datasource.PhotographerRemoteDataSource
import com.ssafy.smile.data.remote.model.*

import com.ssafy.smile.domain.repository.PhotographerRepository
import com.ssafy.smile.presentation.base.BaseRepository
import okhttp3.MultipartBody

class PhotographerRepositoryImpl(private val photographerRemoteDataSource: PhotographerRemoteDataSource): BaseRepository(), PhotographerRepository {
    private val _registerPhotographerInfoResponseLiveData = SingleLiveData<NetworkUtils.NetworkResponse<Any>>(null)
    val registerPhotographerInfoResponseLiveData: SingleLiveData<NetworkUtils.NetworkResponse<Any>>
        get() = _registerPhotographerInfoResponseLiveData

    private val _getPhotographerInfoResponseLiveData = SingleLiveData<NetworkUtils.NetworkResponse<PhotographerResponseDto>>(null)
    val getPhotographerInfoResponseLiveData: SingleLiveData<NetworkUtils.NetworkResponse<PhotographerResponseDto>>
        get() = _getPhotographerInfoResponseLiveData

    private val _modifyPhotographerInfoResponseLiveData = SingleLiveData<NetworkUtils.NetworkResponse<PhotographerResponseDto>>(null)
    val modifyPhotographerInfoResponseLiveData: SingleLiveData<NetworkUtils.NetworkResponse<PhotographerResponseDto>>
        get() = _modifyPhotographerInfoResponseLiveData

    private val _deletePhotographerInfoResponseLiveData = SingleLiveData<NetworkUtils.NetworkResponse<String>>(null)
    val deletePhotographerInfoResponseLiveData: SingleLiveData<NetworkUtils.NetworkResponse<String>>
        get() = _deletePhotographerInfoResponseLiveData

    private val _getPhotographerInfoByAddressResponseLiveData = SingleLiveData<NetworkUtils.NetworkResponse<PhotographerByAddressResponseDto>>(null)
    val getPhotographerInfoByAddressResponseLiveData: SingleLiveData<NetworkUtils.NetworkResponse<PhotographerByAddressResponseDto>>
        get() = _getPhotographerInfoByAddressResponseLiveData

    private val _getPhotographerProfileResponseLiveData = SingleLiveData<NetworkUtils.NetworkResponse<PhotographerProfile>>(null)
    val getPhotographerProfileResponseLiveData: SingleLiveData<NetworkUtils.NetworkResponse<PhotographerProfile>>
        get() = _getPhotographerProfileResponseLiveData


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

    override suspend fun modifyPhotographerInfo(photographerDto: PhotographerModifyDto, image: MultipartBody.Part) {
        safeApiCall(_modifyPhotographerInfoResponseLiveData){
            photographerRemoteDataSource.modifyPhotographerInfo(photographerDto, image)
        }
    }

    override suspend fun deletePhotographerInfo() {
        safeApiCall(_deletePhotographerInfoResponseLiveData){
            photographerRemoteDataSource.deletePhotographerInfo()
        }
    }

    override suspend fun getPhotographerInfoByAddress(address:String, criteria: String){
        safeApiCall(_getPhotographerInfoByAddressResponseLiveData){
            photographerRemoteDataSource.getPhotographerInfoByAddress(address, criteria)
        }
    }

    override suspend fun getPhotographerProfileImg(){
        safeApiCall(_getPhotographerProfileResponseLiveData){
            photographerRemoteDataSource.getPhotographerProfileImg()
        }
    }
}