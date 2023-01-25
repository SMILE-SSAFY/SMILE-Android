package com.ssafy.smile.data

import com.ssafy.smile.data.remote.service.ExampleApiService
import com.ssafy.smile.data.remote.service.LikeApiService
import com.ssafy.smile.data.remote.service.PortfolioApiService
import com.ssafy.smile.data.remote.service.UserApiService
import javax.inject.Singleton

@Singleton
class ServiceInstances(retrofitInstances : RetrofitInstances) {
    @Singleton
    private val exampleApiService : ExampleApiService = retrofitInstances.getRetrofit().create(ExampleApiService::class.java)

    private val userApiService : UserApiService = retrofitInstances.getRetrofit().create(UserApiService::class.java)

    @Singleton
    private val portfolioApiService: PortfolioApiService = retrofitInstances.getRetrofit().create(PortfolioApiService::class.java)

    @Singleton
    private val likeApiService: LikeApiService = retrofitInstances.getRetrofit().create(LikeApiService::class.java)

    fun getExampleApiService() : ExampleApiService = exampleApiService
    fun getUserApiService() : UserApiService = userApiService
    fun getPortfolioApiService(): PortfolioApiService = portfolioApiService
    fun getLikeApiService(): LikeApiService = likeApiService

}