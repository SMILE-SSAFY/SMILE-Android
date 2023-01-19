package com.ssafy.smile.data

import com.google.gson.GsonBuilder
import com.ssafy.smile.common.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Singleton
class RetrofitInstances(okHttpClientInstances: OkhttpClientInstances) {

    @Singleton
    private val retrofit : Retrofit = Retrofit
        .Builder()
        .baseUrl(Constants.BASE_URL)
        .client(okHttpClientInstances.getOkhttpClient())
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .build()

    fun getRetrofit() : Retrofit = retrofit
}

