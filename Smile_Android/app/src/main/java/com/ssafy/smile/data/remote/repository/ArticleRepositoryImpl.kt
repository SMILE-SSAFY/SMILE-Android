package com.ssafy.smile.data.remote.repository

import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.common.view.sources.SingleLiveData
import com.ssafy.smile.data.remote.datasource.ArticleRemoteDataSource
import com.ssafy.smile.data.remote.model.ClusterDto
import com.ssafy.smile.domain.repository.ArticleRepository
import com.ssafy.smile.presentation.base.BaseRepository

class ArticleRepositoryImpl(private val articleRemoteDataSource: ArticleRemoteDataSource): BaseRepository(), ArticleRepository {

    private val _getArticleClusterInfoResponseLiveData = SingleLiveData<NetworkUtils.NetworkResponse<List<ClusterDto>>>(null)
    val getArticleClusterInfoResponseLiveData: SingleLiveData<NetworkUtils.NetworkResponse<List<ClusterDto>>>
        get() = _getArticleClusterInfoResponseLiveData

    override suspend fun getArticleClusterInfo(coord1x: Double, coord1y: Double, coord2x: Double, coord2y: Double){
        safeApiCall(_getArticleClusterInfoResponseLiveData){
            articleRemoteDataSource.getArticleClusterInfo(coord1x, coord1y, coord2x, coord2y)
        }
    }

}