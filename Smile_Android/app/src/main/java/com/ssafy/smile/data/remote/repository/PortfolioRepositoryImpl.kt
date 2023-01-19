package com.ssafy.smile.data.remote.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.data.remote.datasource.PortfolioRemoteDataSourceImpl
import com.ssafy.smile.domain.repository.PortfolioRepository
import com.ssafy.smile.presentation.base.BaseRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class PortfolioRepositoryImpl(private val portfolioRemoteDataSource: PortfolioRemoteDataSourceImpl) : BaseRepository(), PortfolioRepository {
    private val _postUploadResponseLiveData = MutableLiveData<NetworkUtils.NetworkResponse<Any>>()
    val postUploadResponseLiveData: LiveData<NetworkUtils.NetworkResponse<Any>>
        get() = _postUploadResponseLiveData

    override suspend fun uploadPost(token: String, images: MutableMap<String, RequestBody>){
        safeApiCall(_postUploadResponseLiveData){
            portfolioRemoteDataSource.uploadPost(token, images)
        }
    }
    override suspend fun uploadPost(token: String, latitude: Float, longitude: Float,
                                    detailAddress: String, category: String, images : List<MultipartBody.Part>){
        safeApiCall(_postUploadResponseLiveData){
            portfolioRemoteDataSource.uploadPost(token, latitude, longitude, detailAddress, category, images)
        }
    }

    override suspend fun uploadPost(token: String, latitude: Float, longitude: Float,
                                                    detailAddress: String, category: String, images : HashMap<String, List<MultipartBody.Part>>){
        safeApiCall(_postUploadResponseLiveData){
            portfolioRemoteDataSource.uploadPost(token, latitude, longitude, detailAddress, category, images)
        }
    }



}