package com.ssafy.smile.presentation.viewmodel.mypage

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.ssafy.smile.Application
import com.ssafy.smile.common.util.Constants.REQUEST_KEY_IMAGE_PROFILE
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.common.view.sources.SingleLiveData
import com.ssafy.smile.data.remote.model.PhotographerResponseDto
import com.ssafy.smile.domain.model.*
import com.ssafy.smile.presentation.base.BaseViewModel
import kotlinx.coroutines.launch
import java.io.File

class PhotographerWriteGraphViewModel : BaseViewModel() {
    private val photographerRepository = Application.repositoryInstances.getPhotographerRepository()

    var profileBitmap : Bitmap? = null
    private val _photographerRequestDomainDto : PhotographerRequestDomainDto = PhotographerRequestDomainDto()
    private val _photographerDataResponse : SingleLiveData<PhotographerRequestDomainDto> = SingleLiveData(_photographerRequestDomainDto)
    val photographerDataResponse : SingleLiveData<PhotographerRequestDomainDto>
        get() = _photographerDataResponse
    private val _checkDataResponse : SingleLiveData<Boolean> = SingleLiveData(null)
    val checkDataResponse : SingleLiveData<Boolean>
        get() = _checkDataResponse
    private val _profileImageResponse : SingleLiveData<File?> = SingleLiveData<File?>(null)
    val profileImageResponse : SingleLiveData<File?>
        get() = _profileImageResponse

    fun getData() = this._photographerRequestDomainDto

    fun uploadData(image:File?, introduction:String, categories : List<CategoryDomainDto>, places:List<PlaceDomainDto>, account:AccountDomainDto){
        _photographerRequestDomainDto.profileImg = image
        _photographerRequestDomainDto.introduction = introduction
        _photographerRequestDomainDto.categories = categories
        _photographerRequestDomainDto.places = places
        _photographerRequestDomainDto.account = account
        _profileImageResponse.postValue(image)
        photographerDataResponse.postValue(_photographerRequestDomainDto)
        _checkDataResponse.postValue(checkData())
    }

    fun uploadProfileImage(image:File?){
        _photographerRequestDomainDto.profileImg = image
        _profileImageResponse.postValue(image)
        _checkDataResponse.postValue(checkData())
    }

    fun uploadIntroductionData(introduction: String?){
        _photographerRequestDomainDto.introduction = introduction
        _checkDataResponse.postValue(checkData())
    }
    fun uploadCategoriesData(categories : List<CategoryDomainDto>){
        _photographerRequestDomainDto.categories = categories
        _checkDataResponse.postValue(checkData())
    }
    fun uploadPlacesData(places: List<PlaceDomainDto>){
        _photographerRequestDomainDto.places = places
        _checkDataResponse.postValue(checkData())
    }
    fun uploadAccountData(account:AccountDomainDto){
        _photographerRequestDomainDto.account = account
        _checkDataResponse.postValue(checkData())
    }
    private fun checkData() : Boolean {
        if (_photographerRequestDomainDto.profileImg==null || _photographerRequestDomainDto.introduction==null||_photographerRequestDomainDto.categories.any { it.isEmpty }||
            _photographerRequestDomainDto.places.any { it.isEmpty }||_photographerRequestDomainDto.account?.isEmpty==true) return false
        return true
    }

    val registerPhotographerResponse: SingleLiveData<NetworkUtils.NetworkResponse<Any>>
        get() = photographerRepository.registerPhotographerInfoResponseLiveData

    val modifyPhotographerResponse: SingleLiveData<NetworkUtils.NetworkResponse<PhotographerResponseDto>>
        get() = photographerRepository.modifyPhotographerInfoResponseLiveData

    fun registerPhotographerInfo() = viewModelScope.launch{
        val photographerRequestDto = _photographerRequestDomainDto.makeToPhotographerRequestDto()
        val image = makeMultiPartBody(REQUEST_KEY_IMAGE_PROFILE, photographerRequestDto.profileImg)
        photographerRepository.registerPhotographerInfo(image = image, photographerDto = photographerRequestDto.photographerDto)
    }

    fun modifyPhotographerInfo() = viewModelScope.launch{
        val photographerModifyRequestDto = _photographerRequestDomainDto.makeToPhotographerModifyRequestDto()
        val image = makeMultiPartBody(REQUEST_KEY_IMAGE_PROFILE, photographerModifyRequestDto.profileImg)
        photographerRepository.modifyPhotographerInfo(image = image, photographerDto = photographerModifyRequestDto.photographerDto)
    }

}