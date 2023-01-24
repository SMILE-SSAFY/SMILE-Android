package com.ssafy.smile.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.smile.Application
import com.ssafy.smile.common.sources.Event
import java.util.concurrent.TimeUnit

abstract class BaseViewModel : ViewModel() {

    // TODO : 민하 코드 필요 (토큰 처리)
    private var _fcmToken: String? = null
    val fcmToken: String get() = _fcmToken!!
    private var _authToken: String? = null
    val authToken: String get() = _authToken!!
    val isTokenAvailable: Boolean get() = _authToken != null

    init {
        _authToken = Application.authToken
        _fcmToken = Application.fcmToken
    }

    private val _onBackPressed = MutableLiveData<Any>()
    val onBackPressed: LiveData<Any> get() = _onBackPressed
    private var mBackPressedAt = 0L

    private val _error = MutableLiveData<Event<String>>()
    val error: LiveData<Event<String>> = _error


    fun onBackPressed() {
        if (mBackPressedAt + TimeUnit.SECONDS.toMillis(2) > System.currentTimeMillis()) {
            _onBackPressed.postValue(true)
        } else {
            _onBackPressed.postValue("\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.")
            mBackPressedAt = System.currentTimeMillis()
        }
    }

    fun handleError(exception: Throwable) {
        val message = exception.message ?: ""
        _error.value = Event(message)
    }

}