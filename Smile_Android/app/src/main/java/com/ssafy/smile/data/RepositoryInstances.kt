package com.ssafy.smile.data

import com.ssafy.smile.data.remote.repository.ExampleRepositoryImpl
import com.ssafy.smile.data.remote.repository.LikeRepositoryImpl
import com.ssafy.smile.data.remote.repository.PortfolioRepositoryImpl
import com.ssafy.smile.data.remote.repository.UserRepositoryImpl
import javax.inject.Singleton

@Singleton
class RepositoryInstances(dataSourceInstances: DataSourceInstances) {
    @Singleton
    private val exampleRemoteRepository : ExampleRepositoryImpl = ExampleRepositoryImpl(dataSourceInstances.getExampleRemoteDataSource())

    @Singleton
    private val userRepository: UserRepositoryImpl = UserRepositoryImpl(dataSourceInstances.getUserRemoteDataSource())

    @Singleton
    private val portfolioRepository: PortfolioRepositoryImpl = PortfolioRepositoryImpl(dataSourceInstances.getPortfolioRemoteDataSource())

    @Singleton
    private val likeRepository: LikeRepositoryImpl = LikeRepositoryImpl(dataSourceInstances.getLikeRemoteDataSource())

    fun getExampleRemoteRepository() : ExampleRepositoryImpl = exampleRemoteRepository
    fun getUserRepository(): UserRepositoryImpl = userRepository
    fun getPortfolioRepository(): PortfolioRepositoryImpl = portfolioRepository
    fun getLikeRepository(): LikeRepositoryImpl = likeRepository
}
