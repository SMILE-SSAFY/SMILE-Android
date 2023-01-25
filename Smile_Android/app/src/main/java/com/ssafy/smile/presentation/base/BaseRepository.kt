package com.ssafy.smile.presentation.base

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ssafy.smile.common.util.NetworkUtils.NetworkResponse
import retrofit2.Response


private const val TAG = "BaseRepository_스마일"
open class BaseRepository {

    suspend fun <T : Any> safeApiCall(liveData: MutableLiveData<NetworkResponse<T>>, action: suspend () -> Response<T>){
        liveData.postValue(NetworkResponse.Loading())
        val result : NetworkResponse<T> = safeApiResult(action=action)
        liveData.postValue(result)
    }

    private suspend fun <T : Any> safeApiResult(action: suspend () -> Response<T>): NetworkResponse<T> {
        val response = action.invoke()
        Log.d(TAG, "safeApiResult: $response")
        if (response.isSuccessful && response.body()!=null) return NetworkResponse.Success(response.body()!!)
        return NetworkResponse.Failure(response.code())

    }
}