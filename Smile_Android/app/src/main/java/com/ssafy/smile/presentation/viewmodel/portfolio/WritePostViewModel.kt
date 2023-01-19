package com.ssafy.smile.presentation.viewmodel.portfolio


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.smile.Application
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.data.remote.model.Post
import com.ssafy.smile.domain.model.Address
import com.ssafy.smile.domain.model.PostDto
import com.ssafy.smile.presentation.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import java.io.File


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
        if (postData.images==null || postData.address==null || postData.category==null) return false
        return true
    }

    fun uploadPost() = viewModelScope.launch(Dispatchers.IO){
        val token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZSI6IlVTRVIiLCJpZCI6IjEiLCJpYXQiOjE2NzQyOTM2MDEsImV4cCI6MTY3Njg4NTYwMX0.-6CkwSW7-AveynO0Oi961P8dyhIkJ8x-QcoYmSsZwTY"
        val post = postData.toPost()
        portfolioRepository.uploadPost(
            "Bearer $token",
            post.ArticlePostReq.latitude,
            post.ArticlePostReq.longitude,
            post.ArticlePostReq.detailAddress,
            post.ArticlePostReq.category,
            makeImageList(post.image)
        )
    }

    private fun makeImageList(images: List<File>): List<MultipartBody.Part> {
        val imageList = arrayListOf<MultipartBody.Part>()
        for (file in images) {
            val fileBody: RequestBody = file.convertToRequestBody()
            val part = MultipartBody.Part.createFormData("imageList", "", fileBody)
            imageList.add(part)
        }
        return imageList
    }


}