package com.ssafy.smile

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.ssafy.smile.common.util.SharedPreferencesUtil
import com.ssafy.smile.data.*
import com.ssafy.smile.data.local.database.AppDatabase

class Application : Application()  {

    companion object{
        lateinit var sharedPreferences: SharedPreferencesUtil
        var authToken : String? = null
        var fcmToken : String? = null

        private val okHttpInstances = OkhttpClientInstances
        private val retrofitInstances = RetrofitInstances(okHttpInstances)
        private val serviceInstances = ServiceInstances(retrofitInstances)
        lateinit var appDatabaseInstance : AppDatabase
        lateinit var dataSourceInstances : DataSourceInstances
        lateinit var repositoryInstances : RepositoryInstances
    }

    override fun onCreate() {
        super.onCreate()
        initContextInjection()
        kakaoInit()
    }

    private fun initContextInjection(){
        sharedPreferences = SharedPreferencesUtil(this)
        authToken = sharedPreferences.getAuthToken()
        fcmToken = sharedPreferences.getFCMToken()

        appDatabaseInstance = AppDatabase.getDatabase(this)
        dataSourceInstances = DataSourceInstances(appDatabaseInstance, serviceInstances)
        repositoryInstances = RepositoryInstances(dataSourceInstances)
    }

    private fun kakaoInit() {
        KakaoSdk.init(this, getString(R.string.kakao_native_key))
    }

}