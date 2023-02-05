package com.ssafy.smile.presentation.base

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.smile.common.util.SharedPreferencesUtil
import com.ssafy.smile.common.view.sources.Event
import com.ssafy.smile.common.util.convertToRequestBody
import com.ssafy.smile.domain.model.Types
import okhttp3.MultipartBody
import java.io.File
import java.util.concurrent.TimeUnit

abstract class BaseViewModel : ViewModel() {

    private val _roleLiveData : MutableLiveData<Types.Role> = MutableLiveData<Types.Role>()
    val getRoleLiveData : LiveData<Types.Role> = _roleLiveData

    fun getRole(context: Context){
        val role = SharedPreferencesUtil(context).getRole()
        _roleLiveData.postValue(Types.Role.getRoleType(role?:Types.Role.USER.getValue()))
    }

    fun putRole(context: Context, role: Types.Role){
        SharedPreferencesUtil(context).putRole(role)
    }

    fun changeRole(context: Context, role: Types.Role){
        SharedPreferencesUtil(context).changeRole(role)
        getRole(context)
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

    fun makeMultiPartBody(jsonKey: String, file: File) : MultipartBody.Part {
        val requestBody = file.convertToRequestBody()
        return MultipartBody.Part.createFormData(jsonKey, file.name, requestBody)
    }
    fun makeMultiPartBodyList(jsonKey: String, images: List<File>): List<MultipartBody.Part> {
        val imageList = arrayListOf<MultipartBody.Part>()
        for (i in images.indices) {
            val multipartBody = makeMultiPartBody(jsonKey, images[i])
            imageList.add(multipartBody)
        }
        return imageList
    }

}