package com.ssafy.smile.data.remote.repository

import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.common.view.sources.SingleLiveData
import com.ssafy.smile.data.remote.datasource.PostRemoteDataSource
import com.ssafy.smile.data.remote.model.PostDetailResponseDto
import com.ssafy.smile.data.remote.model.PostSearchRequestDto
import com.ssafy.smile.domain.repository.PostRepository
import com.ssafy.smile.presentation.base.BaseRepository

class PostRepositoryImpl(private val postRemoteDataSource: PostRemoteDataSource): BaseRepository(), PostRepository {
    private val _getPostByIdLiveData = SingleLiveData<NetworkUtils.NetworkResponse<PostDetailResponseDto>>(null)
    val getPostByIdLiveData: SingleLiveData<NetworkUtils.NetworkResponse<PostDetailResponseDto>>
        get() = _getPostByIdLiveData

    private val _deletePostByIdLiveData = SingleLiveData<NetworkUtils.NetworkResponse<Any>>(null)
    val deletePostByIdLiveData: SingleLiveData<NetworkUtils.NetworkResponse<Any>>
        get() = _deletePostByIdLiveData

    private val _getPostSearchListByIdLiveData = SingleLiveData<NetworkUtils.NetworkResponse<PostSearchRequestDto>>(null)
    val getPostSearchListByIdLiveData: SingleLiveData<NetworkUtils.NetworkResponse<PostSearchRequestDto>>
        get() = _getPostSearchListByIdLiveData

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


    override suspend fun getPostSearchListById(clusterId: Long, condition: String, page: Int) {
        safeApiCall(_getPostSearchListByIdLiveData){
            postRemoteDataSource.getPostSearchListById(clusterId, condition, page)
        }
    }


}