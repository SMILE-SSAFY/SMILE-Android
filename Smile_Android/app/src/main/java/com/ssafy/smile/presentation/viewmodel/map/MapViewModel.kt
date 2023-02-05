package com.ssafy.smile.presentation.viewmodel.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.naver.maps.geometry.LatLng
import com.ssafy.smile.Application
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.data.remote.model.ClusterDto
import com.ssafy.smile.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class MapViewModel : BaseViewModel() {
    private val articleRepository = Application.repositoryInstances.getArticleRepository()

    val getArticleClusterInfoResponse: LiveData<NetworkUtils.NetworkResponse<List<ClusterDto>>>
        get() = articleRepository.getArticleClusterInfoResponseLiveData


    fun getPhotographerInfo(latLng1: LatLng, latLng2: LatLng) = viewModelScope.launch{
        articleRepository.getArticleClusterInfo(latLng1.longitude, latLng1.latitude, latLng2.longitude, latLng2.latitude)
    }

}