package com.ssafy.smile.data


import android.provider.ContactsContract.CommonDataKinds.Photo
import com.ssafy.smile.data.remote.service.LikeApiService
import com.ssafy.smile.data.remote.service.PhotographerApiService
import com.ssafy.smile.data.remote.service.PortfolioApiService
import com.ssafy.smile.data.remote.service.UserApiService
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

    fun getUserApiService() : UserApiService = userApiService
    fun getPortfolioApiService(): PortfolioApiService = portfolioApiService
    fun getLikeApiService(): LikeApiService = likeApiService
    fun getPhotographerApiService() : PhotographerApiService = photographerApiService


}