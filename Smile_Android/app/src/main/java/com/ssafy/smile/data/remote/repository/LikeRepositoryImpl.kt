package com.ssafy.smile.data.remote.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.data.remote.datasource.LikeRemoteDataSource
import com.ssafy.smile.domain.repository.LikeRepository
import com.ssafy.smile.presentation.base.BaseRepository

class LikeRepositoryImpl(private val likeRemoteDataSource: LikeRemoteDataSource): BaseRepository(), LikeRepository {
    private val _photographerLikeResponseLiveData = MutableLiveData<NetworkUtils.NetworkResponse<Any>>()
    val photographerLikeResponseLiveData: LiveData<NetworkUtils.NetworkResponse<Any>>
        get() = _photographerLikeResponseLiveData

    private val _photographerLikeCancelResponseLiveData = MutableLiveData<NetworkUtils.NetworkResponse<Any>>()
    val photographerLikeCancelResponseLiveData: LiveData<NetworkUtils.NetworkResponse<Any>>
        get() = _photographerLikeCancelResponseLiveData

    private val _postLikeResponseLiveData = MutableLiveData<NetworkUtils.NetworkResponse<Any>>()
    val postLikeResponseLiveData: LiveData<NetworkUtils.NetworkResponse<Any>>
        get() = _postLikeResponseLiveData

    private val _postLikeCancelResponseLiveData = MutableLiveData<NetworkUtils.NetworkResponse<Any>>()
    val postLikeCancelResponseLiveData: LiveData<NetworkUtils.NetworkResponse<Any>>
        get() = _postLikeCancelResponseLiveData

    override suspend fun photographerLike(photographerId: Long) {
        safeApiCall(_photographerLikeResponseLiveData) {
            likeRemoteDataSource.photographerLike(photographerId)
        }
    }

    override suspend fun photographerLikeCancel(photographerId: Long) {
        safeApiCall(_photographerLikeCancelResponseLiveData) {
            likeRemoteDataSource.photographerLikeCancel(photographerId)
        }
    }

    override suspend fun postLike(articleId: Long) {
        safeApiCall(_postLikeResponseLiveData) {
            likeRemoteDataSource.postLike(articleId)
        }
    }

    override suspend fun postLikeCancel(articleId: Long) {
        safeApiCall(_postLikeCancelResponseLiveData) {
            likeRemoteDataSource.postLikeCancel(articleId)
        }
    }
}