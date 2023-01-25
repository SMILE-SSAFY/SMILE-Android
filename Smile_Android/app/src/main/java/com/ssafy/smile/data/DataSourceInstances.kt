package com.ssafy.smile.data

import com.ssafy.smile.data.remote.datasource.*

import javax.inject.Singleton

@Singleton
class DataSourceInstances(serviceInstances: ServiceInstances) {

    @Singleton
    private val userRemoteDataSourceImpl : UserRemoteDataSourceImpl = UserRemoteDataSourceImpl(serviceInstances.getUserApiService())

    @Singleton
    private val portfolioRemoteDataSourceImpl: PortfolioRemoteDataSourceImpl = PortfolioRemoteDataSourceImpl(serviceInstances.getPortfolioApiService())

    @Singleton
    private val likeRemoteDataSourceImpl: LikeRemoteDataSourceImpl = LikeRemoteDataSourceImpl(serviceInstances.getLikeApiService())

    @Singleton
    private val photographerRemoteDataSourceImpl : PhotographerRemoteDataSourceImpl = PhotographerRemoteDataSourceImpl(serviceInstances.getPhotographerApiService())

    fun getUserRemoteDataSource(): UserRemoteDataSourceImpl = userRemoteDataSourceImpl
    fun getPortfolioRemoteDataSource(): PortfolioRemoteDataSourceImpl = portfolioRemoteDataSourceImpl
    fun getLikeRemoteDataSource(): LikeRemoteDataSourceImpl = likeRemoteDataSourceImpl
    fun getPhotographerRemoteDataSource() : PhotographerRemoteDataSourceImpl = photographerRemoteDataSourceImpl


}