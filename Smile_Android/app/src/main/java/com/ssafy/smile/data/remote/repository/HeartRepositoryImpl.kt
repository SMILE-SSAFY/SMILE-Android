package com.ssafy.smile.data.remote.repository

import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.common.view.sources.SingleLiveData
import com.ssafy.smile.data.remote.datasource.HeartRemoteDataSource
import com.ssafy.smile.data.remote.model.ArticleHeartResponseDto
import com.ssafy.smile.data.remote.model.PhotographerHeartDto
import com.ssafy.smile.data.remote.model.PhotographerHeartResponseDto
import com.ssafy.smile.data.remote.model.PostHeartDto
import com.ssafy.smile.domain.repository.HeartRepository
import com.ssafy.smile.presentation.base.BaseRepository

class HeartRepositoryImpl(private val heartRemoteDataSource: HeartRemoteDataSource): BaseRepository(), HeartRepository {
    private val _photographerHeartResponseLiveData = SingleLiveData<NetworkUtils.NetworkResponse<PhotographerHeartDto>>(null)
    val photographerHeartResponseLiveData: SingleLiveData<NetworkUtils.NetworkResponse<PhotographerHeartDto>>
        get() = _photographerHeartResponseLiveData

    private val _postHeartResponseLiveData = SingleLiveData<NetworkUtils.NetworkResponse<PostHeartDto>>(null)
    val postHeartResponseLiveData: SingleLiveData<NetworkUtils.NetworkResponse<PostHeartDto>>
        get() = _postHeartResponseLiveData

    private val _articleHeartListResponseLiveData = SingleLiveData<NetworkUtils.NetworkResponse<List<ArticleHeartResponseDto>>>(null)
    val articleHeartListResponseLiveData: SingleLiveData<NetworkUtils.NetworkResponse<List<ArticleHeartResponseDto>>>
        get() = _articleHeartListResponseLiveData

    private val _photographerHeartListResponseLiveData = SingleLiveData<NetworkUtils.NetworkResponse<List<PhotographerHeartResponseDto>>>(null)
    val photographerHeartListResponseLiveData: SingleLiveData<NetworkUtils.NetworkResponse<List<PhotographerHeartResponseDto>>>
        get() = _photographerHeartListResponseLiveData

    override suspend fun photographerHeart(photographerId: Long) {
        safeApiCall(_photographerHeartResponseLiveData){
            heartRemoteDataSource.photographerHeart(photographerId)
        }
    }

    override suspend fun postHeart(articleId: Long) {
        safeApiCall(_postHeartResponseLiveData){
            heartRemoteDataSource.postHeart(articleId)
        }
    }

    override suspend fun getArticleHeartList() {
        safeApiCall(_articleHeartListResponseLiveData){
            heartRemoteDataSource.getArticleHeartList()
        }
    }

    override suspend fun getPhotographerHeartList() {
        safeApiCall(_photographerHeartListResponseLiveData){
            heartRemoteDataSource.getPhotographerHeartList()
        }
    }
}