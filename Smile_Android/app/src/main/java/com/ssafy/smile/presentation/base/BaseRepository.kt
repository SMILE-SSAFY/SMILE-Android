package com.ssafy.smile.presentation.base

import androidx.lifecycle.MutableLiveData
import com.ssafy.smile.common.util.NetworkUtils.NetworkResponse
import com.ssafy.smile.data.remote.model.Post
import retrofit2.Response


open class BaseRepository {

    suspend fun <T : Any> safeApiCall(liveData: MutableLiveData<NetworkResponse<T>>, action: suspend () -> Response<T>){
        liveData.postValue(NetworkResponse.Loading())
        val result : NetworkResponse<T> = safeApiResult(action=action)
        liveData.postValue(result)
    }

    private suspend fun <T : Any> safeApiResult(action: suspend () -> Response<T>): NetworkResponse<T> {
        val response = action.invoke()
        if (response.isSuccessful && response.body()!=null) return NetworkResponse.Success(response.body()!!)
        return NetworkResponse.Failure(response.code())
        // error 메세지를 그냥 코드에 맞춰서 할거야? 아니면 기능?
    }
}