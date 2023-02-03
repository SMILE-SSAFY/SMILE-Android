package com.ssafy.smile.data.remote.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.data.remote.datasource.HeartRemoteDataSource
import com.ssafy.smile.data.remote.model.ArticleHeartResponseDto
import com.ssafy.smile.data.remote.model.PhotographerHeartDto
import com.ssafy.smile.data.remote.model.PhotographerHeartResponseDto
import com.ssafy.smile.data.remote.model.PostHeartDto
import com.ssafy.smile.domain.repository.HeartRepository
import com.ssafy.smile.presentation.base.BaseRepository
import retrofit2.Response

class HeartRepositoryImpl(private val heartRemoteDataSource: HeartRemoteDataSource): BaseRepository(), HeartRepository {
    private val _photographerHeartResponseLiveData = MutableLiveData<NetworkUtils.NetworkResponse<PhotographerHeartDto>>()
    val photographerHeartResponseLiveData: LiveData<NetworkUtils.NetworkResponse<PhotographerHeartDto>>
        get() = _photographerHeartResponseLiveData

    private val _postHeartResponseLiveData = MutableLiveData<NetworkUtils.NetworkResponse<PostHeartDto>>()
    val postHeartResponseLiveData: LiveData<NetworkUtils.NetworkResponse<PostHeartDto>>
        get() = _postHeartResponseLiveData

    private val _articleHeartListResponseLiveData = MutableLiveData<NetworkUtils.NetworkResponse<List<ArticleHeartResponseDto>>>()
    val articleHeartListResponseLiveData: LiveData<NetworkUtils.NetworkResponse<List<ArticleHeartResponseDto>>>
        get() = _articleHeartListResponseLiveData

    private val _photographerHeartListResponseLiveData = MutableLiveData<NetworkUtils.NetworkResponse<List<PhotographerHeartResponseDto>>>()
    val photographerHeartListResponseLiveData: LiveData<NetworkUtils.NetworkResponse<List<PhotographerHeartResponseDto>>>
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