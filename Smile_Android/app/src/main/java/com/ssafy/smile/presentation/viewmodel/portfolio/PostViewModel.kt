package com.ssafy.smile.presentation.viewmodel.portfolio

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.smile.Application
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.data.remote.model.PostDetailResponseDto
import com.ssafy.smile.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class PostViewModel: BaseViewModel() {
    private val postRepository = Application.repositoryInstances.getPostRepository()

    // 게시물 조회 결과를 관리하는 LiveData
    val getPostByIdResponse: LiveData<NetworkUtils.NetworkResponse<PostDetailResponseDto>>
        get() = postRepository.getPostByIdLiveData

    // 게시물 조회를 수행하는 함수
    fun getPostById(articleId: Long) {
        viewModelScope.launch {
            postRepository.getPostById(articleId)
        }
    }

    // 게시물 삭제 결과를 관리하는 LiveData
    val deletePostByIdResponse: LiveData<NetworkUtils.NetworkResponse<Any>>
        get() = postRepository.deletePostByIdLiveData

    // 게시물 삭제를 수행하는 함수
    fun deletePostById(articleId: Long) {
        viewModelScope.launch {
            postRepository.deletePostById(articleId)
        }
    }
}