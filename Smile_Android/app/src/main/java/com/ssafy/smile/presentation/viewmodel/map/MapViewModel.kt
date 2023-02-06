package com.ssafy.smile.presentation.viewmodel.map

import androidx.lifecycle.viewModelScope
import com.naver.maps.geometry.LatLng
import com.ssafy.smile.Application
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.common.view.sources.SingleLiveData
import com.ssafy.smile.data.remote.model.ClusterDto
import com.ssafy.smile.data.remote.model.PostSearchRequestDto
import com.ssafy.smile.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class MapViewModel : BaseViewModel() {
    private val articleRepository = Application.repositoryInstances.getArticleRepository()
    private val postRepository = Application.repositoryInstances.getPostRepository()

    val getArticleClusterInfoResponse: SingleLiveData<NetworkUtils.NetworkResponse<List<ClusterDto>>>
        get() = articleRepository.getArticleClusterInfoResponseLiveData

    val getPostSearchListResponse: SingleLiveData<NetworkUtils.NetworkResponse<PostSearchRequestDto>>
        get() = postRepository.getPostSearchListByIdLiveData

    fun getPhotographerInfo(latLng1: LatLng, latLng2: LatLng) = viewModelScope.launch{
        articleRepository.getArticleClusterInfo(latLng1.longitude, latLng1.latitude, latLng2.longitude, latLng2.latitude)
    }

    fun getPostPostSearchList(clusterId: Long, condition: String, page: Int) = viewModelScope.launch{
        postRepository.getPostSearchListById(clusterId, condition, page)
    }

}