package com.ssafy.smile

import android.app.Application
import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import com.kakao.sdk.common.KakaoSdk
import com.ssafy.smile.common.util.SharedPreferencesUtil
import com.ssafy.smile.data.*
import com.ssafy.smile.data.local.database.AppDatabase
import kr.co.bootpay.android.*;

private const val TAG = "Application_싸피"
class Application : Application()  {

    companion object{
        lateinit var sharedPreferences: SharedPreferencesUtil
        var authToken : String? = null
        var authTime : Long? = null
        var fcmToken : String? = null
        var role : String? = null
        var userId : Long = -1L

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
        getFirebaseToken()
    }

    private fun initContextInjection(){
        sharedPreferences = SharedPreferencesUtil(this)
        authToken = sharedPreferences.getAuthToken()
        fcmToken = sharedPreferences.getFCMToken()
        role = sharedPreferences.getRole()
        userId = sharedPreferences.getUserId()

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

    private fun getFirebaseToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                task.result?.let {
                    sharedPreferences.putFCMToken(it)
                }
            } else error("FCM 토큰 얻기에 실패하였습니다. 잠시 후 다시 시도해주세요.")
        }
    }
}