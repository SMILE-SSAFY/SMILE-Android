package com.ssafy.smile.data

import com.ssafy.smile.data.remote.service.ExampleApiService
import javax.inject.Singleton

@Singleton
class ServiceInstances(retrofitInstances : RetrofitInstances) {
    @Singleton
    private val exampleApiService : ExampleApiService = retrofitInstances.getRetrofit().create(ExampleApiService::class.java)

    fun getExampleApiService() : ExampleApiService = exampleApiService
}