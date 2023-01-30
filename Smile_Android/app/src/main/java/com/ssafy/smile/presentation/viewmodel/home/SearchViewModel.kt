package com.ssafy.smile.presentation.viewmodel.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.smile.Application
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.data.remote.model.PhotographerHeartDto
import com.ssafy.smile.data.remote.model.PostHeartDto
import com.ssafy.smile.data.remote.model.SearchPhotographerResponseDto
import com.ssafy.smile.data.remote.model.SearchPostResponseDto
import com.ssafy.smile.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class SearchViewModel: BaseViewModel() {
    private val searchRepository = Application.repositoryInstances.getSearchRepository()
    private val heartRepository = Application.repositoryInstances.getHeartRepository()

    // 입력한 검색어
    var searchCategory: String = ""

    // 작가 검색 결과를 관리하는 LiveData
    val searchPhotographerResponse: LiveData<NetworkUtils.NetworkResponse<ArrayList<SearchPhotographerResponseDto>>>
        get() = searchRepository.searchPhotographerLiveData

    // 게시글 검색 결과를 관리하는 LiveData
    val searchPostResponse: LiveData<NetworkUtils.NetworkResponse<ArrayList<SearchPostResponseDto>>>
        get() = searchRepository.searchPostLiveData

    // 작가 좋아요 결과를 관리하는 LiveData
    val photographerHeartResponse: LiveData<NetworkUtils.NetworkResponse<PhotographerHeartDto>>
        get() = heartRepository.photographerHeartResponseLiveData

    // 게시물 좋아요 결과를 관리하는 LiveData
    val postHeartResponse: LiveData<NetworkUtils.NetworkResponse<PostHeartDto>>
        get() = heartRepository.postHeartResponseLiveData

    // 작가 검색을 수행하는 함수
    fun searchPhotographer(categoryName: String) {
        viewModelScope.launch {
            searchRepository.searchPhotographer(categoryName)
        }
    }

    // 게시글 검색을 수행하는 함수
    fun searchPost(categoryName: String) {
        viewModelScope.launch {
            searchRepository.searchPost(categoryName)
        }
    }

    // 작가 좋아요를 수행하는 함수
    fun photographerHeart(photographerId: Long) {
        viewModelScope.launch {
            heartRepository.photographerHeart(photographerId)
        }
    }

    // 게시물 좋아요를 수행하는 함수
    fun postHeart(articleId: Long) {
        viewModelScope.launch {
            heartRepository.postHeart(articleId)
        }
    }
}