package com.ssafy.smile.data

import android.content.Context
import com.ssafy.smile.data.local.repository.AddressRepositoryImpl
import com.ssafy.smile.data.remote.repository.*
import javax.inject.Singleton

@Singleton
class RepositoryInstances(dataSourceInstances: DataSourceInstances) {

    @Singleton
    private val addressRepository : AddressRepositoryImpl = AddressRepositoryImpl(dataSourceInstances.getAddressRemoteDataSource())

    @Singleton
    private val userRepository: UserRepositoryImpl = UserRepositoryImpl(dataSourceInstances.getUserRemoteDataSource())

    @Singleton
    private val photographerRepository : PhotographerRepositoryImpl = PhotographerRepositoryImpl(dataSourceInstances.getPhotographerRemoteDataSource())

    @Singleton
    private val heartRepository: HeartRepositoryImpl = HeartRepositoryImpl(dataSourceInstances.getHeartRemoteDataSource())

    @Singleton
    private val portfolioRepository : PortfolioRepositoryImpl = PortfolioRepositoryImpl(dataSourceInstances.getPortfolioRemoteDataSource())

    @Singleton
    private val postRepository: PostRepositoryImpl = PostRepositoryImpl(dataSourceInstances.getPostRemoteDataSource())

    @Singleton
    private val searchRepository: SearchRepositoryImpl = SearchRepositoryImpl(dataSourceInstances.getSearchRemoteDataSource())

    @Singleton
    private val reservationRepository: ReservationRepositoryImpl = ReservationRepositoryImpl(dataSourceInstances.getReservationRemoteDataSource())

    @Singleton
    private val articleRepository : ArticleRepositoryImpl = ArticleRepositoryImpl(dataSourceInstances.getArticleRemoteDataSource())

    @Singleton
    private val recommendRepository: RecommendRepositoryImpl = RecommendRepositoryImpl(dataSourceInstances.getPhotographerRecommendDataSource())

    fun getAddressRepository() : AddressRepositoryImpl = addressRepository
    fun getUserRepository(): UserRepositoryImpl = userRepository
    fun getPortfolioRepository(): PortfolioRepositoryImpl = portfolioRepository
    fun getHeartRepository(): HeartRepositoryImpl = heartRepository
    fun getPhotographerRepository() : PhotographerRepositoryImpl = photographerRepository
    fun getPostRepository(): PostRepositoryImpl = postRepository
    fun getSearchRepository(): SearchRepositoryImpl = searchRepository
    fun getReservationRepository(): ReservationRepositoryImpl = reservationRepository
    fun getArticleRepository() : ArticleRepositoryImpl = articleRepository
    fun getRecommendRepository(): RecommendRepositoryImpl = recommendRepository
}
