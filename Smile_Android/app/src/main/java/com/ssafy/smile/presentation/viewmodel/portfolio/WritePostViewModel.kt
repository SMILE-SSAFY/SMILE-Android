package com.ssafy.smile.presentation.viewmodel.portfolio


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.smile.Application
import com.ssafy.smile.common.util.Constants.REQUEST_KEY_IMAGE_LIST
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.domain.model.Address
import com.ssafy.smile.domain.model.PostDto
import com.ssafy.smile.presentation.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

private const val TAG = "WritePostViewModel_스마일"
class WritePostViewModel : BaseViewModel() {
    private val portfolioRepository = Application.repositoryInstances.getPortfolioRepository()

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
    fun uploadAddressData(address: Address){
        postData.address = address
        _postDataResponse.postValue(checkData())
    }
    fun uploadCategoryData(category : String){
        postData.category = category
        _postDataResponse.postValue(checkData())
    }
    private fun checkData() : Boolean {
        if (postData.images.isNullOrEmpty() || postData.address==null || postData.category==null) return false
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

}