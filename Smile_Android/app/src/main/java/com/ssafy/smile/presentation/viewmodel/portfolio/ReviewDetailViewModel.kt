package com.ssafy.smile.presentation.viewmodel.portfolio

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.smile.Application
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.data.remote.model.ReviewRequestDto
import com.ssafy.smile.data.remote.model.ReviewResponseDto
import com.ssafy.smile.domain.model.ReviewDomainDto
import com.ssafy.smile.presentation.base.BaseViewModel
import kotlinx.coroutines.launch
import java.io.File

class ReviewDetailViewModel : BaseViewModel()  {
    private val reservationRepository = Application.repositoryInstances.getReservationRepository()

    private val reviewData : ReviewDomainDto = ReviewDomainDto()
    private val _imageDataResponse : MutableLiveData<File?> = MutableLiveData<File?>(null)
    val imageDataResponse : MutableLiveData<File?>
        get() = _imageDataResponse
    private val _reviewDataResponse : MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    val reviewDataResponse : MutableLiveData<Boolean>
        get() = _reviewDataResponse

    val getReviewLiveData: LiveData<NetworkUtils.NetworkResponse<ReviewResponseDto>>
        get() = reservationRepository.getReviewLiveData

    val postReviewResponse: LiveData<NetworkUtils.NetworkResponse<Any>>
        get() = reservationRepository.postReviewLiveData

    val deleteReviewResponse: LiveData<NetworkUtils.NetworkResponse<Any>>
        get() = reservationRepository.deleteReviewLiveData

    fun uploadData(reviewDto : ReviewDomainDto){
        reviewData.score = reviewDto.score
        reviewData.content = reviewDto.content
        reviewData.image = reviewDto.image
        _imageDataResponse.postValue(reviewDto.image)
        _reviewDataResponse.postValue(checkData())
    }

    fun uploadScoreData(score : Float){
        reviewData.score = score
        _reviewDataResponse.postValue(checkData())
    }
    fun uploadContentData(content: String){
        reviewData.content = content
        _reviewDataResponse.postValue(checkData())
    }
    fun uploadImageData(image : File?){
        reviewData.image = image
        _imageDataResponse.postValue(image)
        _reviewDataResponse.postValue(checkData())
    }

    private fun checkData() : Boolean {
        if (reviewData.score==null || reviewData.content ==null || reviewData.image==null) return false
        return true
    }

    fun getReviewInfo(reviewId: Long) {
        viewModelScope.launch {
            reservationRepository.getReview(reviewId)
        }
    }

    fun postReviewInfo(reservationId: Long) {
        viewModelScope.launch {
            reviewData.image?.let {
                reservationRepository.postReview(reservationId, reviewData.score!!, reviewData.content!!, makeMultiPartBody("image", it))
            }
        }
    }

    fun deleteReviewInfo(reviewId: Long) {
        viewModelScope.launch {
            reservationRepository.deleteReview(reviewId)
        }
    }


}