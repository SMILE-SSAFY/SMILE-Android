package com.ssafy.smile

import android.app.Application
import com.ssafy.smile.common.util.SharedPreferencesUtil
import com.ssafy.smile.data.*

class Application : Application()  {

    companion object{
        lateinit var sharedPreferences: SharedPreferencesUtil
        var authToken : String? = null
        var fcmToken : String? = null

        private val okHttpInstances = OkhttpClientInstances
        private val retrofitInstances = RetrofitInstances(okHttpInstances)
        private val serviceInstances = ServiceInstances(retrofitInstances)
        private val dataSourceInstances = DataSourceInstances(serviceInstances)
        val repositoryInstances = RepositoryInstances(dataSourceInstances)
    }

    override fun onCreate() {
        super.onCreate()
        initSharedPreference()
    }

    private fun initSharedPreference(){
        sharedPreferences = SharedPreferencesUtil(this)
        authToken = sharedPreferences.getAuthToken()
        fcmToken = sharedPreferences.getFCMToken()
    }


}