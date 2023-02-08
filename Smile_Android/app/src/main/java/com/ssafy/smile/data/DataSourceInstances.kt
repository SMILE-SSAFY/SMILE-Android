package com.ssafy.smile.data

import com.ssafy.smile.data.local.database.AppDatabase
import com.ssafy.smile.data.local.datasource.AddressLocalDataSourceImpl
import com.ssafy.smile.data.remote.datasource.*

import javax.inject.Singleton

@Singleton
class DataSourceInstances(appDatabase: AppDatabase, serviceInstances: ServiceInstances) {

    @Singleton
    private val addressLocalDataSourceImpl : AddressLocalDataSourceImpl = AddressLocalDataSourceImpl(appDatabase.addressDao())

    @Singleton
    private val userRemoteDataSourceImpl : UserRemoteDataSourceImpl = UserRemoteDataSourceImpl(serviceInstances.getUserApiService())

    @Singleton
    private val portfolioRemoteDataSourceImpl: PortfolioRemoteDataSourceImpl = PortfolioRemoteDataSourceImpl(serviceInstances.getPortfolioApiService())

    @Singleton
    private val heartRemoteDataSourceImpl: HeartRemoteDataSourceImpl = HeartRemoteDataSourceImpl(serviceInstances.getHeartApiService())

    @Singleton
    private val photographerRemoteDataSourceImpl : PhotographerRemoteDataSourceImpl = PhotographerRemoteDataSourceImpl(serviceInstances.getPhotographerApiService())

    @Singleton
    private val postRemoteDataSourceImpl: PostRemoteDataSourceImpl = PostRemoteDataSourceImpl(serviceInstances.getPostApiService())

    @Singleton
    private val searchRemoteDataSourceImpl: SearchRemoteDataSourceImpl = SearchRemoteDataSourceImpl(serviceInstances.getSearchApiService())

    @Singleton
    private val reservationRemoteDataSourceImpl: ReservationRemoteDateSourceImpl = ReservationRemoteDateSourceImpl(serviceInstances.getReservationApiService())

    @Singleton
    private val articleRemoteDataSourceImpl : ArticleRemoteDataSourceImpl = ArticleRemoteDataSourceImpl(serviceInstances.getArticleApiService())

    @Singleton
    private val recommendRemoteDataSourceImpl: RecommendRemoteDataSourceImpl = RecommendRemoteDataSourceImpl(serviceInstances.getPhotographerRecommendApiService())

    fun getAddressRemoteDataSource() : AddressLocalDataSourceImpl = addressLocalDataSourceImpl
    fun getUserRemoteDataSource(): UserRemoteDataSourceImpl = userRemoteDataSourceImpl
    fun getPortfolioRemoteDataSource(): PortfolioRemoteDataSourceImpl = portfolioRemoteDataSourceImpl
    fun getHeartRemoteDataSource(): HeartRemoteDataSourceImpl = heartRemoteDataSourceImpl
    fun getPhotographerRemoteDataSource() : PhotographerRemoteDataSourceImpl = photographerRemoteDataSourceImpl
    fun getPostRemoteDataSource(): PostRemoteDataSourceImpl = postRemoteDataSourceImpl
    fun getSearchRemoteDataSource(): SearchRemoteDataSourceImpl = searchRemoteDataSourceImpl
    fun getReservationRemoteDataSource(): ReservationRemoteDateSourceImpl = reservationRemoteDataSourceImpl
    fun getArticleRemoteDataSource() : ArticleRemoteDataSourceImpl = articleRemoteDataSourceImpl
    fun getPhotographerRecommendDataSource(): RecommendRemoteDataSourceImpl = recommendRemoteDataSourceImpl

}