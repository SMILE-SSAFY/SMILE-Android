package com.ssafy.smile.presentation.viewmodel.mypage

import android.graphics.Bitmap
import androidx.lifecycle.viewModelScope
import com.ssafy.smile.Application
import com.ssafy.smile.common.util.Constants.REQUEST_KEY_IMAGE_PROFILE
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.common.view.sources.SingleLiveData
import com.ssafy.smile.data.remote.model.PhotographerRequestDto
import com.ssafy.smile.data.remote.model.PhotographerResponseDto
import com.ssafy.smile.domain.model.*
import com.ssafy.smile.presentation.base.BaseViewModel
import kotlinx.coroutines.launch
import java.io.File

class PhotographerWriteGraphViewModel : BaseViewModel() {
    private val photographerRepository = Application.repositoryInstances.getPhotographerRepository()

    var profileBitmap : Bitmap? = null
    private val photographerRequestDomainDto : PhotographerRequestDomainDto = PhotographerRequestDomainDto()
    private val _photographerDataResponse : SingleLiveData<PhotographerRequestDomainDto> = SingleLiveData(photographerRequestDomainDto)
    val photographerDataResponse : SingleLiveData<PhotographerRequestDomainDto>
        get() = _photographerDataResponse
    private val _checkDataResponse : SingleLiveData<Boolean> = SingleLiveData(null)
    val checkDataResponse : SingleLiveData<Boolean>
        get() = _checkDataResponse
    private val _profileImageResponse : SingleLiveData<File?> = SingleLiveData<File?>(null)
    val profileImageResponse : SingleLiveData<File?>
        get() = _profileImageResponse


    fun uploadProfileImage(image:File?){
        photographerRequestDomainDto.profileImg = image
        _profileImageResponse.postValue(image)
        _checkDataResponse.postValue(checkData())
    }

    fun uploadData(introduction:String, categories : List<CategoryDomainDto>, places:List<PlaceDomainDto>, account:AccountDomainDto){
        photographerRequestDomainDto.introduction = introduction
        photographerRequestDomainDto.categories = categories
        photographerRequestDomainDto.places = places
        photographerRequestDomainDto.account = account
        photographerDataResponse.postValue(photographerRequestDomainDto)
        _checkDataResponse.postValue(checkData())
    }

    fun uploadIntroductionData(introduction: String){
        photographerRequestDomainDto.introduction = introduction
        _checkDataResponse.postValue(checkData())
    }
    fun uploadCategoriesData(categories : List<CategoryDomainDto>){
        photographerRequestDomainDto.categories = categories
        _checkDataResponse.postValue(checkData())
    }
    fun uploadPlacesData(places: List<PlaceDomainDto>){
        photographerRequestDomainDto.places = places
        _checkDataResponse.postValue(checkData())
    }
    fun uploadAccountData(account:AccountDomainDto){
        photographerRequestDomainDto.account = account
        _checkDataResponse.postValue(checkData())
    }
    private fun checkData() : Boolean {
        if (photographerRequestDomainDto.profileImg==null || photographerRequestDomainDto.introduction==null||photographerRequestDomainDto.categories.any { it.isEmpty }||
            photographerRequestDomainDto.places.any { it.isEmpty }||photographerRequestDomainDto.account?.isEmpty==true) return false
        return true
    }

    val registerPhotographerResponse: SingleLiveData<NetworkUtils.NetworkResponse<Any>>
        get() = photographerRepository.registerPhotographerInfoResponseLiveData

    val modifyPhotographerResponse: SingleLiveData<NetworkUtils.NetworkResponse<PhotographerResponseDto>>
        get() = photographerRepository.modifyPhotographerInfoResponseLiveData

    fun registerPhotographerInfo() = viewModelScope.launch{
        val photographerRequestDto = photographerRequestDomainDto.makeToPhotographerRequestDto()
        val image = makeMultiPartBody(REQUEST_KEY_IMAGE_PROFILE, photographerRequestDto.profileImg)
        photographerRepository.registerPhotographerInfo(image = image, photographerDto = photographerRequestDto.photographerDto)
    }

    fun modifyPhotographerInfo() = viewModelScope.launch{
        val photographerRequestDto = photographerRequestDomainDto.makeToPhotographerRequestDto()
        val image = makeMultiPartBody(REQUEST_KEY_IMAGE_PROFILE, photographerRequestDto.profileImg)
        photographerRepository.modifyPhotographerInfo(image = image, photographerDto = photographerRequestDto.photographerDto)
    }

}