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
    private val likeRemoteDataSourceImpl: LikeRemoteDataSourceImpl = LikeRemoteDataSourceImpl(serviceInstances.getLikeApiService())

    @Singleton
    private val photographerRemoteDataSourceImpl : PhotographerRemoteDataSourceImpl = PhotographerRemoteDataSourceImpl(serviceInstances.getPhotographerApiService())

    @Singleton
    private val postRemoteDataSourceImpl: PostRemoteDataSourceImpl = PostRemoteDataSourceImpl(serviceInstances.getPostApiService())

    @Singleton
    private val searchRemoteDataSourceImpl: SearchRemoteDataSourceImpl = SearchRemoteDataSourceImpl(serviceInstances.getSearchApiService())


    fun getAddressRemoteDataSource() : AddressLocalDataSourceImpl = addressLocalDataSourceImpl
    fun getUserRemoteDataSource(): UserRemoteDataSourceImpl = userRemoteDataSourceImpl
    fun getPortfolioRemoteDataSource(): PortfolioRemoteDataSourceImpl = portfolioRemoteDataSourceImpl
    fun getLikeRemoteDataSource(): LikeRemoteDataSourceImpl = likeRemoteDataSourceImpl
    fun getPhotographerRemoteDataSource() : PhotographerRemoteDataSourceImpl = photographerRemoteDataSourceImpl
    fun getPostRemoteDataSource(): PostRemoteDataSourceImpl = postRemoteDataSourceImpl
    fun getSearchRemoteDataSource(): SearchRemoteDataSourceImpl = searchRemoteDataSourceImpl

}