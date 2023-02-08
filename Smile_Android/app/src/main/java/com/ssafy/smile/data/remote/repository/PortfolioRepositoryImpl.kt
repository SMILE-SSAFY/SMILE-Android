package com.ssafy.smile.data.remote.repository

import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.common.view.sources.SingleLiveData
import com.ssafy.smile.data.remote.datasource.PortfolioRemoteDataSource
import com.ssafy.smile.data.remote.model.PortfolioResponseDto
import com.ssafy.smile.data.remote.model.PostListResponseDto
import com.ssafy.smile.domain.repository.PortfolioRepository
import com.ssafy.smile.presentation.base.BaseRepository
import okhttp3.MultipartBody

class PortfolioRepositoryImpl(private val portfolioRemoteDataSource: PortfolioRemoteDataSource): BaseRepository(), PortfolioRepository {
    private val _getPortfolioResponseLiveData =
        SingleLiveData<NetworkUtils.NetworkResponse<PortfolioResponseDto>>(null)
    val getPortfolioResponseLiveData: SingleLiveData<NetworkUtils.NetworkResponse<PortfolioResponseDto>>
        get() = _getPortfolioResponseLiveData

    private val _getPostsResponseLiveData =
        SingleLiveData<NetworkUtils.NetworkResponse<ArrayList<PostListResponseDto>>>(null)
    val getPostsResponseLiveData: SingleLiveData<NetworkUtils.NetworkResponse<ArrayList<PostListResponseDto>>>
        get() = _getPostsResponseLiveData

    private val _postUploadResponseLiveData = SingleLiveData<NetworkUtils.NetworkResponse<Any>>(null)
    val postUploadResponseLiveData: SingleLiveData<NetworkUtils.NetworkResponse<Any>>
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