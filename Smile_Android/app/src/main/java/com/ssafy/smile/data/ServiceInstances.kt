package com.ssafy.smile.data


import com.ssafy.smile.data.remote.service.*
import retrofit2.create
import javax.inject.Singleton

@Singleton
class ServiceInstances(retrofitInstances : RetrofitInstances) {
    private val userApiService : UserApiService = retrofitInstances.getRetrofit().create(UserApiService::class.java)

    @Singleton
    private val portfolioApiService: PortfolioApiService = retrofitInstances.getRetrofit().create(PortfolioApiService::class.java)

    @Singleton
    private val likeApiService: LikeApiService = retrofitInstances.getRetrofit().create(LikeApiService::class.java)

    @Singleton
    private val photographerApiService : PhotographerApiService = retrofitInstances.getRetrofit().create(PhotographerApiService::class.java)

    @Singleton
    private val postApiService: PostApiService = retrofitInstances.getRetrofit().create(PostApiService::class.java)

    @Singleton
    private val searchApiService: SearchApiService = retrofitInstances.getRetrofit().create(SearchApiService::class.java)

    @Singleton
    private val reservationApiService: ReservationApiService = retrofitInstances.getRetrofit().create(ReservationApiService::class.java)

    fun getUserApiService() : UserApiService = userApiService
    fun getPortfolioApiService(): PortfolioApiService = portfolioApiService
    fun getLikeApiService(): LikeApiService = likeApiService
    fun getPhotographerApiService() : PhotographerApiService = photographerApiService
    fun getPostApiService(): PostApiService = postApiService
    fun getSearchApiService(): SearchApiService = searchApiService
    fun getReservationApiService(): ReservationApiService = reservationApiService

}