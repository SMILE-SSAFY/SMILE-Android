package com.ssafy.smile.presentation.base


import androidx.lifecycle.MutableLiveData
import com.ssafy.smile.common.util.NetworkUtils.NetworkResponse
import retrofit2.Response


// TODO : Retrofit + Coroutine + SafeCall 처리 필요 - 1
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
    }
}