package com.ssafy.smile.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.concurrent.TimeUnit

abstract class BaseViewModel : ViewModel() {


    private val _onBackPressed = MutableLiveData<Any>()
    val onBackPressed: LiveData<Any> get() = _onBackPressed
    private var mBackPressedAt = 0L


    fun onBackPressed() {
        if (mBackPressedAt + TimeUnit.SECONDS.toMillis(2) > System.currentTimeMillis()) {
            _onBackPressed.postValue(true)
        } else {
            _onBackPressed.postValue("\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.")
            mBackPressedAt = System.currentTimeMillis()
        }
    }

}