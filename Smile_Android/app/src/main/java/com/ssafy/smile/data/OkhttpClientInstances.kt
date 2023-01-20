package com.ssafy.smile.data

import android.app.Application
import com.ssafy.smile.MainActivity
import com.ssafy.smile.common.util.SharedPreferencesUtil
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

object OkhttpClientInstances {

//    private val interceptor = Interceptor { chain ->
//        val accessToken = SharedPreferencesUtil(Application().applicationContext).getAuthToken()
//        val request = chain.request().newBuilder()
//            .addHeader("Authorization", accessToken)
//            .build()
//        chain.proceed(request)
//    }

    @Singleton
    private val okhttpClient : OkHttpClient = OkHttpClient
        .Builder()
        .readTimeout(10, TimeUnit.SECONDS)
        .connectTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
//        .addInterceptor(interceptor)
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    fun getOkhttpClient() : OkHttpClient = okhttpClient
}
