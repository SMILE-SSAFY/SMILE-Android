package com.ssafy.smile.presentation.viewmodel.map

import androidx.lifecycle.viewModelScope
import com.naver.maps.geometry.LatLng
import com.ssafy.smile.Application
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.common.view.sources.SingleLiveData
import com.ssafy.smile.data.remote.model.ClusterDto
import com.ssafy.smile.data.remote.model.PostHeartDto
import com.ssafy.smile.data.remote.model.PostSearchRequestDto
import com.ssafy.smile.domain.model.Types
import com.ssafy.smile.presentation.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MapViewModel : BaseViewModel() {
    private val articleRepository = Application.repositoryInstances.getArticleRepository()
    private val postRepository = Application.repositoryInstances.getPostRepository()
    private val heartRepository = Application.repositoryInstances.getHeartRepository()

    private var mapListViewStateData : MapListViewStateData = MapListViewStateData()
    private val _getMapListViewStateDatResponse : SingleLiveData<MapListViewStateData> = SingleLiveData(mapListViewStateData)
    val getMapListViewStateDatResponse : SingleLiveData<MapListViewStateData>
        get() = _getMapListViewStateDatResponse

    val getArticleClusterInfoResponse: SingleLiveData<NetworkUtils.NetworkResponse<List<ClusterDto>>>
        get() = articleRepository.getArticleClusterInfoResponseLiveData

    val getPostSearchListResponse: SingleLiveData<NetworkUtils.NetworkResponse<PostSearchRequestDto>>
        get() = postRepository.getPostSearchListByIdLiveData

    val getUpdateHeartResponse: SingleLiveData<NetworkUtils.NetworkResponse<PostHeartDto>>
        get() = heartRepository.postHeartResponseLiveData

    fun uploadClusterIdData(clusterId: Long){
        mapListViewStateData.clusterId = clusterId
        _getMapListViewStateDatResponse.postValue(mapListViewStateData)
    }
    fun uploadScrolledPosition(scrolledPosition: Int){
        mapListViewStateData.scrolledPositionNum = scrolledPosition
        _getMapListViewStateDatResponse.postValue(mapListViewStateData)
    }

    fun getData() = mapListViewStateData

    fun getPhotographerInfo(latLng1: LatLng, latLng2: LatLng) = viewModelScope.launch{
        articleRepository.getArticleClusterInfo(latLng1.longitude, latLng1.latitude, latLng2.longitude, latLng2.latitude)
    }

    fun getPostPostSearchList(clusterId: Long, condition: String, page: Int) = viewModelScope.launch{
        postRepository.getPostSearchListById(clusterId, condition, page)
    }

    fun updatePostHeart(articleId: Long) = viewModelScope.launch(Dispatchers.IO) {
        heartRepository.postHeart(articleId)
    }


    data class MapListViewStateData(var clusterId : Long = -1L,
                                    var searchType : Types.PostSearchType = Types.PostSearchType.HEART,
                                    var isInitialized : Boolean = false,
                                    var adapterPageNum : Int = 0,
                                    var scrolledPositionNum : Int = 0)
}
