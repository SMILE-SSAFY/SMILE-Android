package com.ssafy.smile.presentation.viewmodel.portfolio

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.smile.Application
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.data.remote.model.PhotographerHeartDto
import com.ssafy.smile.data.remote.model.PhotographerReviewDto
import com.ssafy.smile.data.remote.model.PortfolioResponseDto
import com.ssafy.smile.data.remote.model.PostListResponseDto
import com.ssafy.smile.presentation.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PortfolioViewModel: BaseViewModel() {
    private val portfolioRepository = Application.repositoryInstances.getPortfolioRepository()
    private val heartRepository = Application.repositoryInstances.getHeartRepository()
    private val reviewRepository = Application.repositoryInstances.getReservationRepository()

    // 작가 id
    var photographerId: Long = 0

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
    val getPostsResponse: LiveData<NetworkUtils.NetworkResponse<ArrayList<PostListResponseDto>>>
        get() = portfolioRepository.getPostsResponseLiveData

    // 포트폴리오 게시물 화면 데이터 조회를 수행하는 함수
    fun getPosts(photographerId: Long) {
        viewModelScope.launch {
            portfolioRepository.getPosts(photographerId)
        }
    }

    // 작가 좋아요 결과를 관리하는 LiveData
    val photographerHeartResponse: LiveData<NetworkUtils.NetworkResponse<PhotographerHeartDto>>
        get() = heartRepository.photographerHeartResponseLiveData

    // 작가 좋아요를 수행하는 함수
    fun photographerHeart(photographerId: Long) {
        viewModelScope.launch {
            heartRepository.photographerHeart(photographerId)
        }
    }

    val photographerReviewListResponse: LiveData<NetworkUtils.NetworkResponse<List<PhotographerReviewDto>>>
        get() = reviewRepository.getPhotographerReviewLiveData

    fun getPhotographerReviewList(photographerId: Long){
        viewModelScope.launch(Dispatchers.IO) {
            reviewRepository.getPhotographerReviewList(photographerId)
        }
    }

    val deleteReviewResponse: LiveData<NetworkUtils.NetworkResponse<Any>>
        get() = reviewRepository.deleteReviewLiveData

    fun deleteReview(reviewId:Long){
        viewModelScope.launch(Dispatchers.IO) {
            reviewRepository.deleteReview(reviewId)
        }
    }

}