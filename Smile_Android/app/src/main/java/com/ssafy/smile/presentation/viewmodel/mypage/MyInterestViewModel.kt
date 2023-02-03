package com.ssafy.smile.presentation.viewmodel.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.smile.Application
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.data.remote.model.ArticleHeartResponseDto
import com.ssafy.smile.data.remote.model.PhotographerHeartDto
import com.ssafy.smile.data.remote.model.PhotographerHeartResponseDto
import com.ssafy.smile.data.remote.model.PostHeartDto
import com.ssafy.smile.presentation.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyInterestViewModel : BaseViewModel() {
    private val heartRepository = Application.repositoryInstances.getHeartRepository()

    val getArticleHeartListResponse: LiveData<NetworkUtils.NetworkResponse<List<ArticleHeartResponseDto>>>
        get() = heartRepository.articleHeartListResponseLiveData

    val getPhotographerHeartListResponse: LiveData<NetworkUtils.NetworkResponse<List<PhotographerHeartResponseDto>>>
        get() = heartRepository.photographerHeartListResponseLiveData

    val postPhotographerHeartResponse: LiveData<NetworkUtils.NetworkResponse<PhotographerHeartDto>>
        get() = heartRepository.photographerHeartResponseLiveData

    val postArticleHeartResponse: LiveData<NetworkUtils.NetworkResponse<PostHeartDto>>
        get() = heartRepository.postHeartResponseLiveData

    fun getArticleHeartList() = viewModelScope.launch(Dispatchers.IO) {
        heartRepository.getArticleHeartList()
    }

    fun getPhotographerHeartList() = viewModelScope.launch(Dispatchers.IO) {
        heartRepository.getPhotographerHeartList()
    }

    fun postPhotographerHeart(photographerId: Long) {
        viewModelScope.launch {
            heartRepository.photographerHeart(photographerId)
        }
    }
    fun postArticleHeart(articleId: Long) {
        viewModelScope.launch {
            heartRepository.postHeart(articleId)
        }
    }
}