package com.ssafy.smile.data.remote.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.data.remote.datasource.PostRemoteDataSource
import com.ssafy.smile.data.remote.model.PostDetailResponseDto
import com.ssafy.smile.domain.repository.PostRepository
import com.ssafy.smile.presentation.base.BaseRepository

class PostRepositoryImpl(private val postRemoteDataSource: PostRemoteDataSource): BaseRepository(), PostRepository {
    private val _getPostByIdLiveData = MutableLiveData<NetworkUtils.NetworkResponse<PostDetailResponseDto>>()
    val getPostByIdLiveData: LiveData<NetworkUtils.NetworkResponse<PostDetailResponseDto>>
        get() = _getPostByIdLiveData

    private val _deletePostByIdLiveData = MutableLiveData<NetworkUtils.NetworkResponse<Any>>()
    val deletePostByIdLiveData: LiveData<NetworkUtils.NetworkResponse<Any>>
        get() = _deletePostByIdLiveData

    override suspend fun getPostById(articleId: Long) {
        safeApiCall(_getPostByIdLiveData){
            postRemoteDataSource.getPostById(articleId)
        }
    }

    override suspend fun deletePostById(articleId: Long) {
        safeApiCall(_deletePostByIdLiveData){
            postRemoteDataSource.deletePostById(articleId)
        }
    }
}