package com.ssafy.smile

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.navigation.fragment.findNavController
import com.google.firebase.messaging.FirebaseMessaging
import com.kakao.sdk.common.KakaoSdk
import com.ssafy.smile.common.util.SharedPreferencesUtil
import com.ssafy.smile.data.*
import com.ssafy.smile.data.local.database.AppDatabase
import com.ssafy.smile.presentation.view.MainFragmentDirections
import com.ssafy.smile.presentation.view.home.CustomRecommendDialog
import kr.co.bootpay.android.*;

private const val TAG = "Application_싸피"
class Application : Application()  {

    companion object{
        lateinit var sharedPreferences: SharedPreferencesUtil
        var isRecommendRefused : Boolean = false
        var authToken : String? = null
        var authTime : Long? = null
        var fcmToken : String? = null
        var role : String? = null
        var userId : Long = -1L
        var isFirstInit: Boolean = true

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
        bootPayInit()
    }

    private fun initContextInjection(){
        sharedPreferences = SharedPreferencesUtil(this)
        authToken = sharedPreferences.getAuthToken()
        fcmToken = sharedPreferences.getFCMToken()
        role = sharedPreferences.getRole()
        userId = sharedPreferences.getUserId()
        isFirstInit = sharedPreferences.changeViewPagerInit(true)

        appDatabaseInstance = AppDatabase.getDatabase(this)
        dataSourceInstances = DataSourceInstances(appDatabaseInstance, serviceInstances)
        repositoryInstances = RepositoryInstances(dataSourceInstances)
    }

    private fun kakaoInit() {
        KakaoSdk.init(this, getString(R.string.kakao_native_key))
    }

    private fun bootPayInit() {
        BootpayAnalytics.init(this, getString(R.string.bootpay_key))
    }

    fun getAuthToken(): String? {
        return sharedPreferences.getAuthToken()
    }
}