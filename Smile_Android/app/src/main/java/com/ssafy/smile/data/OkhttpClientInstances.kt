package com.ssafy.smile.data

import com.ssafy.smile.Application
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


object OkhttpClientInstances {
    private var interceptor = Interceptor { chain ->
        val accessToken = Application.authToken
        val request = if (accessToken != null && accessToken != "") {
            chain.request().newBuilder()
              .addHeader("Authorization", accessToken)
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
