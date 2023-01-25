package com.ssafy.smile.presentation.viewmodel.portfolio


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.smile.Application
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.domain.model.Address
import com.ssafy.smile.domain.model.PostDto
import com.ssafy.smile.presentation.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okio.Buffer
import java.io.File
import java.io.IOException

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
        // TODD : 민하 sharedPreference 가져오기.
        val post = postData.toPost()
        makeImageList(post.image)
        portfolioRepository.uploadPost(
            post.ArticlePostReq.latitude,
            post.ArticlePostReq.longitude,
            post.ArticlePostReq.detailAddress,
            post.ArticlePostReq.category,
            makeImageList(post.image)
        )
    }

    private fun makeImageList(images: List<File>): List<MultipartBody.Part> {
        val imageList = arrayListOf<MultipartBody.Part>()
        for (i in images.indices) {
            val file = images[i]
            Log.d(TAG, "check: ${file.absoluteFile}, ${file.isFile}, ${file.absolutePath}, ${file.name}")
            val fileBody: RequestBody = file.convertToRequestBody()
            Log.d(TAG, bodyToString(fileBody).toString())
            val part = MultipartBody.Part.createFormData("imageList", "image$i.jpg", fileBody)
            Log.d(TAG, "check: ${fileBody.contentType()}")
            imageList.add(part)
        }
        return imageList
    }

    private fun bodyToString(request: RequestBody): String? {
        return try {
            val buffer = Buffer()
            request.writeTo(buffer)
            buffer.readUtf8()
        } catch (e: IOException) { "did not work" }
    }


}