package com.ssafy.smile.presentation.viewmodel.portfolio

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.smile.Application
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.data.remote.model.ArticleResponseDto
import com.ssafy.smile.data.remote.model.PortfolioResponseDto
import com.ssafy.smile.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class PortfolioViewModel: BaseViewModel() {
    private val portfolioRepository = Application.repositoryInstances.getPortfolioRepository()

    // 포트폴리오 화면 결과를 관리하는 LiveData
    val getPortfolioResponse: LiveData<NetworkUtils.NetworkResponse<PortfolioResponseDto>>
        get() = portfolioRepository.getPortfolioResponseLiveData

    // 포트폴리오 화면 데이터 조회를 수행하는 함수
    fun getPortfolio(photographerId: Long) {
        viewModelScope.launch {
            portfolioRepository.getPortfolio(photographerId)
        }
    }

    // 포트폴리오 게시물 화면 결과를 관리하는 LiveData
    val getArticlesResponse: LiveData<NetworkUtils.NetworkResponse<ArticleResponseDto>>
        get() = portfolioRepository.geArticlesResponseLiveData

    // 포트폴리오 게시물 화면 데이터 조회를 수행하는 함수
    fun getArticles(photographerId: Long) {
        viewModelScope.launch {
            portfolioRepository.getArticles(photographerId)
        }
    }
}