package com.ssafy.smile.data

import com.ssafy.smile.data.remote.service.ExampleApiService
import com.ssafy.smile.data.remote.service.PortfolioApiService
import javax.inject.Singleton

@Singleton
class ServiceInstances(retrofitInstances : RetrofitInstances) {
    @Singleton
    private val exampleApiService : ExampleApiService = retrofitInstances.getRetrofit().create(ExampleApiService::class.java)
    @Singleton
    private val portfolioApiService : PortfolioApiService = retrofitInstances.getRetrofit().create(PortfolioApiService::class.java)

    fun getExampleApiService() : ExampleApiService = exampleApiService
    fun getPortfolioApiService() : PortfolioApiService = portfolioApiService
}