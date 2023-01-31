package com.ssafy.smile.data.remote.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.data.remote.datasource.PortfolioRemoteDataSource
import com.ssafy.smile.data.remote.model.PortfolioResponseDto
import com.ssafy.smile.data.remote.model.PostListResponseDto
import com.ssafy.smile.domain.repository.PortfolioRepository
import com.ssafy.smile.presentation.base.BaseRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class PortfolioRepositoryImpl(private val portfolioRemoteDataSource: PortfolioRemoteDataSource): BaseRepository(), PortfolioRepository {
    private val _getPortfolioResponseLiveData =
        MutableLiveData<NetworkUtils.NetworkResponse<PortfolioResponseDto>>()
    val getPortfolioResponseLiveData: LiveData<NetworkUtils.NetworkResponse<PortfolioResponseDto>>
        get() = _getPortfolioResponseLiveData

    private val _getPostsResponseLiveData =
        MutableLiveData<NetworkUtils.NetworkResponse<ArrayList<PostListResponseDto>>>()
    val getPostsResponseLiveData: LiveData<NetworkUtils.NetworkResponse<ArrayList<PostListResponseDto>>>
        get() = _getPostsResponseLiveData

    private val _postUploadResponseLiveData = MutableLiveData<NetworkUtils.NetworkResponse<Any>>()
    val postUploadResponseLiveData: LiveData<NetworkUtils.NetworkResponse<Any>>
        get() = _postUploadResponseLiveData

    override suspend fun getPortfolio(photographerId: Long) {
        safeApiCall(_getPortfolioResponseLiveData) {
            portfolioRemoteDataSource.getPortfolio(photographerId)
        }
    }

    override suspend fun getPosts(photographerId: Long) {
        safeApiCall(_getPostsResponseLiveData) {
            portfolioRemoteDataSource.getPosts(photographerId)
        }
    }

    override suspend fun uploadPost(latitude: Double, longitude: Double, detailAddress: String, category: String, images: List<MultipartBody.Part>) {
        safeApiCall(_postUploadResponseLiveData){
            portfolioRemoteDataSource.uploadPost(latitude, longitude, detailAddress, category, images)
        }
    }

}