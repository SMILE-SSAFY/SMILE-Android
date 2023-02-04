package com.ssafy.smile.presentation.viewmodel.portfolio


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.smile.Application
import com.ssafy.smile.common.util.Constants.REQUEST_KEY_IMAGE_LIST
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.data.remote.model.*
import com.ssafy.smile.domain.model.AddressDomainDto
import com.ssafy.smile.domain.model.PostDto
import com.ssafy.smile.presentation.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class PortfolioGraphViewModel : BaseViewModel() {
    private val portfolioRepository = Application.repositoryInstances.getPortfolioRepository()
    private val postRepository = Application.repositoryInstances.getPostRepository()
    private val heartRepository = Application.repositoryInstances.getHeartRepository()
    private val reservationRepository = Application.repositoryInstances.getReservationRepository()

    // 게시물 작성
    private val postData : PostDto = PostDto()
    private val _postDataResponse : MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    val postDataResponse : MutableLiveData<Boolean>
        get() = _postDataResponse
    val postUploadResponse: LiveData<NetworkUtils.NetworkResponse<Any>>
        get() = portfolioRepository.postUploadResponseLiveData

    fun uploadImageData(images:List<File>){
        postData.images = images
        _postDataResponse.postValue(checkData())
    }
    fun uploadAddressData(addressDomainDto: AddressDomainDto){
        postData.addressDomainDto = addressDomainDto
        _postDataResponse.postValue(checkData())
    }
    fun uploadCategoryData(category : String){
        postData.category = category
        _postDataResponse.postValue(checkData())
    }
    private fun checkData() : Boolean {
        if (postData.images.isNullOrEmpty() || postData.addressDomainDto==null || postData.category==null) return false
        return true
    }

    fun uploadPost() = viewModelScope.launch(Dispatchers.IO){
        val post = postData.toPost()
        makeMultiPartBodyList(REQUEST_KEY_IMAGE_LIST, post.image)
        portfolioRepository.uploadPost(
            post.ArticlePostReq.latitude,
            post.ArticlePostReq.longitude,
            post.ArticlePostReq.detailAddress,
            post.ArticlePostReq.category,
            makeMultiPartBodyList(REQUEST_KEY_IMAGE_LIST, post.image)
        )
    }

    // 게시물 조회
    val getPostByIdResponse: LiveData<NetworkUtils.NetworkResponse<PostDetailResponseDto>>
        get() = postRepository.getPostByIdLiveData

    fun getPostById(articleId: Long) {
        viewModelScope.launch {
            postRepository.getPostById(articleId)
        }
    }

    // 게시물 삭제
    val deletePostByIdResponse: LiveData<NetworkUtils.NetworkResponse<Any>>
        get() = postRepository.deletePostByIdLiveData

    fun deletePostById(articleId: Long) {
        viewModelScope.launch {
            postRepository.deletePostById(articleId)
        }
    }

    // 게시물 좋아요
    val postHeartResponse: LiveData<NetworkUtils.NetworkResponse<PostHeartDto>>
        get() = heartRepository.postHeartResponseLiveData

    fun postHeart(articleId: Long) {
        viewModelScope.launch {
            heartRepository.postHeart(articleId)
        }
    }

    // 예약
    val photographerReservationResponse: LiveData<NetworkUtils.NetworkResponse<ReservationPhotographerDto>>
        get() = reservationRepository.photographerReservationInfoLiveData

    fun getPhotographerReservationInfo(photographerId: Long) {
        viewModelScope.launch {
            reservationRepository.getPhotographerReservationInfo(photographerId)
        }
    }

    val postReservationResponse: LiveData<NetworkUtils.NetworkResponse<ReservationResponseDto>>
        get() = reservationRepository.postReservationLiveData

    fun postReservation(reservationRequestDto: ReservationRequestDto) {
        viewModelScope.launch {
            reservationRepository.postReservation(reservationRequestDto)
        }
    }

}