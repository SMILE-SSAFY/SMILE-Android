package com.ssafy.smile.data.remote.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.data.remote.datasource.PortfolioRemoteDataSource
import com.ssafy.smile.data.remote.model.Article
import com.ssafy.smile.data.remote.model.ArticleResponseDto
import com.ssafy.smile.data.remote.model.PortfolioResponseDto
import com.ssafy.smile.domain.repository.PortfolioRepository
import com.ssafy.smile.presentation.base.BaseRepository
import retrofit2.Response

class PortfolioRepositoryImpl(private val portfolioRemoteDataSource: PortfolioRemoteDataSource): BaseRepository(), PortfolioRepository {
    private val _getPortfolioResponseLiveData = MutableLiveData<NetworkUtils.NetworkResponse<PortfolioResponseDto>>()
    val getPortfolioResponseLiveData: LiveData<NetworkUtils.NetworkResponse<PortfolioResponseDto>>
        get() = _getPortfolioResponseLiveData

    private val _geArticlesResponseLiveData = MutableLiveData<NetworkUtils.NetworkResponse<ArticleResponseDto>>()
    val geArticlesResponseLiveData: LiveData<NetworkUtils.NetworkResponse<ArticleResponseDto>>
        get() = _geArticlesResponseLiveData

    override suspend fun getPortfolio(photographerId: Long) {
        portfolioRemoteDataSource.getPortfolio(photographerId)
    }

    override suspend fun getArticles(photographerId: Long) {
        portfolioRemoteDataSource.getArticles(photographerId)
    }
}