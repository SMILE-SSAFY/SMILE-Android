package com.ssafy.smile.presentation.viewmodel.mypage

import android.graphics.Bitmap
import androidx.lifecycle.viewModelScope
import com.ssafy.smile.Application
import com.ssafy.smile.common.util.Constants.REQUEST_KEY_IMAGE_PROFILE
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.common.view.sources.SingleLiveData
import com.ssafy.smile.data.remote.model.PhotographerRequestDto
import com.ssafy.smile.data.remote.model.PhotographerResponseDto
import com.ssafy.smile.presentation.base.BaseViewModel
import kotlinx.coroutines.launch
import java.io.File

class PhotographerWriteGraphViewModel : BaseViewModel() {
    private val photographerRepository = Application.repositoryInstances.getPhotographerRepository()

    var profileBitmap : Bitmap? = null
    var profileImage : File? = null         // TODO -> DTO로 다듬기 (with PhotographerProfileFragment의 변수와 통일화)
    private val _profileImageResponse : SingleLiveData<File?> = SingleLiveData<File?>(null)
    val profileImageResponse : SingleLiveData<File?>
        get() = _profileImageResponse

    fun uploadProfileImage(image:File?){
        profileImage = image
        _profileImageResponse.postValue(profileImage)
    }

    val registerPhotographerResponse: SingleLiveData<NetworkUtils.NetworkResponse<Any>>
        get() = photographerRepository.registerPhotographerInfoResponseLiveData

    val modifyPhotographerResponse: SingleLiveData<NetworkUtils.NetworkResponse<PhotographerResponseDto>>
        get() = photographerRepository.modifyPhotographerInfoResponseLiveData

    fun registerPhotographerInfo(photographerRequestDto: PhotographerRequestDto) = viewModelScope.launch{
        val image = makeMultiPartBody(REQUEST_KEY_IMAGE_PROFILE, photographerRequestDto.profileImg)
        photographerRepository.registerPhotographerInfo(image = image, photographerDto = photographerRequestDto.photographerDto)
    }

    fun modifyPhotographerInfo(photographerRequestDto: PhotographerRequestDto) = viewModelScope.launch{
        val image = makeMultiPartBody(REQUEST_KEY_IMAGE_PROFILE, photographerRequestDto.profileImg)
        photographerRepository.modifyPhotographerInfo(image = image, photographerDto = photographerRequestDto.photographerDto)
    }

}