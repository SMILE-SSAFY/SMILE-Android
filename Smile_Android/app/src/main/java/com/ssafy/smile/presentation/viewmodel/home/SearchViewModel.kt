package com.ssafy.smile.presentation.viewmodel.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.smile.Application
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.data.remote.model.SearchPhotographerResponseDto
import com.ssafy.smile.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class SearchViewModel: BaseViewModel() {
    private val searchRepository = Application.repositoryInstances.getSearchRepository()

    // 작가 검색 결과를 관리하는 LiveData
    val searchPhotographerResponse: LiveData<NetworkUtils.NetworkResponse<ArrayList<SearchPhotographerResponseDto>>>
        get() = searchRepository.searchPhotographerLiveData

    // 작가 검색을 수행하는 함수
    fun searchPhotographer(category: String) {
        viewModelScope.launch {
            searchRepository.searchPhotographer(category)
        }
    }
}