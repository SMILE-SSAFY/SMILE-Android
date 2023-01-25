package com.ssafy.smile.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.smile.Application
import com.ssafy.smile.common.sources.Event
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.util.concurrent.TimeUnit

abstract class BaseViewModel : ViewModel() {

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

    fun String?.convertToRequestBody() : RequestBody = requireNotNull(this).toRequestBody("text/plain".toMediaTypeOrNull())
    fun File?.convertToRequestBody() : RequestBody = requireNotNull(this).asRequestBody("image/*".toMediaTypeOrNull())

}