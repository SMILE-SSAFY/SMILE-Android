package com.ssafy.smile.data

import com.ssafy.smile.data.remote.repository.LikeRepositoryImpl
import com.ssafy.smile.data.remote.repository.PortfolioRepositoryImpl
import com.ssafy.smile.data.remote.repository.PhotographerRepositoryImpl
import com.ssafy.smile.data.remote.repository.UserRepositoryImpl
import javax.inject.Singleton

@Singleton
class RepositoryInstances(dataSourceInstances: DataSourceInstances) {

    @Singleton
    private val userRepository: UserRepositoryImpl = UserRepositoryImpl(dataSourceInstances.getUserRemoteDataSource())

    @Singleton
    private val photographerRepository : PhotographerRepositoryImpl = PhotographerRepositoryImpl(dataSourceInstances.getPhotographerRemoteDataSource())

    @Singleton
    private val likeRepository: LikeRepositoryImpl = LikeRepositoryImpl(dataSourceInstances.getLikeRemoteDataSource())

    @Singleton
    private val portfolioRepository : PortfolioRepositoryImpl = PortfolioRepositoryImpl(dataSourceInstances.getPortfolioRemoteDataSource())


    fun getUserRepository(): UserRepositoryImpl = userRepository
    fun getPortfolioRepository(): PortfolioRepositoryImpl = portfolioRepository
    fun getLikeRepository(): LikeRepositoryImpl = likeRepository
    fun getPhotographerRepository() : PhotographerRepositoryImpl = photographerRepository

}
