package com.ssafy.smile.data.remote.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssafy.smile.common.util.NetworkUtils
import com.ssafy.smile.data.remote.datasource.PortfolioRemoteDataSource
import com.ssafy.smile.data.remote.model.PortfolioResponseDto
import com.ssafy.smile.domain.repository.PortfolioRepository
import com.ssafy.smile.presentation.base.BaseRepository

class PortfolioRepositoryImpl(private val portfolioRemoteDataSource: PortfolioRemoteDataSource): BaseRepository(), PortfolioRepository {
    private val _getPortfolioResponseLiveData = MutableLiveData<NetworkUtils.NetworkResponse<PortfolioResponseDto>>()
    val getPortfolioResponseLiveData: LiveData<NetworkUtils.NetworkResponse<PortfolioResponseDto>>
        get() = _getPortfolioResponseLiveData


    override suspend fun getPortfolio(photographerId: Long) {
        portfolioRemoteDataSource.getPortfolio(photographerId)
    }
}