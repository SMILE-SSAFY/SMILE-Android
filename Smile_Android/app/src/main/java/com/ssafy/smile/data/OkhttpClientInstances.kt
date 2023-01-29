package com.ssafy.smile.data

import com.ssafy.smile.Application
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


object OkhttpClientInstances {
    var interceptor = Interceptor { chain ->
        val accessToken = Application.authToken
        var request = if (accessToken != null && accessToken != "") {
            chain.request().newBuilder()
              .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZSI6IlVTRVIiLCJpZCI6IjEiLCJpYXQiOjE2NzQ4MDA1NzQsImV4cCI6MTY3NzM5MjU3NH0.51P7KyE3fBoT2r7NCzBSvY4X8zd1Efp1QzM3bbzv2PA")
              .build()
        } else chain.request()

        chain.proceed(request)
    }

    @Singleton
    private val okhttpClient : OkHttpClient = OkHttpClient
        .Builder()
        .readTimeout(10, TimeUnit.SECONDS)
        .connectTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .addNetworkInterceptor(interceptor)
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    fun getOkhttpClient() : OkHttpClient = okhttpClient
}
