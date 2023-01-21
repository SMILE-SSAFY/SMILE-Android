package com.ssafy.smile.data.remote.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.data.remote.datasource.LikeRemoteDataSource
import com.ssafy.smile.data.remote.model.PortfolioResponseDto
import com.ssafy.smile.domain.repository.LikeRepository
import com.ssafy.smile.presentation.base.BaseRepository

class LikeRepositoryImpl(private val likeRemoteDataSource: LikeRemoteDataSource): BaseRepository(), LikeRepository {
    private val _photographerLikeResponseLiveData = MutableLiveData<NetworkUtils.NetworkResponse<Any>>()
    val photographerLikeResponseLiveData: LiveData<NetworkUtils.NetworkResponse<Any>>
        get() = _photographerLikeResponseLiveData

    private val _photographerLikeCancelResponseLiveData = MutableLiveData<NetworkUtils.NetworkResponse<Any>>()
    val photographerLikeCancelResponseLiveData: LiveData<NetworkUtils.NetworkResponse<Any>>
        get() = _photographerLikeCancelResponseLiveData

    override suspend fun photographerLike(photographerId: Long) {
        likeRemoteDataSource.photographerLike(photographerId)
    }

    override suspend fun photographerLikeCancel(photographerId: Long) {
        likeRemoteDataSource.photographerLikeCancel(photographerId)
    }
}