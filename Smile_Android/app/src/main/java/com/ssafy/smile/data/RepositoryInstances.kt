package com.ssafy.smile.data

import com.ssafy.smile.data.remote.repository.*
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

    @Singleton
    private val postRepository: PostRepositoryImpl = PostRepositoryImpl(dataSourceInstances.getPostRemoteDataSource())

    @Singleton
    private val searchRepository: SearchRepositoryImpl = SearchRepositoryImpl(dataSourceInstances.getSearchRemoteDataSource())

    @Singleton
    private val reservationRepository: ReservationRepositoryImpl = ReservationRepositoryImpl(dataSourceInstances.getReservationRemoteDataSource())

    fun getUserRepository(): UserRepositoryImpl = userRepository
    fun getPortfolioRepository(): PortfolioRepositoryImpl = portfolioRepository
    fun getLikeRepository(): LikeRepositoryImpl = likeRepository
    fun getPhotographerRepository() : PhotographerRepositoryImpl = photographerRepository
    fun getPostRepository(): PostRepositoryImpl = postRepository
    fun getSearchRepository(): SearchRepositoryImpl = searchRepository
    fun getReservationRepository(): ReservationRepositoryImpl = reservationRepository
}
