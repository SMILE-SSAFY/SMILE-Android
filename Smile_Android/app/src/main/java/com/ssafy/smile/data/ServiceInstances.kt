package com.ssafy.smile.data

import com.ssafy.smile.data.remote.service.ExampleApiService
import com.ssafy.smile.data.remote.service.PhotographerApiService
import com.ssafy.smile.data.remote.service.UserApiService
import javax.inject.Singleton

@Singleton
class ServiceInstances(retrofitInstances : RetrofitInstances) {
    @Singleton
    private val exampleApiService : ExampleApiService = retrofitInstances.getRetrofit().create(ExampleApiService::class.java)

    @Singleton
    private val userApiService : UserApiService = retrofitInstances.getRetrofit().create(UserApiService::class.java)

    @Singleton
    private val photographerApiService : PhotographerApiService = retrofitInstances.getRetrofit().create(PhotographerApiService::class.java)

    fun getExampleApiService() : ExampleApiService = exampleApiService
    fun getUserApiService() : UserApiService = userApiService
    fun getPhotographerApiService() : PhotographerApiService = photographerApiService

}