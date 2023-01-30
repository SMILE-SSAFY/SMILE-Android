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

    @Singleton
    private val postRemoteDataSourceImpl: PostRemoteDataSourceImpl = PostRemoteDataSourceImpl(serviceInstances.getPostApiService())

    @Singleton
    private val searchRemoteDataSourceImpl: SearchRemoteDataSourceImpl = SearchRemoteDataSourceImpl(serviceInstances.getSearchApiService())

    @Singleton
    private val reservationRemoteDataSourceImpl: ReservationRemoteDateSourceImpl = ReservationRemoteDateSourceImpl(serviceInstances.getReservationApiService())

    fun getUserRemoteDataSource(): UserRemoteDataSourceImpl = userRemoteDataSourceImpl
    fun getPortfolioRemoteDataSource(): PortfolioRemoteDataSourceImpl = portfolioRemoteDataSourceImpl
    fun getLikeRemoteDataSource(): LikeRemoteDataSourceImpl = likeRemoteDataSourceImpl
    fun getPhotographerRemoteDataSource() : PhotographerRemoteDataSourceImpl = photographerRemoteDataSourceImpl
    fun getPostRemoteDataSource(): PostRemoteDataSourceImpl = postRemoteDataSourceImpl
    fun getSearchRemoteDataSource(): SearchRemoteDataSourceImpl = searchRemoteDataSourceImpl
    fun getReservationRemoteDataSource(): ReservationRemoteDateSourceImpl = reservationRemoteDataSourceImpl

}