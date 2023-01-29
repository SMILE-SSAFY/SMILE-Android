package com.ssafy.smile.data.remote.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.data.remote.datasource.HeartRemoteDataSource
import com.ssafy.smile.data.remote.model.PhotographerHeartDto
import com.ssafy.smile.data.remote.model.PostHeartDto
import com.ssafy.smile.domain.repository.HeartRepository
import com.ssafy.smile.presentation.base.BaseRepository

class HeartRepositoryImpl(private val heartRemoteDataSource: HeartRemoteDataSource): BaseRepository(), HeartRepository {
    private val _photographerHeartResponseLiveData = MutableLiveData<NetworkUtils.NetworkResponse<PhotographerHeartDto>>()
    val photographerHeartResponseLiveData: LiveData<NetworkUtils.NetworkResponse<PhotographerHeartDto>>
        get() = _photographerHeartResponseLiveData

    private val _postHeartResponseLiveData = MutableLiveData<NetworkUtils.NetworkResponse<PostHeartDto>>()
    val postHeartResponseLiveData: LiveData<NetworkUtils.NetworkResponse<PostHeartDto>>
        get() = _postHeartResponseLiveData

    override suspend fun photographerHeart(photographerId: Long) {
        safeApiCall(_photographerHeartResponseLiveData){
            heartRemoteDataSource.photographerHeart(photographerId)
        }
    }

    override suspend fun postHeart(articleId: Long) {
        safeApiCall(_postHeartResponseLiveData){
            heartRemoteDataSource.postHeart(articleId)
        }
    }
}